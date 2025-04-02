package com.TeaManagement.TeaManagement.dto;
import com.TeaManagement.TeaManagement.entity.TeaOptions;
import com.TeaManagement.TeaManagement.entity.User;
import com.TeaManagement.TeaManagement.entity.enums.Teatime;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeaSelectionDto {

    private int selectionId;
    private LocalDateTime localDateTime;
    private User user;
    private TeaOptions teaOptions;
    private Teatime teatime;

}


