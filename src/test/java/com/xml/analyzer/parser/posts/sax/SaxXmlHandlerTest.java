package com.xml.analyzer.parser.posts.sax;

import com.xml.analyzer.node.XmlNode;
import com.xml.analyzer.result.Result;
import com.xml.analyzer.result.ResultDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.xml.sax.Attributes;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SaxXmlHandlerTest {
    @InjectMocks
    private SaxXmlHandler saxXmlHandler;

    @Mock
    private Result result;

    @Mock
    private ResultDetails resultDetails;

    @Mock
    private Attributes attrs;

    @Mock
    private XmlNode xmlNode;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        when(xmlNode.getName()).thenAnswer(invocationOnMock -> "xmlNodeName");
        when(xmlNode.isInsideThisNode()).thenAnswer(invocationOnMock -> true);
        when(result.getResultDetails()).thenAnswer(invocationOnMock -> resultDetails);
    }

    @Test
    public void testStartElementNotInsideXmlNode() throws Exception {
        saxXmlHandler.startElement("namespaceURI", "localName", "xmlNodeName", attrs);

        verify(xmlNode).getName();
        verify(resultDetails).resetDetails();
        verify(resultDetails, never()).accumulateDetails(anyString(), eq(attrs));
    }

    @Test
    public void testStartElementIsInsideXmlNode() throws Exception {
        saxXmlHandler.startElement("namespaceURI", "localName", "xmlNodeNameInside", attrs);

        verify(xmlNode).getName();
        verify(resultDetails).accumulateDetails(anyString(), eq(attrs));
        verify(xmlNode).isInsideThisNode();
        verify(resultDetails, never()).resetDetails();
    }

    @Test
    public void testEndElement() throws Exception {
        saxXmlHandler.endElement("namespaceURI", "localName", "xmlNodeName");

        verify(xmlNode).getName();
        verify(xmlNode).isInsideThisNode();
        verify(xmlNode).setInsideThisNode(anyBoolean());
    }
}