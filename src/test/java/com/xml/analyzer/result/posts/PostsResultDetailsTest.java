package com.xml.analyzer.result.posts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class PostsResultDetailsTest {
    @InjectMocks
    private PostsResultDetails postsResultDetails;

    @Before
    public void setUp() throws Exception {
        postsResultDetails.resetDetails();
    }

    @After
    public void tearDown() throws Exception {
        postsResultDetails = null;

        assertNull(postsResultDetails);
    }

    @Test
    public void resetDetails() {
        postsResultDetails.setAverageScore(1);
        postsResultDetails.setTotalPosts(1);

        postsResultDetails.resetDetails();

        assertEquals(postsResultDetails.getTotalPosts(), 0);
        assertEquals(postsResultDetails.getAverageScore(), 0, 0);
    }

    @Test
    public void accumulateDetails() {
    }
}