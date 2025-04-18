package com.TeaManagement.TeaManagement.dto;

import com.TeaManagement.TeaManagement.entity.enums.Teatime;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ActiveSessionDto {

    private Boolean isActive;
    private Teatime teatime;
    private long remainingTime;

}
