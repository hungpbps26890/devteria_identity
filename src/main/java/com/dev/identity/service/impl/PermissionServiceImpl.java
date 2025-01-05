package com.dev.identity.service.impl;

import com.dev.identity.dto.request.PermissionRequest;
import com.dev.identity.dto.response.PermissionResponse;
import com.dev.identity.entity.Permission;
import com.dev.identity.mapper.PermissionMapper;
import com.dev.identity.repository.PermissionRepository;
import com.dev.identity.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;

    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permissionToCreate = permissionMapper.toPermission(request);

        Permission savedPermission = permissionRepository.save(permissionToCreate);

        return permissionMapper.toPermissionResponse(savedPermission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        return permissionRepository
                .findAll()
                .stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    @Override
    public void delete(String name) {
        permissionRepository.deleteById(name);
    }
}
