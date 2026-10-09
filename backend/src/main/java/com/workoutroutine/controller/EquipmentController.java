package com.workoutroutine.controller;

import com.workoutroutine.config.JwtAuthFilter;
import com.workoutroutine.dto.EquipmentDto;
import com.workoutroutine.service.EquipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public List<EquipmentDto> list() {
        return equipmentService.listForUser(JwtAuthFilter.currentUserId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentDto add(@RequestBody EquipmentDto dto) {
        return equipmentService.add(JwtAuthFilter.currentUserId(), dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        equipmentService.delete(JwtAuthFilter.currentUserId(), id);
    }
}
