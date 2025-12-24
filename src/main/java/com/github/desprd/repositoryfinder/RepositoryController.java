package com.github.desprd.repositoryfinder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repos")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<RepositoryResponse>> getUserRepositoriesInfo(@PathVariable String username) {
        List<RepositoryResponse> responses = repositoryService.getRepositoryResponseList(username);
        return ResponseEntity.ok(responses);
    }
}