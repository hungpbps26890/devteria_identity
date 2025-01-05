package com.dev.identity.mapper;

import com.dev.identity.dto.request.PermissionRequest;
import com.dev.identity.dto.response.PermissionResponse;
import com.dev.identity.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
