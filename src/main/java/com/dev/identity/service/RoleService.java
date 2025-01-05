package com.dev.identity.service;

import com.dev.identity.dto.request.RoleRequest;
import com.dev.identity.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    void delete(String name);
}
