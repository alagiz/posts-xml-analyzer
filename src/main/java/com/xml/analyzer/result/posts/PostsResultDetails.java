package com.xml.analyzer.result.posts;

import com.xml.analyzer.result.ResultDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;

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
            Arrays.asList("Id", "CreationDate", "Score"));

    @Override
    public void resetDetails() {
        setTotalPosts(0);
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
                                updatePostsCount();

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

    private void updatePostsCount() {
        setTotalPosts(getTotalPosts() + 1);
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
