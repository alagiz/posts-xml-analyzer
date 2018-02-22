package com.xml.analyzer.parser.posts;

import com.xml.analyzer.parser.XmlParser.ParseException;
import com.xml.analyzer.parser.posts.sax.SaxXmlParser;
import com.xml.analyzer.result.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostsXmlParserTest {
    @InjectMocks
    private PostsXmlParser postsXmlParser;

    @Mock
    private SaxXmlParser saxXmlParser;

    @Mock
    private Result result;

    @Before
    public void init() throws ParseException {
        MockitoAnnotations.initMocks(this);

        when(saxXmlParser.parseXMLFromUrl(anyString(), any(), any())).thenAnswer(invocationOnMock -> result);
    }

    @Test
    public void testParseXMLFromUrl() throws ParseException {
        assertTrue(postsXmlParser.parseXMLFromUrl("test") != null);
    }
}