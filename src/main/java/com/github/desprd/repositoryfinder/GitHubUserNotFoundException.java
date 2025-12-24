package com.github.desprd.repositoryfinder;

public class GitHubUserNotFoundException extends RuntimeException{
    public GitHubUserNotFoundException(String message) {
        super(message);
    }
}
