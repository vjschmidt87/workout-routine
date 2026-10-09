package com.workoutroutine.service;

import com.workoutroutine.dto.EquipmentDto;
import com.workoutroutine.model.Equipment;
import com.workoutroutine.model.User;
import com.workoutroutine.repository.EquipmentRepository;
import com.workoutroutine.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    public EquipmentService(EquipmentRepository equipmentRepository, UserRepository userRepository) {
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
    }

    public List<EquipmentDto> listForUser(Long userId) {
        return equipmentRepository.findAllByUserId(userId).stream()
                .map(e -> new EquipmentDto(e.getId(), e.getName(), e.getCategory()))
                .toList();
    }

    public EquipmentDto add(Long userId, EquipmentDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Equipment equipment = new Equipment(user, dto.name(), dto.category());
        Equipment saved = equipmentRepository.save(equipment);
        return new EquipmentDto(saved.getId(), saved.getName(), saved.getCategory());
    }

    public void delete(Long userId, Long equipmentId) {
        Equipment equipment = equipmentRepository.findByIdAndUserId(equipmentId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment not found"));
        equipmentRepository.delete(equipment);
    }
}
