package com.example.youcontribute.service;

import com.example.youcontribute.controllers.resource.RepositoryResource;
import com.example.youcontribute.model.Repository;
import com.example.youcontribute.repositories.RepositoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RepositoryService {
    private final RepositoryRepository repositoryRepository;

    @Transactional
    public void create(String organization,String repository) {
        Repository repositoryEntity = Repository.builder()
                .organization(organization)
                .repository(repository)
                .build();
        this.repositoryRepository.save(repositoryEntity);

    }

    public List<Repository> list() {
        return repositoryRepository.findAll();
    }
}
