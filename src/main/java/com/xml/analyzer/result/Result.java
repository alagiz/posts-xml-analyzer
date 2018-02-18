package com.xml.analyzer.result;

import java.time.ZonedDateTime;

public interface Result {
    static ZonedDateTime getAnalysisDate() {
        return ZonedDateTime.now();
    }

    void setAnalysisDate(ZonedDateTime now);

    void setResultDetails(ResultDetails resultDetails);

    ResultDetails getResultDetails();
}
