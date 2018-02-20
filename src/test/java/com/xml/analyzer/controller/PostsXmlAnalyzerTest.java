package com.xml.analyzer.controller;

import com.xml.analyzer.result.Result;
import com.xml.analyzer.result.posts.PostsResultDetails;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PostsXmlAnalyzerTestConfiguration.class)
public class PostsXmlAnalyzerTest {
    @BeforeClass
    public static void startServer() {
        new ClientAndServer(1070)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/xml")
                )
                .respond(
                        response()
                                .withBody("<posts>" +
                                        "<row Id=\"1\"/>" +
                                        "</posts>")
                );
    }

    @Autowired
    private XmlAnalyzerController xmlAnalyzerController;

    @Test
    public void testAnalyzePosts() {
        Result result = xmlAnalyzerController.analyzePosts("http://127.0.0.1:1070/xml");

        PostsResultDetails details = (PostsResultDetails)result.getResultDetails();

        System.out.println(details.getTotalPosts());
    }
}