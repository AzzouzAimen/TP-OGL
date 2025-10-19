package test;

import com.example.model.GitHubResponse;
import com.example.service.Endpoint;
import com.example.service.UserServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceClientTest {

    private UserServiceClient userServiceClient;
    @Mock
    private Endpoint endpoint;
    @Mock
    private HttpResponse<String> mockHttpResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        // Create an instance of the class
        userServiceClient = new UserServiceClient();

        // inject the mocked dependency
        userServiceClient.setEndpoint(endpoint);
    }

    @Test
    void testGetUser_WhenApiCallIsSuccessful_ShouldReturnGitHubResponse() throws URISyntaxException, IOException, InterruptedException {
        String username = "testuser";
        String url = "https://api.github.com/users/" + username;

        // mock JSON data
        String fakeJsonResponse = "{\"login\":\"testuser\",\"id\":12345,\"name\":\"Test User\"}";

        // Configure the mock HttpResponse
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(fakeJsonResponse);

        // it should return configured mockHttpResponse.
        when(endpoint.getResponse(url)).thenReturn(mockHttpResponse);

        // Call the method
        GitHubResponse result = userServiceClient.getUser(url);

        // verify  the method on our mock was  called
        verify(endpoint).getResponse(url);

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getStatusCode());
        assertNotNull(result.getGitHubUser());
        assertEquals("testuser", result.getGitHubUser().getLogin());
        assertEquals(12345, result.getGitHubUser().getId());
        assertEquals("Test User", result.getGitHubUser().getName());
    }

    @Test
    void testGetUser_WhenApiReturnsError_ShouldReturnErrorStatusCode() throws URISyntaxException, IOException, InterruptedException {
        String username = "nonexistentuser";
        String url = "https://api.github.com/users/" + username;
        String fakeErrorResponse = "{\"message\":\"Not Found\"}";

        // Configure the mock HttpResponse for an error case (e.g., 404 Not Found)
        when(mockHttpResponse.statusCode()).thenReturn(404);
        when(mockHttpResponse.body()).thenReturn(fakeErrorResponse);

        when(endpoint.getResponse(url)).thenReturn(mockHttpResponse);
        GitHubResponse result = userServiceClient.getUser(url);

        // assert
        verify(endpoint).getResponse(url);
        assertNotNull(result);
        assertEquals(404, result.getStatusCode());
    }
}