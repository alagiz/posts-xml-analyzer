package com.xml.analyzer.parser.posts.sax;

import com.xml.analyzer.node.XmlNode;
import com.xml.analyzer.parser.XmlParser.ParseException;
import com.xml.analyzer.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.net.URL;

/**
 * Sax parser implementation. Parses XML from an input stream from the given url.
 */
@Component
public class SaxXmlParser {
    @Autowired
    private SaxXmlHandler saxXmlHandler;

    public Result parseXMLFromUrl(String url, XmlNode xmlNode, Result result) throws ParseException {
        saxXmlHandler.setResult(result);
        saxXmlHandler.setXmlNode(xmlNode);

        try {
            parseStreamFromUrl(url);
        } catch (Exception parseException) {
            throw new ParseException(parseException.getMessage());
        }

        result.finalizeResult();

        return result;
    }

    private void parseStreamFromUrl(String url) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        URL linkURL = new URL(url);

        try (InputStream inputStream = linkURL.openStream()) {
            sp.parse(new InputSource(inputStream), saxXmlHandler);
        }
    }
}
