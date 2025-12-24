package com.github.desprd.repositoryfinder;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(
        url = "https://api.github.com",
        accept = "application/json"
)
public interface GitHubClient {
    @GetExchange("/users/{username}/repos")
    List<Repository> getAllRepos(@PathVariable String username);

    @GetExchange("/repos/{username}/{repositoryName}/branches")
    List<Branch> getRepositoryBranches(@PathVariable String username, @PathVariable String repositoryName);
}