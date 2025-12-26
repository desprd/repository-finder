package com.github.desprd.repositoryfinder;

class GitHubUserNotFoundException extends RuntimeException{
    GitHubUserNotFoundException(String message) {
        super(message);
    }
}
