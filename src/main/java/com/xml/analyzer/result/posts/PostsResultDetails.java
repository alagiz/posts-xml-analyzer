package com.xml.analyzer.result.posts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xml.analyzer.result.ResultDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Specific "posts" implementation of ResultDetails.
 * Contains methods with logic for accumulating data for the xml parsing Result.
 */
@Component
public class PostsResultDetails implements ResultDetails {
    @Getter
    @Setter
    private int totalPosts;

    @Getter
    @Setter
    private int totalAnswers;

    @Getter
    @Setter
    private int totalAcceptedAnswers;

    @Getter
    @Setter
    private int totalComments;

    @Getter
    @Setter
    private int totalViews;

    @Getter
    @Setter
    private double averageScore;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime firstPostDate;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime lastPostDate;

    private final List<String> attributeNameList = new ArrayList<>(
            Arrays.asList(
                    "Id",
                    "AnswerCount",
                    "AcceptedAnswerId",
                    "CommentCount",
                    "ViewCount",
                    "Score",
                    "CreationDate"
            )
    );

    @PostConstruct
    public void init() {
        resetDetails();
    }

    @Override
    public void resetDetails() {
        setTotalPosts(0);
        setTotalAnswers(0);
        setTotalAcceptedAnswers(0);
        setTotalComments(0);
        setTotalViews(0);
        setAverageScore(0);
        setFirstPostDate(null);
        setLastPostDate(null);
    }

    @Override
    public void accumulateDetails(String qName, Attributes attrs) {
        if (qName.equals("row")) {
            attributeNameList
                    .forEach(attributeName -> {
                        switch (attributeName) {
                            case "Id":
                                updatePostCount();

                                break;
                            case "AnswerCount":
                                updateAnswerCount(attrs, attributeName);

                                break;
                            case "AcceptedAnswerId":
                                updateAcceptedAnswersCount(attrs, attributeName);

                                break;
                            case "CommentCount":
                                updateCommentCount(attrs, attributeName);

                                break;
                            case "ViewCount":
                                updateViewCount(attrs, attributeName);

                                break;
                            case "Score":
                                updateAvgScore(attrs, attributeName);

                                break;
                            case "CreationDate":
                                updateFirstLastPostDates(attrs, attributeName);

                                break;
                        }
                    });
        }
    }

    private void updatePostCount() {
        setTotalPosts(getTotalPosts() + 1);
    }

    private void updateAnswerCount(Attributes attrs, String attributeName) {
        if (attrs.getValue(attributeName) != null) {
            setTotalAnswers(getTotalAnswers() + Integer.parseInt(attrs.getValue(attributeName)));
        }
    }

    private void updateAcceptedAnswersCount(Attributes attrs, String attributeName) {
        if (attrs.getValue(attributeName) != null) {
            setTotalAcceptedAnswers(getTotalAcceptedAnswers() + 1);
        }
    }

    private void updateCommentCount(Attributes attrs, String attributeName) {
        if (attrs.getValue(attributeName) != null) {
            setTotalComments(getTotalComments() + Integer.parseInt(attrs.getValue(attributeName)));
        }
    }

    private void updateViewCount(Attributes attrs, String attributeName) {
        if (attrs.getValue(attributeName) != null) {
            setTotalViews(getTotalViews() + Integer.parseInt(attrs.getValue(attributeName)));
        }
    }

    private void updateAvgScore(Attributes attrs, String attributeName) {
        int postCount = getTotalPosts();
        int score = Integer.parseInt(attrs.getValue(attributeName));
        double previousAvg = getAverageScore();
        double updatedAvg = previousAvg * (postCount - 1) / postCount + score / postCount;

        setAverageScore(updatedAvg);
    }

    private void updateFirstLastPostDates(Attributes attrs, String attributeName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        ZonedDateTime firstPostDate = getFirstPostDate();
        ZonedDateTime lastPostDate = getLastPostDate();
        ZonedDateTime thisPostDate = ZonedDateTime.parse(attrs.getValue(attributeName), formatter.withZone(ZoneId.systemDefault()));

        if (firstPostDate == null || thisPostDate.isBefore(firstPostDate)) {
            setFirstPostDate(thisPostDate);
        }

        if (lastPostDate == null || thisPostDate.isAfter(lastPostDate)) {
            setLastPostDate(thisPostDate);
        }
    }
}
