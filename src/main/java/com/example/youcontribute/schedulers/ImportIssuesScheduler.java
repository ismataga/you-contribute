package com.example.youcontribute.schedulers;

import com.example.youcontribute.manager.RepositoryManager;
import com.example.youcontribute.model.Repository;
import com.example.youcontribute.service.RepositoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class ImportIssuesScheduler {

    private final RepositoryManager repositoryManager;
    private final RepositoryService repositoryService;
    @Scheduled(fixedRateString = "${application.import-frequency}", initialDelay = 60000)
    public  void  importIssuesScheduler(){
        log.info("ImportIssuesScheduler  started");
        List<Repository> repositories= this.repositoryService.list();
        repositories.forEach(this.repositoryManager::importIssue);
        log.info("ImportIssuesScheduler  finished");


    }
}
