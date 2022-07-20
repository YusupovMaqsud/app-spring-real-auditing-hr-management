package uz.pdp.appspringrealauditinghrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appspringrealauditinghrmanagement.payload.ApiResponse;
import uz.pdp.appspringrealauditinghrmanagement.service.EmployeeSalaryService;

@RestController
@RequestMapping(value = "/api/salary")
public class EmployeeSalaryController {
    @Autowired
    EmployeeSalaryService employeeSalaryService;

    @PreAuthorize("hasRole('DIRECTOR')")
    @GetMapping("/payment")
    public HttpEntity<?> paymentSalary(){
        ApiResponse payment = employeeSalaryService.payment();
        return ResponseEntity.status(payment.isSuccess()?201:409).body(payment);
    }
}
