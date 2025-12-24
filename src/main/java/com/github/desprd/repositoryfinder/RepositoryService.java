package com.github.desprd.repositoryfinder;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryService {

    private final GitHubClient gitHubClient;

    public RepositoryService(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public List<RepositoryResponse> getRepositoryResponseList(String username) {
        List<RepositoryResponse> responses = new ArrayList<>();
        List<Repository> filteredRepositories = getFilteredRepositories(username);
        for (Repository repository: filteredRepositories) {
            RepositoryResponse response = generateRepositoryResponse(repository);
            responses.add(response);
        }
        return responses;
    }

    private List<Repository> getFilteredRepositories(String username) {
        return gitHubClient.getAllRepos(username)
                .stream()
                .filter(repository -> !repository.isFork())
                .toList();
    }

    private RepositoryResponse generateRepositoryResponse(Repository repository) {
        List<Branch> branches = gitHubClient.getRepositoryBranches(repository.owner().login(), repository.name());
        return new RepositoryResponse(repository.name(), repository.owner().login(), branches);
    }

}
