package com.xml.analyzer.result.posts;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.xml.sax.Attributes;

import java.time.ZonedDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostsResultDetailsTest {
    @InjectMocks
    private PostsResultDetails postsResultDetails;

    private Attributes attrs;

    @Before
    public void init() {
        attrs = mock(Attributes.class);
    }

    @Test
    public void testPostInit() {
        assertEquals(postsResultDetails.getTotalPosts(), 0);
        assertEquals(postsResultDetails.getTotalAnswers(), 0);
        assertEquals(postsResultDetails.getTotalAcceptedAnswers(), 0);
        assertEquals(postsResultDetails.getTotalComments(), 0);
        assertEquals(postsResultDetails.getTotalViews(), 0);
        assertEquals(postsResultDetails.getAverageScore(), 0, 0);
        assertNull(postsResultDetails.getFirstPostDate());
        assertNull(postsResultDetails.getLastPostDate());
    }

    @Test
    public void testResetDetails() {
        postsResultDetails.setTotalPosts(1);
        postsResultDetails.setTotalAnswers(1);
        postsResultDetails.setTotalAcceptedAnswers(1);
        postsResultDetails.setTotalComments(1);
        postsResultDetails.setTotalViews(1);
        postsResultDetails.setAverageScore(1);
        postsResultDetails.setFirstPostDate(ZonedDateTime.now());
        postsResultDetails.setLastPostDate(ZonedDateTime.now());

        assertEquals(postsResultDetails.getTotalPosts(), 1);
        assertEquals(postsResultDetails.getTotalAnswers(), 1);
        assertEquals(postsResultDetails.getTotalAcceptedAnswers(), 1);
        assertEquals(postsResultDetails.getTotalComments(), 1);
        assertEquals(postsResultDetails.getTotalViews(), 1);
        assertEquals(postsResultDetails.getAverageScore(), 1, 0);
        assertNotNull(postsResultDetails.getFirstPostDate());
        assertNotNull(postsResultDetails.getLastPostDate());

        postsResultDetails.resetDetails();

        assertEquals(postsResultDetails.getTotalPosts(), 0);
        assertEquals(postsResultDetails.getTotalAnswers(), 0);
        assertEquals(postsResultDetails.getTotalAcceptedAnswers(), 0);
        assertEquals(postsResultDetails.getTotalComments(), 0);
        assertEquals(postsResultDetails.getTotalViews(), 0);
        assertEquals(postsResultDetails.getAverageScore(), 0, 0);
        assertNull(postsResultDetails.getFirstPostDate());
        assertNull(postsResultDetails.getLastPostDate());
    }

    @Test
    public void testUpdatePostCount() {
        when(attrs.getValue("Id")).thenReturn("0");

        assertEquals(postsResultDetails.getTotalPosts(), 0);

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalPosts(), 1);

        when(attrs.getValue("Id")).thenReturn("1");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalPosts(), 2);
    }

    @Test
    public void testUpdateAnswerCount() {
        when(attrs.getValue("AnswerCount")).thenReturn("5");

        assertEquals(postsResultDetails.getTotalAnswers(), 0);

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalAnswers(), 5);

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalAnswers(), 10);
    }

    @Test
    public void testUpdateAcceptedAnswersCount() {
        when(attrs.getValue("AcceptedAnswerId")).thenReturn("0");

        assertEquals(postsResultDetails.getTotalAcceptedAnswers(), 0);

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalAcceptedAnswers(), 1);

        when(attrs.getValue("AcceptedAnswerId")).thenReturn("1");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalAcceptedAnswers(), 2);
    }

    @Test
    public void testUpdateCommentCount() {
        when(attrs.getValue("CommentCount")).thenReturn("11");

        assertEquals(postsResultDetails.getTotalComments(), 0);

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalComments(), 11);

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalComments(), 22);
    }

    @Test
    public void testUpdateViewCount() {
        when(attrs.getValue("ViewCount")).thenReturn("11");

        assertEquals(postsResultDetails.getTotalViews(), 0);

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalViews(), 11);

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalViews(), 22);
    }

    @Test
    public void testUpdateAvgScore() {
        when(attrs.getValue("Id")).thenReturn("0");
        when(attrs.getValue("Score")).thenReturn("5");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalPosts(), 1);
        assertEquals(postsResultDetails.getAverageScore(), 5, 0);

        when(attrs.getValue("Id")).thenReturn("1");
        when(attrs.getValue("Score")).thenReturn("15");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalPosts(), 2);
        assertEquals(postsResultDetails.getAverageScore(), 10, 0);
    }

    @Test
    public void testUpdateFirstLastPostDates() {
        when(attrs.getValue("CreationDate")).thenReturn("2000-01-01T00:00:00.000");

        assertNull(postsResultDetails.getFirstPostDate());
        assertNull(postsResultDetails.getLastPostDate());

        postsResultDetails.accumulateDetails("row", attrs);

        assertNotNull(postsResultDetails.getFirstPostDate());
        assertNotNull(postsResultDetails.getLastPostDate());

        when(attrs.getValue("CreationDate")).thenReturn("2020-01-01T00:00:00.000");

        assertEquals(postsResultDetails.getFirstPostDate().getYear(), 2000);
        assertEquals(postsResultDetails.getLastPostDate().getYear(), 2000);

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getFirstPostDate().getYear(), 2000);
        assertEquals(postsResultDetails.getLastPostDate().getYear(), 2020);

        when(attrs.getValue("CreationDate")).thenReturn("1990-01-01T00:00:00.000");

        assertEquals(postsResultDetails.getFirstPostDate().getYear(), 2000);
        assertEquals(postsResultDetails.getLastPostDate().getYear(), 2020);

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getFirstPostDate().getYear(), 1990);
        assertEquals(postsResultDetails.getLastPostDate().getYear(), 2020);
    }
}