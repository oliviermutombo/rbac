package com.example.rbac.services;

import com.example.rbac.models.UserTask;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    /**
     * This is just an example to demonstrate that roles do not have to be implemented in the service layer.
     * However, if need be, business unit permissions can be passed to the service layer from the controller depending on the business logic.
     * The employee number should also be passed from the controller
     *
     * @param employeeNumber
     * @param businessUnitRoles
     * @return
     */
    public List <UserTask> retrieveTasks(String employeeNumber, Map < String, List <SimpleGrantedAuthority>> businessUnitRoles) {
        List<UserTask> userTasks = new ArrayList<>();
        for (Map.Entry < String, List < SimpleGrantedAuthority >> entry: businessUnitRoles.entrySet()) {
            String businessUnit = entry.getKey();
            List < SimpleGrantedAuthority > roles = entry.getValue(); // Roles can still be accessed here if needed but this is not advised.

            if (businessUnit.equalsIgnoreCase("CC")) {
                userTasks.add(new UserTask("CC Task 1"));
                userTasks.add(new UserTask("CC Task 2"));
            }
            if (businessUnit.equalsIgnoreCase("RAS")) {
                userTasks.add(new UserTask("RAS Task 1"));
                userTasks.add(new UserTask("RAS Task 2"));
            }
        }
        return userTasks;
    }
}
