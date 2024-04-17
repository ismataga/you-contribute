package com.example.youcontribute.service;

import com.example.youcontribute.controllers.resource.RepositoryResource;
import com.example.youcontribute.exceptions.DuplicatedRepositoryException;
import com.example.youcontribute.model.Repository;
import com.example.youcontribute.repositories.RepositoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RepositoryService {
    private final RepositoryRepository repositoryRepository;

    @Transactional
    public void create(String organization, String repository) {
        this.validate(organization, repository);
        Repository repositoryEntity = Repository.builder()
                .organization(organization)
                .repository(repository)
                .updatedAt(null)
                .build();
        this.repositoryRepository.save(repositoryEntity);

    }

    public List<Repository> list() {
        return repositoryRepository.findAll();
    }

    private void validate(String organization, String repository) {
        this.repositoryRepository.findByOrganizationAndRepository(organization, repository).
                ifPresent((r) -> {
                    throw new DuplicatedRepositoryException(organization, repository);
                });
    }

    public Repository findById(Integer repositoryId) {
        return this.repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Repository: %d not found",repositoryId)));
    }
}
