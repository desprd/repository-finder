package com.github.desprd.repositoryfinder;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Branch(
        String name,
        @JsonProperty("commit")
        LastCommit lastCommit
) {
    public record LastCommit(
            @JsonAlias("sha")
            @JsonProperty("lastCommitSha")
            String sha
    ){}
}