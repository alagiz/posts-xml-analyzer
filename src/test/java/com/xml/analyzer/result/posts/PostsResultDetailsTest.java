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
    public void shouldResetDetailsWhenResettingDetails() {
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
    public void shouldRoundAvgScoreWhenFinalizingResultDetails() {
        postsResultDetails.setAverageScore(0.11235);
        postsResultDetails.finalizeResultDetails();

        assertEquals(postsResultDetails.getAverageScore(), 0.112, 0);
    }

    @Test
    public void shouldUpdatePostCountWhenThereIsOnePost() {
        when(attrs.getValue("Id")).thenReturn("0");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalPosts(), 1);
    }

    @Test
    public void shouldUpdatePostCountWhenThereAreTwoPosts() {
        when(attrs.getValue("Id")).thenReturn("0");

        postsResultDetails.accumulateDetails("row", attrs);

        when(attrs.getValue("Id")).thenReturn("1");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalPosts(), 2);
    }

    @Test
    public void shouldUpdateAnswerCountWhenTheseIsOnePost() {
        when(attrs.getValue("AnswerCount")).thenReturn("5");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalAnswers(), 5);
    }

    @Test
    public void shouldUpdateAnswerCountWhenThereAreTwoPosts() {
        when(attrs.getValue("AnswerCount")).thenReturn("5");

        postsResultDetails.accumulateDetails("row", attrs);
        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalAnswers(), 10);
    }

    @Test
    public void shouldUpdateAcceptedAnswersCountWhenThereIsOnePost() {
        when(attrs.getValue("AcceptedAnswerId")).thenReturn("0");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalAcceptedAnswers(), 1);
    }

    @Test
    public void shouldUpdateAcceptedAnswersCountWhenThereAreTwoPosts() {
        when(attrs.getValue("AcceptedAnswerId")).thenReturn("0");

        postsResultDetails.accumulateDetails("row", attrs);
        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalAcceptedAnswers(), 2);
    }

    @Test
    public void shouldUpdateCommentCountWhenThereIsOnePost() {
        when(attrs.getValue("CommentCount")).thenReturn("11");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalComments(), 11);
    }

    @Test
    public void shouldUpdateCommentCountWhenThereAreTwoPosts() {
        when(attrs.getValue("CommentCount")).thenReturn("11");

        postsResultDetails.accumulateDetails("row", attrs);
        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalComments(), 22);
    }

    @Test
    public void shouldUpdateViewCountWhenThereIsOnePost() {
        when(attrs.getValue("ViewCount")).thenReturn("11");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalViews(), 11);
    }

    @Test
    public void shouldUpdateViewCountWhenThereAreTwoPosts() {
        when(attrs.getValue("ViewCount")).thenReturn("11");

        postsResultDetails.accumulateDetails("row", attrs);
        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalViews(), 22);
    }

    @Test
    public void shouldUpdateAvgScoreWhenThereIsOnePost() {
        when(attrs.getValue("Id")).thenReturn("0");
        when(attrs.getValue("Score")).thenReturn("5");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalPosts(), 1);
        assertEquals(postsResultDetails.getAverageScore(), 5, 0);
    }

    @Test
    public void shouldUpdateAvgScoreWhenThereAreTwoPosts() {
        when(attrs.getValue("Id")).thenReturn("0");
        when(attrs.getValue("Score")).thenReturn("5");

        postsResultDetails.accumulateDetails("row", attrs);

        when(attrs.getValue("Id")).thenReturn("1");
        when(attrs.getValue("Score")).thenReturn("15");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getTotalPosts(), 2);
        assertEquals(postsResultDetails.getAverageScore(), 10, 0);
    }

    @Test
    public void shouldUpdateFirstLastPostDatesWhenThereIsOnePost() {
        when(attrs.getValue("CreationDate")).thenReturn("2000-01-01T00:00:00.000");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getFirstPostDate().getYear(), 2000);
        assertEquals(postsResultDetails.getLastPostDate().getYear(), 2000);
    }

    @Test
    public void shouldUpdateFirstLastPostDatesWhenThereAreTwoPosts() {
        when(attrs.getValue("CreationDate")).thenReturn("2000-01-01T00:00:00.000");

        postsResultDetails.accumulateDetails("row", attrs);

        when(attrs.getValue("CreationDate")).thenReturn("2020-01-01T00:00:00.000");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getFirstPostDate().getYear(), 2000);
        assertEquals(postsResultDetails.getLastPostDate().getYear(), 2020);
    }

    @Test
    public void shouldUpdateFirstLastPostDatesWhenThereAreThreePosts() {
        when(attrs.getValue("CreationDate")).thenReturn("2000-01-01T00:00:00.000");

        postsResultDetails.accumulateDetails("row", attrs);

        when(attrs.getValue("CreationDate")).thenReturn("2020-01-01T00:00:00.000");

        postsResultDetails.accumulateDetails("row", attrs);

        when(attrs.getValue("CreationDate")).thenReturn("1990-01-01T00:00:00.000");

        postsResultDetails.accumulateDetails("row", attrs);

        assertEquals(postsResultDetails.getFirstPostDate().getYear(), 1990);
        assertEquals(postsResultDetails.getLastPostDate().getYear(), 2020);
    }
}