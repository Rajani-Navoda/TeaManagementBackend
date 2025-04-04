package com.TeaManagement.TeaManagement.dao;

import com.TeaManagement.TeaManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<Department, Integer> {
    Department findByDeptName(String deptName);

}
