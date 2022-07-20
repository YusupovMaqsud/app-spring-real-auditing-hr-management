package uz.pdp.appspringrealauditinghrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringrealauditinghrmanagement.entity.Employee;
import uz.pdp.appspringrealauditinghrmanagement.payload.ApiResponse;
import uz.pdp.appspringrealauditinghrmanagement.payload.EmployeeDto;
import uz.pdp.appspringrealauditinghrmanagement.payload.LoginDto;
import uz.pdp.appspringrealauditinghrmanagement.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class EmployeeController {
    @Autowired
    EmployeeService authService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addDirector")
    public HttpEntity<?> addDirector(@Valid @RequestBody EmployeeDto employeeDto){
        ApiResponse apiResponse = authService.addDirector(employeeDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }




    @PreAuthorize("hasRole('DIRECTOR')")
    @PostMapping("/addManager")
    public HttpEntity<?> addManager(@Valid @RequestBody EmployeeDto employeeDto){
        ApiResponse apiResponse = authService.addManager(employeeDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }




    @PreAuthorize("hasAnyRole('DIRECTOR', 'MANAGER')")
    @PostMapping("/addWorker")
    public HttpEntity<?> addWorker(@Valid @RequestBody EmployeeDto employeeDto){
        ApiResponse apiResponse = authService.addWorker(employeeDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }




    @GetMapping("/employee/{id}")
    public HttpEntity<?> getEmployee(@PathVariable UUID id){
        ApiResponse apiResponse = authService.getEmployee(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }




    @PreAuthorize("hasAnyRole('DIRECTOR', 'MANAGER')")
    @GetMapping("/employee")
    public HttpEntity<List<Employee>> getEmployees(){
        List<Employee> employeeList = authService.getEmployees();

        return ResponseEntity.ok(employeeList);
    }



    @PostMapping("/verify")
    public HttpEntity<?> verifyEmail(@RequestParam Integer emailCode, @RequestParam String sendingEmail, HttpServletRequest request){
        ApiResponse apiResponse = authService.verifyEmail(sendingEmail, emailCode, request);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse login = authService.login(loginDto);
        return ResponseEntity.status(login.isSuccess()?200:401).body(login);
    }
}
