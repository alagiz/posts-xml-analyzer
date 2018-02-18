package com.xml.analyzer.parser.sax;

import com.xml.analyzer.node.XmlNodeInfo;
import com.xml.analyzer.node.XmlNode;
import com.xml.analyzer.parser.XmlParser;
import com.xml.analyzer.result.Result;
import lombok.Getter;
import lombok.Setter;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.net.URL;

public class SaxXmlParser implements XmlParser {
    @Getter
    @Setter
    private SaxXmlHandler saxXmlHandler;

    @Getter
    @Setter
    private XmlNode xmlNode;

    @Getter
    @Setter
    private Result saxResult;

    public SaxXmlParser(XmlNodeInfo xmlNodeInfo){
        setXmlNode(xmlNodeInfo.getXmlNode());
        setSaxResult(xmlNodeInfo.getResult());
    }

    @Override
    public void parseXMLFromUrl(String url) {
        setSaxXmlHandler(new SaxXmlHandler(getXmlNode(), getSaxResult()));

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            URL linkURL = new URL(url);

            sp.parse(new InputSource(linkURL.openStream()), saxXmlHandler);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void setParsingDate() {
        getSaxResult().setAnalysisDate(Result.getAnalysisDate());
    }

    @Override
    public Result getParsingResults() {
        setParsingDate();

        return getSaxResult();
    }
}
