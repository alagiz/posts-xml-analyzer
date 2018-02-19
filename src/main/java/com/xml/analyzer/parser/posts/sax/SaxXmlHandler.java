package com.xml.analyzer.parser.posts.sax;

import com.xml.analyzer.node.XmlNode;
import com.xml.analyzer.result.Result;
import com.xml.analyzer.result.ResultDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Component
public class SaxXmlHandler extends DefaultHandler {
    @Getter
    @Setter
    private XmlNode xmlNode;

    @Getter
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
        } else {
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
