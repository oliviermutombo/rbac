package com.example.rbac.controllers;

import com.example.rbac.config.CustomUserDetails;
import com.example.rbac.models.UserTask;
import com.example.rbac.services.TaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/rbac")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/test")
    @ResponseBody
    boolean test() {
        return true;
    }
    public HashMap<String, String> rolesFor = new HashMap<>() {{
        put("getRead", "ROLE_Read");
        put("getWrite", "ROLE_Write");
    }};

    @PreAuthorize("hasAnyRole('ROLE_Read', 'ROLE_Write')") // VALID
    //@PreAuthorize("hasAnyRole(this.rolesFor.get('getRead'))") // ALSO VALID
    @GetMapping("/tasks")
    @ResponseBody
    public List<UserTask> getTasks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            String employeeNumber = userDetails.getEmployeeNumber();
            Map< String, List <SimpleGrantedAuthority>> businessUnitRoles = userDetails.getBusinessUnitRoles();
            return taskService.retrieveTasks(employeeNumber, businessUnitRoles);
        }
        return Collections.emptyList();
    }
}
