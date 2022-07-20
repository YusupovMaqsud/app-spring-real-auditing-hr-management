package uz.pdp.appspringrealauditinghrmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uz.pdp.appspringrealauditinghrmanagement.entity.Employee;
import uz.pdp.appspringrealauditinghrmanagement.entity.Task;
import uz.pdp.appspringrealauditinghrmanagement.enums.TaskStatusEnum;
import uz.pdp.appspringrealauditinghrmanagement.payload.ApiResponse;
import uz.pdp.appspringrealauditinghrmanagement.payload.TaskDto;
import uz.pdp.appspringrealauditinghrmanagement.repository.EmployeeRepository;
import uz.pdp.appspringrealauditinghrmanagement.repository.TaskRepository;
import uz.pdp.appspringrealauditinghrmanagement.repository.TaskStatusRepository;

import javax.mail.internet.MimeMessage;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    TaskStatusRepository taskStatusRepository;


    public List<Task> getAllTask(){
        List<Task> taskList = taskRepository.findAll();
        return taskList;
    }


    
    public ApiResponse taskDirector(TaskDto taskDto){
        Optional<Employee> optionalEmployee = employeeRepository.findById(taskDto.getEmployeeId());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Bunday Employee mavjud emas", false);
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDeadline(String.valueOf(new Date(System.currentTimeMillis()+((1000*60*60*24)*taskDto.getDeadline()))));
        task.setStatus(String.valueOf(taskStatusRepository.findByName(TaskStatusEnum.NEW)));
        Employee employee = optionalEmployee.get();
        task.setEmployee(employee);
        taskRepository.save(task);
        sendEmail(employee.getEmail(), task.getTaskCode());
        return new ApiResponse("Xabarni saqlang va menejerga yuboring", true);
    }



    public ApiResponse taskManager(TaskDto taskDto){
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDeadline(String.valueOf(new Date(System.currentTimeMillis()+((1000*60*60*24)*taskDto.getDeadline()))));
        task.setStatus(String.valueOf(taskStatusRepository.findByName(TaskStatusEnum.NEW)));
        Optional<Employee> optionalEmployee = employeeRepository.findById(taskDto.getEmployeeId());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Bunday Emloyee mavjud emas", false);
        Employee employee = optionalEmployee.get();
        task.setEmployee(employee);
        taskRepository.save(task);
        sendEmail(employee.getEmail(), task.getTaskCode());
        return new ApiResponse("Xabarni saqlang va xodimga yuboring", true);

    }


    // Qabul qilish vazifani
    public ApiResponse acceptTask(UUID id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Vazifa topilmadi", false);
        Task task = optionalTask.get();
        task.setStatus(String.valueOf(taskStatusRepository.findByName(TaskStatusEnum.IN_PROGRESS)));
        taskRepository.save(task);
        return new ApiResponse("Vazifa muvaffaqiyatli qabul qilindi", true);
    }





    // Bajarilgan vazifa
    public ApiResponse completed(UUID taskId,UUID employeeId){
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Vazifa topilmadi", false);
        }
        Task task = optionalTask.get();
        if (task.getStatus().equals(TaskStatusEnum.DONE)) {
            return new ApiResponse("Vazifa allaqachon bajarilgan", false);
        }
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            return new ApiResponse("Employee topilmadi", false);
        }
        boolean existsByEmployeeId = taskRepository.existsByEmployeeId(employeeId);
        if (existsByEmployeeId) {
            return new ApiResponse("Empployee allaqachon bajarilayotgan vazifaga ega", false);
        }

        task.setEmployee(optionalEmployee.get());
        Task changedTask = taskRepository.save(task);

        return new ApiResponse("Vazifa muvaffaqiyatli bajarildi", true, changedTask);
    }





    // Xodim uchun topshiriq
    public List<Task> getTaskByEmployeeId(UUID id){
        List<Task> taskList = taskRepository.findAllByEmployeeId(id);
        return taskList;
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



}
