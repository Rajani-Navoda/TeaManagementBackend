package com.TeaManagement.TeaManagement.service;

import com.TeaManagement.TeaManagement.dao.RoleDao;
import com.TeaManagement.TeaManagement.dao.UserDao;
import com.TeaManagement.TeaManagement.dto.DepartmentDto;
import com.TeaManagement.TeaManagement.dto.EmployeeDto;
import com.TeaManagement.TeaManagement.entity.Role;
import com.TeaManagement.TeaManagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

public interface UserService {

    public User registerNewUser(User user);

    public void initRolesAndUser();

    public String getEncodedPassword(String password);

    public EmployeeDto updateEmployee(String empNo, EmployeeDto employeeDto);

    public String deleteCustomer(String empNo);


}
