package com.github.desprd.repositoryfinder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
class RepositoryService {

    private final GitHubClient gitHubClient;

    RepositoryService(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    List<RepositoryResponse> getRepositoryResponseList(String username) {
        List<RepositoryResponse> responses = new ArrayList<>();
        List<Repository> filteredRepositories = getFilteredRepositories(username);
        for (Repository repository: filteredRepositories) {
            RepositoryResponse response = generateRepositoryResponse(repository);
            responses.add(response);
        }
        return responses;
    }

    private List<Repository> getFilteredRepositories(String username) {
        try {
            return gitHubClient.getAllRepos(username)
                    .stream()
                    .filter(repository -> !repository.isFork())
                    .toList();
        } catch (HttpClientErrorException.NotFound e) {
            throw new GitHubUserNotFoundException("User with username %s was not found".formatted(username));
        }
    }

    private RepositoryResponse generateRepositoryResponse(Repository repository) {
        List<Branch> branches = gitHubClient.getRepositoryBranches(repository.owner().login(), repository.name());
        return new RepositoryResponse(repository.name(), repository.owner().login(), branches);
    }

}
