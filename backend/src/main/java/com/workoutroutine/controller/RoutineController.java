package com.workoutroutine.controller;

import com.workoutroutine.config.JwtAuthFilter;
import com.workoutroutine.dto.RoutineDto;
import com.workoutroutine.dto.RoutineRequest;
import com.workoutroutine.service.RoutineService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routines")
public class RoutineController {

    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @GetMapping
    public List<RoutineDto> list() {
        return routineService.listForUser(JwtAuthFilter.currentUserId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoutineDto create(@RequestBody RoutineRequest req) {
        return routineService.create(JwtAuthFilter.currentUserId(), req);
    }

    @GetMapping("/{id}")
    public RoutineDto get(@PathVariable Long id) {
        return routineService.get(JwtAuthFilter.currentUserId(), id);
    }

    @PutMapping("/{id}")
    public RoutineDto update(@PathVariable Long id, @RequestBody RoutineRequest req) {
        return routineService.update(JwtAuthFilter.currentUserId(), id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        routineService.delete(JwtAuthFilter.currentUserId(), id);
    }

    @PatchMapping("/{id}/activate")
    public RoutineDto activate(@PathVariable Long id) {
        return routineService.activate(JwtAuthFilter.currentUserId(), id);
    }
}
