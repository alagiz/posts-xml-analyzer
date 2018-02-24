package com.xml.analyzer;

import com.xml.analyzer.controller.XmlAnalyzerController;
import com.xml.analyzer.parser.XmlParser.ParseException;
import com.xml.analyzer.result.Result;
import com.xml.analyzer.result.posts.PostsResultDetails;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockserver.client.server.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.xml.analyzer.PostsXmlAnalyzerTestConfiguration.class)
public class PostsXmlAnalyzerTest {
    private static MockServerClient mockServerClient;

    @BeforeClass
    public static void startServer() {
        mockServerClient = startClientAndServer(1070);

        mockServerClient
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

        mockServerClient
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/malformed-xml")
                )
                .respond(
                        response()
                                .withBody("<pos")
                );
    }

    @Autowired
    private XmlAnalyzerController xmlAnalyzerController;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturnCorrectResultWhenAnalyzePostsIsCalled() throws ParseException {
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

    @Test
    public void shouldThrowParseExceptionWithXmlMalformedMessageWhenXmlIsMalformed() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(containsString("XML document structures must start and end within the same entity"));

        xmlAnalyzerController.analyzePosts("http://127.0.0.1:1070/malformed-xml");
    }

    @Test
    public void shouldThrowParseExceptionWithFileNotFoundMessageWhenLinkIsWrong() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(containsString("FileNotFoundException"));

        xmlAnalyzerController.analyzePosts("http://127.0.0.1:1070/wrong-link");
    }

    @AfterClass
    public static void stopServer() {
        mockServerClient.stop();
    }
}