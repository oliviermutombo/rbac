package com.example.rbac.services;

import com.example.rbac.models.UserTask;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    public List <UserTask> retrieveTasks(String employeeNumber, Map < String, List <SimpleGrantedAuthority>> businessUnitRoles) {
        // Your logic to retrieve tasks based on business unit roles
        // Example: Retrieve tasks for each business unit and role combination
        for (Map.Entry < String, List < SimpleGrantedAuthority >> entry: businessUnitRoles.entrySet()) {
            String businessUnit = entry.getKey();
            List < SimpleGrantedAuthority > roles = entry.getValue();

            // Process tasks for the business unit and roles
            // Example: taskRepository.findTasksByBusinessUnitAndRoles(businessUnit, roles);
        }
        // Combine results and return
        //return taskRepository.findTasksByEmployeeNumber(employeeNumber);
        return getCCTasks();
    }

    public List<UserTask> getCCTasks() { //TODO - Change back to private
        List<UserTask> userTasks = new ArrayList<>();
        userTasks.add(new UserTask("CC Task 1"));
        userTasks.add(new UserTask("CC Task 2"));
        return userTasks;
    }
}
