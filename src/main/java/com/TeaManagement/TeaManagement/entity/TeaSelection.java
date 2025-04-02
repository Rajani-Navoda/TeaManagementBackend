package com.TeaManagement.TeaManagement.entity;

import com.TeaManagement.TeaManagement.entity.enums.Teatime;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tea_selection")
public class TeaSelection {

    @Id
    @Column(name = "selection_id", length=45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int selectionId;

    @Column(name = "selection_time")
    private LocalDateTime localDateTime;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_no", referencedColumnName = "emp_no")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bvg_id", referencedColumnName = "bvg_id")
    private TeaOptions teaOptions;

    @Enumerated(EnumType.STRING)
    @Column(name = "tea_time")
    private Teatime teatime;

}
