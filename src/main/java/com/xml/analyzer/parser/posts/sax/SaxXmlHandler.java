package com.xml.analyzer.parser.posts.sax;

import com.xml.analyzer.node.XmlNode;
import com.xml.analyzer.result.Result;
import com.xml.analyzer.result.ResultDetails;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SaxHandler implementation. Listens to start and end of XML elements.
 */
@Component
public class SaxXmlHandler extends DefaultHandler {
    @Setter
    private XmlNode xmlNode;

    @Setter
    private Result result;

    @Override
    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes attrs) throws SAXException {
        ResultDetails resultDetails = result.getResultDetails();

        if (xmlNode.getName().equals(qName)) {
            xmlNode.setInsideThisNode(true);
            resultDetails.resetDetails();
        } else if (xmlNode.isInsideThisNode()){
            resultDetails.accumulateDetails(qName, attrs);
        }
    }

    @Override
    public void endElement(String namespaceURI,
                           String localName,
                           String qName) throws SAXException {
        if (xmlNode.getName().equals(qName) && xmlNode.isInsideThisNode()) {
            xmlNode.setInsideThisNode(false);
        }
    }
}
