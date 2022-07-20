package uz.pdp.appspringrealauditinghrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringrealauditinghrmanagement.entity.Employee;
import uz.pdp.appspringrealauditinghrmanagement.entity.EmployeeSalary;
import uz.pdp.appspringrealauditinghrmanagement.entity.Task;
import uz.pdp.appspringrealauditinghrmanagement.entity.TourniquetHistory;
import uz.pdp.appspringrealauditinghrmanagement.payload.ApiResponse;
import uz.pdp.appspringrealauditinghrmanagement.payload.TourniquetCardDto;
import uz.pdp.appspringrealauditinghrmanagement.payload.TourniquetHistoryDto;
import uz.pdp.appspringrealauditinghrmanagement.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tourniquethistory")
public class TourniquetHistoryController {
    @Autowired
    TourniquetHistoryService tourniquetHistoryService;



    @PreAuthorize("hasAnyRole('DIRECTOR', 'MANAGER')")
    @GetMapping("/enter")
    public HttpEntity<?> getEnter(@RequestBody TourniquetHistoryDto tourniquetHistoryDto){
        ApiResponse apiResponse = tourniquetHistoryService.enter(tourniquetHistoryDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize("hasAnyRole('DIRECTOR', 'MANAGER')")
    @GetMapping("/exit")
    public HttpEntity<?> getExit(@RequestBody TourniquetHistoryDto tourniquetHistoryDto){
        ApiResponse apiResponse = tourniquetHistoryService.exit(tourniquetHistoryDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
