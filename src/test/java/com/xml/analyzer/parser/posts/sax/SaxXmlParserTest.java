package com.xml.analyzer.parser.posts.sax;

import com.xml.analyzer.node.XmlNode;
import com.xml.analyzer.parser.XmlParser;
import com.xml.analyzer.parser.XmlParser.*;
import com.xml.analyzer.result.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(URL.class)
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
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);

        doNothing().when(saxXmlHandler).setResult(any());
        doNothing().when(saxXmlHandler).setXmlNode(any());

////        when(saxXmlParser).(anyString(), any(), any()))
////                .thenReturn(result);
//
//        SAXParserFactory spf = mock(SAXParserFactory.class);
//        SAXParser sp = mock(SAXParser.class);
//        InputSource is = mock(InputSource.class);
//        URL url = PowerMockito.mock(URL.class);
//
//        InputStream ist = mock(InputStream.class);
//        HttpURLConnection huc = PowerMockito.mock(HttpURLConnection.class);
//        PowerMockito.when(url.openConnection()).thenReturn(huc);
//        PowerMockito.when(huc.getResponseCode()).thenReturn(200);
//
////        when(spf.newInstance()).thenAnswer(invocationOnMock -> spf);
//        when(spf.newSAXParser()).thenReturn(sp);
////        when(new URL(anyString())).thenAnswer(invocationOnMock -> url);
//        when(url.openStream()).thenAnswer(invocationOnMock -> ist);
//        PowerMockito.whenNew(InputSource.class).withArguments(java.io.Reader.class).thenAnswer(invocationOnMock -> ist);
//        PowerMockito.whenNew(URL.class).withArguments(anyString()).thenReturn(url);
////        when(InputSource.class).
//        doNothing().when(sp).parse(is, saxXmlHandler);
    }

    @Test
    public void testParseXMLFromUrl() throws ParseException {
        assertTrue(saxXmlParser.parseXMLFromUrl("http://localhost", xmlNode, result) != null);
    }
}