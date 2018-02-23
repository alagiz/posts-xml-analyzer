package com.xml.analyzer.parser.posts.sax;

import com.xml.analyzer.node.XmlNode;
import com.xml.analyzer.parser.XmlParser.ParseException;
import com.xml.analyzer.result.Result;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandlerFactory;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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

    private static HttpUrlStreamHandler httpUrlStreamHandler;

    private static void unsetURLStreamHandlerFactory() {
        try {
            Field field = URL.class.getDeclaredField("factory");

            field.setAccessible(true);
            field.set(null, null);

            URL.setURLStreamHandlerFactory(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void clearURLStreamHandlerFactory() {
        unsetURLStreamHandlerFactory();
    }

    @BeforeClass
    public static void setupURLStreamHandlerFactory() {
        unsetURLStreamHandlerFactory();

        URLStreamHandlerFactory urlStreamHandlerFactory = mock(URLStreamHandlerFactory.class);
        httpUrlStreamHandler = new HttpUrlStreamHandler();

        URL.setURLStreamHandlerFactory(urlStreamHandlerFactory);

        given(urlStreamHandlerFactory.createURLStreamHandler("http")).willReturn(httpUrlStreamHandler);
    }

    @Before
    public void reset() {
        httpUrlStreamHandler.resetConnections();
    }

    @Before
    public void init() throws Exception {
        String href = "http://testhost";
        URLConnection urlConnection = mock(URLConnection.class);
        byte[] expectedXmlBytes = "<posts></posts>".getBytes();
        InputStream xmlInputStream = new ByteArrayInputStream(expectedXmlBytes);

        httpUrlStreamHandler.addConnection(new URL(href), urlConnection);

        given(urlConnection.getInputStream()).willReturn(xmlInputStream);
    }

    @Test
    public void testParseXMLFromUrl() throws ParseException {
        assertTrue(saxXmlParser.parseXMLFromUrl("http://testhost", xmlNode, result) != null);
    }
}