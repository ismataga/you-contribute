package com.example.youcontribute.controllers.request;

import lombok.Data;
import lombok.Getter;

@Data
public class CreateRepositoryRequest {
    private String organization;
    private String repository;
}
