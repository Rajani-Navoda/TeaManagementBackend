package com.TeaManagement.TeaManagement.controller;

import com.TeaManagement.TeaManagement.dto.DepartmentDto;
import com.TeaManagement.TeaManagement.dto.EmployeeDto;
import com.TeaManagement.TeaManagement.dto.TeaOptionUpdateDto;
import com.TeaManagement.TeaManagement.dto.TeaOptionsDto;
import com.TeaManagement.TeaManagement.entity.User;
import com.TeaManagement.TeaManagement.service.UserService;
import com.TeaManagement.TeaManagement.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
//@RequestMapping("api/v1/user")
@CrossOrigin(origins = "http://localhost:4200")

public class UserController {
    @Autowired
    private UserService userService;
    @PostConstruct
    public void initRolesAndUsers(){
        userService.initRolesAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user){
        return userService.registerNewUser(user);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "this URL only accessible for the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "this URL only accessible to the employee";
    }

    @PutMapping("/update-employee/{empNo}")
    @PreAuthorize("hasRole('Admin')")
    public EmployeeDto updateEmployee(
            @PathVariable String empNo,
            @RequestBody EmployeeDto employeeDto
    ) {
        return userService.updateEmployee(empNo, employeeDto);
    }

    @DeleteMapping("/delete-Employee/{empNo}")
    @PreAuthorize("hasRole('Admin')")
    public String deleteEmployee(@PathVariable(value = "empNo") String empNo){
        String deletedCustomer = userService.deleteCustomer(empNo);
        return deletedCustomer;
    }

    @GetMapping("/getAllEmployees")
    @PreAuthorize("hasRole('Admin')")
    public List<EmployeeDto> getAllEmployees(){
        List<EmployeeDto> allEmployees = userService.getAllEmployees();
        return allEmployees;
    }



}
