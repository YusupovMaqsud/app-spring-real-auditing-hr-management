package uz.pdp.appspringrealauditinghrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringrealauditinghrmanagement.entity.Employee;
import uz.pdp.appspringrealauditinghrmanagement.entity.Task;
import uz.pdp.appspringrealauditinghrmanagement.payload.ApiResponse;
import uz.pdp.appspringrealauditinghrmanagement.payload.TaskDto;
import uz.pdp.appspringrealauditinghrmanagement.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/home")
    public HttpEntity<?> TaskController() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = (Employee) authentication.getPrincipal();
        List<Task> taskByEmployee = taskService.getTaskByEmployeeId(employee.getId());
        return ResponseEntity.ok(taskByEmployee);
    }

    @PostMapping("/director")
    public HttpEntity<?> taskDirector(@RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.taskDirector(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PostMapping("/manager")
    public HttpEntity<?> taskManager(@RequestBody TaskDto dto){
        ApiResponse apiResponse = taskService.taskManager(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @GetMapping(value="/accept/{id}")
    public HttpEntity<?> acceptTask(@PathVariable UUID id){
        ApiResponse apiResponse = taskService.acceptTask(id);
        return ResponseEntity.status(apiResponse.isSuccess()?203:409).body(apiResponse);
    }

    @GetMapping(value = "/completed/{id}")
    public HttpEntity<?> completedTask(@PathVariable UUID id,@RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.completed(id, taskDto.getEmployeeId());
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }
}
