package com.example.model;

public class GitHubResponse {

    private int statusCode;
    private GitHubUser gitHubUser;



    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public GitHubUser getGitHubUser() {
        return gitHubUser;
    }

    public void setGitHubUser(GitHubUser gitHubUser) {
        this.gitHubUser = gitHubUser;
    }
}
