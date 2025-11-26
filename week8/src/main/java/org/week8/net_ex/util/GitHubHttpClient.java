package org.week8.net_ex.util;

import org.week8.net_ex.component.NetApp;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;


public class GitHubHttpClient {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // GitHub API 기본 URL
    private static final String BASE_URL = "https://api.github.com";

    // HttpClient (HTTP 요청을 보내는 객체)
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .executor(NetApp.executors) // 공유된 스레드 풀 사용
            .build();

    /**
     * GitHub REST API로부터 사용자 정보를 가져옵니다.
     * @param username GitHub 사용자 이름
     * @return 사용자 정보
     */
    public static Optional<GitHubUser> fetchUser(String username) {
        // GitHub REST API로 보낼 HTTP 요청 작성
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .headers("Accept", "application/vnd.github+json")
                .uri(URI.create(BASE_URL+"/users/"+username))
                .build();

        try {
            // HTTP 요청 보내기
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 404 응답이라면 오류
            if (response.statusCode() == 404) {
                System.out.println("User not found: " + username);
                return Optional.empty();
            }

            // 404 아니면 200임 (공식 문서 왈)

            // 응답 본문을 GitHubUser 객체로 변환
            GitHubUser user = objectMapper.readValue(response.body(), GitHubUser.class);
            System.out.println("Successfully read user: " + user);
            return Optional.of(user);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
