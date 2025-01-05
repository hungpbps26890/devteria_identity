package com.dev.identity.controller;

import com.dev.identity.dto.request.PermissionRequest;
import com.dev.identity.dto.response.ApiResponse;
import com.dev.identity.dto.response.PermissionResponse;
import com.dev.identity.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> create(
            @RequestBody PermissionRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<PermissionResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("Create permission successfully")
                        .data(permissionService.create(request))
                        .build());
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(HttpStatus.OK.value())
                .data(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{name}")
    public ApiResponse<Void> delete(@PathVariable String name) {
        permissionService.delete(name);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Delete permission successfully")
                .build();
    }
}
