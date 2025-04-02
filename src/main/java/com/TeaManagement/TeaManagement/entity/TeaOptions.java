package com.TeaManagement.TeaManagement.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tea_options")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaOptions {

    @Id
    @Column(name = "bvg_id", length= 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int beverageId;

    @Column(name="bvg_name", length=50)
    private String beverageName;

    @Column(name = "unit_price")
    private double unitPrice;



}
