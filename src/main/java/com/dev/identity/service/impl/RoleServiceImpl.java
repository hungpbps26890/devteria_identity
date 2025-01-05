package com.dev.identity.service.impl;

import com.dev.identity.dto.request.RoleRequest;
import com.dev.identity.dto.response.RoleResponse;
import com.dev.identity.entity.Permission;
import com.dev.identity.entity.Role;
import com.dev.identity.mapper.RoleMapper;
import com.dev.identity.repository.PermissionRepository;
import com.dev.identity.repository.RoleRepository;
import com.dev.identity.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    PermissionRepository permissionRepository;

    RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request) {
        Role roleToCreate = roleMapper.toRole(request);

        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());

        roleToCreate.setPermissions(new HashSet<>(permissions));

        Role savedRole = roleRepository.save(roleToCreate);

        return roleMapper.toRoleResponse(savedRole);
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository
                .findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public void delete(String name) {
        roleRepository.deleteById(name);
    }
}
