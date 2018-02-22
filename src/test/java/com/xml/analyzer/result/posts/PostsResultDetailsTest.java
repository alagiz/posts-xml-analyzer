package com.xml.analyzer.result.posts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PostsResultDetailsTest {
    @InjectMocks
    private PostsResultDetails postsResultDetails;

    @Test
    public void testResetDetails() {
        postsResultDetails.setAverageScore(1);
        postsResultDetails.setTotalPosts(1);

        postsResultDetails.resetDetails();

        assertEquals(postsResultDetails.getTotalPosts(), 0);
        assertEquals(postsResultDetails.getAverageScore(), 0, 0);
    }

    @Test
    public void testAccumulateDetails() {
    }

    @Test
    public void testUpdatePostCount() {
    }

    @Test
    public void testUpdateAnswerCount() {
    }

    @Test
    public void testUpdateAcceptedAnswersCount() {
    }

    @Test
    public void testUpdateCommentCount() {
    }

    @Test
    public void testUpdateViewCount() {
    }

    @Test
    public void testUpdateAvgScore() {
    }

    @Test
    public void testUpdateFirstLastPostDates() {
    }
}