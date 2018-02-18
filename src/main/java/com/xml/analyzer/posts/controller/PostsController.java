package com.xml.analyzer.posts.controller;

import com.xml.analyzer.node.KnownXmlNode;
import com.xml.analyzer.node.XmlNodeInfo;
import com.xml.analyzer.parser.XmlParser;
import com.xml.analyzer.parser.sax.SaxXmlParser;
import com.xml.analyzer.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analyze")
public class PostsController {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Result analyzeWithSax(@RequestParam("url") String url) {
        XmlNodeInfo xmlNodeInfo = KnownXmlNode.POSTS.getNodeInfo();
        XmlParser xmlParser = new SaxXmlParser(xmlNodeInfo);

        xmlParser.parseXMLFromUrl(url);

        return xmlParser.getParsingResults();
    }
}
