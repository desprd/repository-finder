package com.github.desprd.repositoryfinder;

import java.util.List;

public record RepositoryResponse(
        String repositoryName,
        String ownerLogin,
        List<Branch> branches
) {
}
