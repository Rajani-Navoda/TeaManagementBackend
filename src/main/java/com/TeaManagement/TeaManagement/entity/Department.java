package com.TeaManagement.TeaManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "department")
public class Department {

    @Id
    @Column(name = "dept_id", length= 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int deptId;

    @Column(name="dept_name", length=50)
    private String deptName;

}
