package org.week8.net_ex.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * GitHub 사용자 정보를 담는 레코드 클래스 (Java 16+)
 * @param login 사용자 로그인 이름
 * @param avatarUrl 아바타 이미지 URL
 * @param htmlUrl GitHub 프로필 URL
 * @param publicRepos 공개 저장소 수
 * @param createdAt 계정 생성 날짜
 */
public record GitHubUser(
        String login,
        @JsonProperty("avatar_url")     String avatarUrl,
        @JsonProperty("html_url")       String htmlUrl,
        @JsonProperty("public_repos")   int publicRepos,
        @JsonProperty("created_at")     String createdAt
) {
}

/*
// 위의 레코드와 동일한 기능을 하는 일반 클래스 버전
public final class GitHubUser {
    private final String login;
    @JsonProperty("avatar_url")
    private final String avatarUrl;
    @JsonProperty("html_url")
    private final String htmlUrl;
    @JsonProperty("public_repos")
    private final int publicRepos;
    @JsonProperty("created_at")
    private final String createdAt;

    public GitHubUser(
            String login,
            @JsonProperty("avatar_url") String avatarUrl,
            @JsonProperty("html_url") String htmlUrl,
            @JsonProperty("public_repos") int publicRepos,
            @JsonProperty("created_at") String createdAt
    ) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
        this.publicRepos = publicRepos;
        this.createdAt = createdAt;
    }

    public String login() {
        return login;
    }

    @JsonProperty("avatar_url")
    public String avatarUrl() {
        return avatarUrl;
    }

    @JsonProperty("html_url")
    public String htmlUrl() {
        return htmlUrl;
    }

    @JsonProperty("public_repos")
    public int publicRepos() {
        return publicRepos;
    }

    @JsonProperty("created_at")
    public String createdAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (GitHubUser) obj;
        return Objects.equals(this.login, that.login) &&
                Objects.equals(this.avatarUrl, that.avatarUrl) &&
                Objects.equals(this.htmlUrl, that.htmlUrl) &&
                this.publicRepos == that.publicRepos &&
                Objects.equals(this.createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, avatarUrl, htmlUrl, publicRepos, createdAt);
    }

    @Override
    public String toString() {
        return "GitHubUser[" +
                "login=" + login + ", " +
                "avatarUrl=" + avatarUrl + ", " +
                "htmlUrl=" + htmlUrl + ", " +
                "publicRepos=" + publicRepos + ", " +
                "createdAt=" + createdAt + ']';
    }
}
 */