package uz.pdp.appspringrealauditinghrmanagement.service;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appspringrealauditinghrmanagement.entity.*;
import uz.pdp.appspringrealauditinghrmanagement.enums.RoleEnum;
import uz.pdp.appspringrealauditinghrmanagement.payload.*;
import uz.pdp.appspringrealauditinghrmanagement.repository.*;
import uz.pdp.appspringrealauditinghrmanagement.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService implements UserDetailsService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CompanyRepository companyRepository;



    public ApiResponse addDirector(EmployeeDto employeeDto){
        boolean existsByEmail = employeeRepository.existsByEmail(employeeDto.getEmail());
        if (existsByEmail) {
            return new ApiResponse("Bunday email allaqachon mavjud", false);
        }


        Optional<Company> optionalCompany = companyRepository.findById(employeeDto.getCompanyId());
        if(!optionalCompany.isPresent()){
            return new ApiResponse("Kompaniya topilmadi", false);
        }

        Employee employee = new Employee();
        employee.setRoles(Collections.singleton(roleRepository.findByRoleEnum(RoleEnum.ROLE_DIRECTOR)));
        employee.setCompany(optionalCompany.get());
        employeeRepository.save(employee);

        sendEmail(employee.getEmail(), employee.getEmailCode());

        return new ApiResponse("Director saqlandi", true);
    }




    public ApiResponse addManager(EmployeeDto employeeDto){
        Boolean exists = employeeRepository.existsByEmail(employeeDto.getEmail());
        if (exists)
            return new ApiResponse("Employee has already exists", false);
        Employee employee = new Employee();
        employee.setRoles(Collections.singleton(roleRepository.findByRoleEnum(RoleEnum.ROLE_MANAGER)));
        employeeRepository.save(employee);

        sendEmail(employee.getEmail(), employee.getEmailCode());

        return new ApiResponse("Manager saqlandi", true);
    }





    public ApiResponse addWorker(EmployeeDto employeeDto){
        Boolean exists = employeeRepository.existsByEmail(employeeDto.getEmail());
        if (exists)
            return new ApiResponse("Employee has already exists", false);
        Employee employee = new Employee();
        employee.setRoles(Collections.singleton(roleRepository.findByRoleEnum(RoleEnum.ROLE_WORKER)));
        employeeRepository.save(employee);

        sendEmail(employee.getEmail(), employee.getEmailCode());

        return new ApiResponse("Worker saqlandi", true);
    }





    public Employee createEmployee(EmployeeDto dto){
        Employee employee = new Employee();
        employee.setEmail(dto.getEmail());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmailCode(UUID.randomUUID().toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null
                &&authentication.isAuthenticated()
                &&!authentication.getPrincipal().equals("anonymousUser")){
            Employee authEmployee = (Employee) authentication.getPrincipal();
            employee.setCompany(authEmployee.getCompany());
        }
        return employee;
    }





    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }



    public ApiResponse getEmployee(UUID id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(!optionalEmployee.isPresent())
            return new ApiResponse("Bunday employee yoq", false);
        Employee employee = optionalEmployee.get();
        employee.setEnabled(false);
        employeeRepository.save(employee);
        return new ApiResponse("Employee mavjud", true);
    }





    public boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Test@pdp.com"); //kimdan kelganligi
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Accauntni tasdiqlash"); //tekst
            mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode="
                    + emailCode + "+&email=" + sendingEmail + "'>Tasdiqlang</a>");//tasdiqlashni bossa shu yulga
            //http://localhost:8080/api/auth/verifyEmail?emailCode=2666511111g8kkk&email=Test@Pdp.com mana shunday bo'ladi
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }





    public ApiResponse verifyEmail(String sendingEmail, Integer emailCode, HttpServletRequest request){
        Optional<Employee> optionalEmployee = employeeRepository.findByEmailAndEmailCode(sendingEmail, emailCode);
        if (optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            employee.setEnabled(true);
            employee.setPassword(passwordEncoder.encode(request.getParameter("password")));
            employee.setEmailCode(null);
            employeeRepository.save(employee);
            return new ApiResponse("Employee saqlandi", true);
        }
        return new ApiResponse("Account tasdiqlanmadi", false);
    }




    public ApiResponse login(LoginDto loginDto){
        try{
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()
                    ));
            Employee employee = (Employee) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getEmail(), employee.getRoles());

            return new ApiResponse("Token", true, token);

        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse("Parol yoki Login xato!", false);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> optionalUser = employeeRepository.findByEmail(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(username + "topilmadi");
        //yoki
        //return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(username+"topilmadi"))
    }
}
