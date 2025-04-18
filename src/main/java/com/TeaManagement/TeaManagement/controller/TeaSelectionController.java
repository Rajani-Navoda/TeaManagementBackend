package com.TeaManagement.TeaManagement.controller;

import com.TeaManagement.TeaManagement.dto.ActiveSessionDto;
import com.TeaManagement.TeaManagement.dto.CountByBeverageDto;
import com.TeaManagement.TeaManagement.dto.CountByDepartmentDto;
import com.TeaManagement.TeaManagement.dto.TeaSelectionDto;
import com.TeaManagement.TeaManagement.entity.enums.Teatime;
import com.TeaManagement.TeaManagement.service.TeaSelectionService;
import com.TeaManagement.TeaManagement.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/teaSelection")
@CrossOrigin(origins = "http://localhost:4200")
public class TeaSelectionController {

    @Autowired
    private TeaSelectionService teaSelectionService;

    @PostMapping("/selectTeaOption")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<StandardResponse> selectTeaOption(@RequestBody TeaSelectionDto teaSelectionDto){
        String selectTea = teaSelectionService.saveTeaSelectionOption(teaSelectionDto);
        return new ResponseEntity<>(new StandardResponse(201, "Success", selectTea), HttpStatus.CREATED);
    }

    @GetMapping(
            path = "/sessionCountsByBeverage",
            params = "teaTime"
    )
    @PreAuthorize("hasRole('Admin')")
    public List<CountByBeverageDto> getSessionBeverageCount (@RequestParam(value = "teaTime") Teatime teaTime){
        return teaSelectionService.countByBeverages(teaTime);
    }


    @GetMapping(
            path = "/sessionCountByDepartmentAndBeverage",
            params = "teaTime"
    )
    @PreAuthorize("hasRole('Admin')")
    public List<CountByDepartmentDto> getSessionCountByDepartmentSAndBeverage (@RequestParam(value="teaTime") Teatime teaTime){
        return teaSelectionService.countByDepartmentSAndBeverage(teaTime);
    }

    @GetMapping(
            path = "/current-session-status"
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ActiveSessionDto getCurrentSessionStatus(){
        ActiveSessionDto status = teaSelectionService.getCurrentSessionStatus();
        return status;
    }

}
