package com.xml.analyzer.result.posts;

import com.xml.analyzer.result.Result;
import com.xml.analyzer.result.ResultDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

public class PostsResult implements Result {
    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime analysisDate;

    @Getter
    @Setter
    @JsonProperty("details")
    private ResultDetails resultDetails;

    public PostsResult(ResultDetails resultDetails) {
        setResultDetails(resultDetails);
    }
}
