package com.xml.analyzer;

import com.xml.analyzer.controller.XmlAnalyzerController;
import com.xml.analyzer.result.Result;
import com.xml.analyzer.result.posts.PostsResultDetails;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.xml.analyzer.PostsXmlAnalyzerTestConfiguration.class)
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
                                        "<row" +
                                        " Id=\"0\"" +
                                        " AnswerCount=\"5\"" +
                                        " AcceptedAnswerId=\"2\"" +
                                        " CommentCount=\"50\"" +
                                        " ViewCount=\"100\"" +
                                        " Score=\"15\"" +
                                        " CreationDate=\"1990-01-01T00:00:00.000\" " +
                                        "/>" +
                                        "<row " +
                                        " Id=\"1\"" +
                                        " AnswerCount=\"15\"" +
                                        " CommentCount=\"10\"" +
                                        " ViewCount=\"200\"" +
                                        " Score=\"25\"" +
                                        " CreationDate=\"2000-01-01T00:00:00.000\"" +
                                        "/>" +
                                        "</posts>")
                );
    }

    @Autowired
    private XmlAnalyzerController xmlAnalyzerController;

    @Test
    public void testAnalyzePosts() {
        Result result = xmlAnalyzerController.analyzePosts("http://127.0.0.1:1070/xml");
        PostsResultDetails details = (PostsResultDetails) result.getResultDetails();

        assertEquals(details.getTotalPosts(), 2);
        assertEquals(details.getTotalAnswers(), 20);
        assertEquals(details.getTotalAcceptedAnswers(), 1);
        assertEquals(details.getTotalComments(), 60);
        assertEquals(details.getTotalViews(), 300);
        assertEquals(details.getFirstPostDate().getYear(), 1990);
        assertEquals(details.getLastPostDate().getYear(), 2000);
    }
}