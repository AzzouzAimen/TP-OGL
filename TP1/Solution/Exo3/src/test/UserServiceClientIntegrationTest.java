package test;

import com.example.model.GitHubResponse;
import com.example.service.Endpoint;
import com.example.service.UserServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserServiceClientIntegrationTest {

    private UserServiceClient userServiceClient;

    @BeforeEach
    void setUp() {
    //real obeject
        userServiceClient = new UserServiceClient();
        userServiceClient.setEndpoint(new Endpoint()); // Injecting a real Endpoint instance
    }

    @Test
    void testGetUser_ShouldReturnActualUserData_FromGitHubApi() throws URISyntaxException, IOException, InterruptedException {
        // Given
        // We use a real GitHub user
        String username = "google";
        String url = "https://api.github.com/users/" + username;
        // HTTP request to GitHub's server
        GitHubResponse result = userServiceClient.getUser(url);
        // assert
        assertEquals(200, result.getStatusCode());
        assertNotNull(result.getGitHubUser());
        assertEquals(username, result.getGitHubUser().getLogin());
        assertNotNull(result.getGitHubUser().getId());
        assertNotNull(result.getGitHubUser().getName());
    }
}