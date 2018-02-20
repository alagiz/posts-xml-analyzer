package com.xml.analyzer.parser.posts.sax;

import com.xml.analyzer.node.XmlNode;
import com.xml.analyzer.result.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.parsers.SAXParserFactory;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SaxXmlParserTest {
    @InjectMocks
    private SaxXmlParser saxXmlParser;

    @Mock
    private SaxXmlHandler saxXmlHandler;

    @Mock
    private XmlNode xmlNode;

    @Mock
    private Result result;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        when(saxXmlParser.parseXMLFromUrl(anyString(), any(), any())).thenAnswer(invocationOnMock -> result);
    }

    @Test
    public void testParseXMLFromUrl() {
        assertTrue(saxXmlParser.parseXMLFromUrl("test", xmlNode, result) != null);
    }
}