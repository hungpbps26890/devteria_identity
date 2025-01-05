package com.dev.identity.service;

import com.dev.identity.dto.request.PermissionRequest;
import com.dev.identity.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    void delete(String name);
}
