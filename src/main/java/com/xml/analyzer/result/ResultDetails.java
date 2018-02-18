package com.xml.analyzer.result;

import org.xml.sax.Attributes;

public interface ResultDetails {
    void resetDetails();

    void accumulateDetails(String qName, Attributes attrs);
}
