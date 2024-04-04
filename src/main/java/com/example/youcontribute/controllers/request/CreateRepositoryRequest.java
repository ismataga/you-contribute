package com.example.youcontribute.controllers.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class CreateRepositoryRequest {
    private String organization;
    private String repository;
}
