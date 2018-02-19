package com.xml.analyzer.controller;

import com.xml.analyzer.parser.posts.PostsXmlParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@RunWith(MockitoJUnitRunner.class)
public class XmlAnalyzerControllerTest {
//    @InjectMocks
//    private XmlAnalyzerController xmlAnalyzerController;
//
//    @Mock
//    private PostsXmlParser postsXmlParser;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        // Change behaviour of `resource`
//        doReturn("sd").when(postsXmlParser).getPostsResultDetails();
//    }
//
//    @Test
//    public void analyzePosts() {
//        System.out.println(postsXmlParser.getPostsResultDetails());
//    }
}