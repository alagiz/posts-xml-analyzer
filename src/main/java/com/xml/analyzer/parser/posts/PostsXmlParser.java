package com.xml.analyzer.parser.posts;

import com.xml.analyzer.parser.XmlParser;
import com.xml.analyzer.parser.posts.sax.SaxXmlParser;
import com.xml.analyzer.result.Result;
import com.xml.analyzer.result.posts.PostsResultDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PostsXmlParser extends XmlParser {
    @Getter
    @Setter
    @Autowired
    private PostsResultDetails postsResultDetails;

    @Getter
    @Setter
    @Autowired
    private SaxXmlParser saxXmlParser;

    @PostConstruct
    public void init() {
        getResult().setResultDetails(getPostsResultDetails());
        getXmlNode().setName("posts");
    }

    @Override
    public Result parseXMLFromUrl(String url) {
        saxXmlParser.parseXMLFromUrl(url, getXmlNode(), getResult());

        getResult().setAnalysisDate(Result.getAnalysisDate());

        return getResult();
    }
}
