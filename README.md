# repository-finder

Spring Boot API that lists a GitHub user’s repositories which are not forks, together with each
repository’s branches and the last commit SHA for each branch.

## Tech stack

- Java 25
- Spring Boot 4
- Spring WebMVC
- Spring `RestClient` + HTTP interfaces (`@HttpExchange`)
- Integration tests: `@SpringBootTest` + WireMock

## Configuration

The GitHub API base URL is configurable (defaults to the public GitHub API):

- `github.api.url` (default: `https://api.github.com`)

See `src/main/resources/application.yaml`.

## Run locally

```bash
./mvnw spring-boot:run
```

## API

### List repositories (non-forks) with branches

**Request**

```
GET /api/v1/repos/{username}
```

**Example**

```bash
curl -s http://localhost:8080/api/v1/repos/desprd | jq
```

**Response example**

```json
[
  {
    "repositoryName": "Repo1",
    "ownerLogin": "desprd",
    "branches": [
      {
        "name": "master",
        "commit": {
          "lastCommitSha": "d467ecbc851b68567536ae33214916139e66d89a"
        }
      }
    ]
  }
]
```

### User not found

If the GitHub user does not exist, the API returns **404**:

```json
{
  "status": 404,
  "message": "User with username <username> was not found"
}
```

## Tests

Run integration tests (WireMock is used to emulate GitHub API):

```bash
./mvnw test
```
