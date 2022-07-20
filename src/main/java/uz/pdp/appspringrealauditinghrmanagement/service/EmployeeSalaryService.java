package uz.pdp.appspringrealauditinghrmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appspringrealauditinghrmanagement.entity.Employee;
import uz.pdp.appspringrealauditinghrmanagement.entity.EmployeeSalary;
import uz.pdp.appspringrealauditinghrmanagement.payload.ApiResponse;
import uz.pdp.appspringrealauditinghrmanagement.repository.EmployeeRepository;
import uz.pdp.appspringrealauditinghrmanagement.repository.EmployeeSalaryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeSalaryService {
    @Autowired
    EmployeeSalaryRepository employeeSalaryRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public ApiResponse payment(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null
                &&authentication.isAuthenticated()
                &&authentication.getPrincipal().equals("anonymousUser"));
        Employee employee = (Employee) authentication.getPrincipal();

        List<Employee> employeeList = employeeRepository.findAllByCompanyId(employee.getCompany().getId());
        for (Employee employees : employeeList) {
            EmployeeSalary salary = new EmployeeSalary();
            salary.setSalary(employees.getSalary());
            salary.setEmployee(employees);
            salary.setDateTime(LocalDateTime.now());
            employeeSalaryRepository.save(salary);
        }
        return new ApiResponse("To'lov muvaffaqiyati bajarildi", true);
    }


}
