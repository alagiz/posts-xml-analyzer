package com.xml.analyzer.parser.sax;

import com.xml.analyzer.node.XmlNode;
import com.xml.analyzer.result.Result;
import com.xml.analyzer.result.ResultDetails;
import lombok.Getter;
import lombok.Setter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxXmlHandler extends DefaultHandler {
    @Getter
    @Setter
    private XmlNode xmlNode;

    @Getter
    @Setter
    private Result saxResult;

    SaxXmlHandler(XmlNode xmlNode, Result result) {
        setXmlNode(xmlNode);
        setSaxResult(result);
    }

    @Override
    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes attrs) throws SAXException {
        XmlNode xmlNode = getXmlNode();
        ResultDetails saxResultDetails = getSaxResult().getResultDetails();

        if (xmlNode.getName().equals(qName)) {
            xmlNode.setInsideThisNode(true);
            saxResultDetails.resetDetails();
        } else {
            saxResultDetails.accumulateDetails(qName, attrs);
        }
    }

    @Override
    public void endElement(String namespaceURI,
                           String localName,
                           String qName) throws SAXException {
        XmlNode xmlNode = getXmlNode();

        if (xmlNode.getName().equals(qName) && xmlNode.isInsideThisNode()) {
            xmlNode.setInsideThisNode(false);
        }
    }
}
