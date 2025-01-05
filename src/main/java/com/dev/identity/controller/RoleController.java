package com.dev.identity.controller;

import com.dev.identity.dto.request.RoleRequest;
import com.dev.identity.dto.response.ApiResponse;
import com.dev.identity.dto.response.RoleResponse;
import com.dev.identity.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> create(
            @RequestBody RoleRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<RoleResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("Create role successfully")
                        .data(roleService.create(request))
                        .build());
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(HttpStatus.OK.value())
                .data(roleService.getAll())
                .build();
    }


    @DeleteMapping("/{name}")
    public ApiResponse<Void> delete(@PathVariable String name) {
        roleService.delete(name);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Delete role successfully")
                .build();
    }
}
