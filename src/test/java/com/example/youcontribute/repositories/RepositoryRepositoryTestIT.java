package com.example.youcontribute.repositories;

import com.example.youcontribute.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(initializers = RepositoryRepositoryTestIT.Initializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("it")
@Testcontainers
public class RepositoryRepositoryTestIT {

    public static final DockerImageName MYSQL_LATEST_IMAGE = DockerImageName.parse("mysql:latest");
    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>(MYSQL_LATEST_IMAGE);
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private RepositoryRepository repositoryRepository;

    @Test
    public void it_should_find_all_repositories() {
        //given
        Repository repository = Repository.builder().organization("org1").repository("repo1").build();
        Repository repository2 = Repository.builder().organization("org12").repository("repo12").build();

        this.repositoryRepository.saveAll(Arrays.asList(repository, repository2));
        this.testEntityManager.flush();

        //when
        List<Repository> repos = this.repositoryRepository.findAll();

        //then
        then(repos.size()).isEqualTo(2);
        Repository repo = repos.get(0);
        Repository repo1 = repos.get(1);
        then(repo.getRepository()).isEqualTo("repo1");
        then(repo.getOrganization()).isEqualTo("org1");
        then(repo1.getRepository()).isEqualTo("repo12");
        then(repo1.getOrganization()).isEqualTo("org12");

    }


    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of("spring.datasource.url=" + mysql.getJdbcUrl(),
                            "spring.datasource.username=" + mysql.getUsername(),
                            "spring.datasource.password=" + mysql.getPassword())
                    .applyTo(applicationContext.getEnvironment());
        }
    }

}