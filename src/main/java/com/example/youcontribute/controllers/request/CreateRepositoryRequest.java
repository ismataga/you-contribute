package com.example.youcontribute.controllers.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRepositoryRequest {
    private String organization;
    private String repository;
}
