package com.example.youcontribute.repositories;

import com.example.youcontribute.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryRepository extends PagingAndSortingRepository<Repository, Integer>, JpaRepository<Repository, Integer> {
    List<Repository> findAll();

    Optional<Repository> findByOrganizationAndRepository(String organization, String repository);
}
