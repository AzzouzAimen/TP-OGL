package com.example.service;

import com.example.model.GitHubResponse;
import com.example.model.GitHubUser;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;


public class UserServiceClient {

    private Endpoint endpoint;


    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public GitHubResponse getUser(String url) throws URISyntaxException, IOException, InterruptedException {

        HttpResponse<String> response =  endpoint.getResponse(url);
        Gson gson = new Gson();
        GitHubUser gitHubUser = gson.fromJson(response.body(),GitHubUser.class);
        GitHubResponse gitHubResponse = new GitHubResponse();
        gitHubResponse.setStatusCode(response.statusCode());
        gitHubResponse.setGitHubUser(gitHubUser);
        return gitHubResponse;

    }



}
