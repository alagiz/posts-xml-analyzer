package com.xml.analyzer.posts.controller;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.verify.VerificationTimes;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.StringBody.exact;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.mockserver.model.Header;

import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpClassCallback.callback;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;

@RunWith(SpringJUnit4ClassRunner.class)
public class PostsControllerTest {
    private static ClientAndServer mockServer;
    private PostsController postsController;

    @BeforeClass
    public static void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @Before
    public void setUp() throws Exception {
        postsController = new PostsController();
    }

    @Test
    public void whenPostRequestMockServer_thenServerReceived() {
        createExpectationForValidXml();

//        postsController.analyzePostsWithSax("localhost:1080");

        org.apache.http.HttpResponse response = hitTheServerWithPostRequest();
        assertEquals(401, response.getStatusLine().getStatusCode());
    }

    private void verifyPostRequest() {
        new MockServerClient("localhost", 1080).verify(
                request()
                        .withMethod("POST")
                        .withPath("/analyze")
                        .withBody(exact("{url: 'test-url'}")),
                VerificationTimes.exactly(1)
        );
    }

    private org.apache.http.HttpResponse hitTheServerWithPostRequest() {
        String url = "http://127.0.0.1:1080/validate";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        org.apache.http.HttpResponse response = null;

        post.setHeader("Content-type", "application/json");

        try {
            StringEntity stringEntity = new StringEntity("{url: 'test-url'}");
            post.getRequestLine();
            post.setEntity(stringEntity);
            response = client.execute(post);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private void createExpectationForValidXml() {
        new MockServerClient("127.0.0.1", 1080)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/validate")
                                .withHeader("\"Content-type\", \"application/json\"")
                                .withBody(exact("<posts>" +
                                        "<row Id=/>" +
                                        "</posts>")),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(401)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody("{ message: 'incorrect username and password combination' }")
                                .withDelay(TimeUnit.SECONDS,1)
                );
    }

    @Test
    public void testAnalyze() throws Exception {
        String json = "{\n" +
                "  \"url\": \"https://s3-eu-west-1.amazonaws.com/merapar-assessment/3dprinting-posts.parser\",\n" +
                "}";
//        mockMvc.perform(post("/analyze")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title", Matchers.is("Greetings")))
//                .andExpect(jsonPath("$.value", Matchers.is("Hello World")))
//                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
    }

}