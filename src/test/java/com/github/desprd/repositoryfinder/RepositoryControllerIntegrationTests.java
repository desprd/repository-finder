package com.github.desprd.repositoryfinder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@EnableWireMock({
        @ConfigureWireMock(name = "github-api", baseUrlProperties = "github.api.url")
})
class RepositoryControllerIntegrationTests {

    @Autowired
    RestTestClient client;

    String path = "/api/v1/repos/username";

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void existingGitHubUser_getUserRepositoriesInfo_returnRepositoryResponse() throws Exception{
        // Given
        String repositoriesJson = """
                [
                  {"name":"Repo1","fork":false,"owner":{"login":"username"}},
                  {"name":"Repo2","fork":true,"owner":{"login":"username"}},
                  {"name":"Repo3","fork":false,"owner":{"login":"username"}}
                ]
                """;
        String firstRepoBranchesJson = """
                [
                  {"name":"master","commit":{"sha":"12345"}},
                  {"name":"new-feature","commit":{"sha":"6789"}}
                ]
                """;
        String thirdRepoBranchesJson = """
                [
                  {"name":"master","commit":{"sha":"12345"}}
                ]
                """;
        List<Branch> firstRepoBranches = List.of(
                new Branch("master", new Branch.LastCommit("12345")),
                new Branch("new-feature", new Branch.LastCommit("6789"))
        );
        List<Branch> thirdRepoBranches = List.of(
                new Branch("master", new Branch.LastCommit("12345"))
        );
        List<RepositoryResponse> expectedResponse = List.of(
                new RepositoryResponse("Repo1", "username", firstRepoBranches),
                new RepositoryResponse("Repo3", "username", thirdRepoBranches)
        );

        // When
        stubFor(get("/users/username/repos").willReturn(okJson(repositoriesJson)));
        stubFor(get("/repos/username/Repo1/branches").willReturn(okJson(firstRepoBranchesJson)));
        stubFor(get("/repos/username/Repo3/branches").willReturn(okJson(thirdRepoBranchesJson)));

        // Then
        client.get()
              .uri(path)
              .exchange()
              .expectStatus()
              .is2xxSuccessful()
              .expectBody()
              .json(mapper.writeValueAsString(expectedResponse));
    }

    @Test
    void nonExistingGitHubUser_getUserRepositoriesInfo_returnErrorResponse() throws Exception {
        // Given
        ErrorResponse errorResponse = new ErrorResponse(
                404,
                "User with username username was not found"
        );
        String errorResponseJson = mapper.writeValueAsString(errorResponse);

        // When
        stubFor(get("/users/username/repos").willReturn(notFound()));

        // Then
        client.get()
                .uri(path)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody()
                .json(errorResponseJson);
    }

}
