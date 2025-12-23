package com.github.desprd.repositoryfinder;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Repository(
        String name,
        @JsonProperty("owner") Owner owner,
        @JsonProperty("fork") boolean isFork
) {
    public record Owner(String login) {}
}
