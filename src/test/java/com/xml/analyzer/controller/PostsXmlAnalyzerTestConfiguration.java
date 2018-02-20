package com.xml.analyzer.controller;

import com.xml.analyzer.node.XmlNode;
import com.xml.analyzer.parser.posts.PostsXmlParser;
import com.xml.analyzer.parser.posts.sax.SaxXmlHandler;
import com.xml.analyzer.parser.posts.sax.SaxXmlParser;
import com.xml.analyzer.result.Result;
import com.xml.analyzer.result.posts.PostsResultDetails;
import org.springframework.context.annotation.Bean;

public class PostsXmlAnalyzerTestConfiguration {
    @Bean
    public XmlAnalyzerController xmlAnalyzerController() {
        return new XmlAnalyzerController();
    }

    @Bean
    public PostsXmlParser postsXmlParser() {
        return new PostsXmlParser();
    }

    @Bean
    public SaxXmlParser saxXmlParser() {
        return new SaxXmlParser();
    }

    @Bean
    public SaxXmlHandler saxXmlHandler() {
        return new SaxXmlHandler();
    }

    @Bean
    public XmlNode xmlNode() {
        return new XmlNode();
    }

    @Bean
    public Result result() {
        return new Result();
    }

    @Bean
    public PostsResultDetails postsResultDetails() {
        return new PostsResultDetails();
    }
}
