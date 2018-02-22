package com.xml.analyzer.controller;

import com.xml.analyzer.parser.XmlParser;
import com.xml.analyzer.parser.XmlParser.ParseException;
import com.xml.analyzer.parser.posts.PostsXmlParser;
import com.xml.analyzer.result.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class XmlAnalyzerControllerTest {
    @InjectMocks
    private XmlAnalyzerController xmlAnalyzerController;

    @Mock
    private PostsXmlParser postsXmlParser;

    @Mock
    private Result result;

    @Before
    public void init() throws XmlParser.ParseException {
        MockitoAnnotations.initMocks(this);

        when(postsXmlParser.parseXMLFromUrl(anyString())).thenAnswer(invocationOnMock -> result);
    }

    @Test
    public void testAnalyzePosts() throws ParseException {
        assertTrue(xmlAnalyzerController.analyzePosts("test") != null);
    }
}