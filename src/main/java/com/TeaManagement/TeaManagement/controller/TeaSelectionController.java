package com.TeaManagement.TeaManagement.controller;

import com.TeaManagement.TeaManagement.dto.TeaSelectionDto;
import com.TeaManagement.TeaManagement.service.TeaSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/teaSelection")
@CrossOrigin(origins = "http://localhost:4200")
public class TeaSelectionController {

    @Autowired
    private TeaSelectionService teaSelectionService;

    @PostMapping("/selectTeaOption")
    @PreAuthorize("hasRole('User')")
    public String selectTeaOption(@RequestBody TeaSelectionDto teaSelectionDto){
        teaSelectionService.saveTeaSelectionOption(teaSelectionDto);
        return "record saved successfully.";

    }
}
