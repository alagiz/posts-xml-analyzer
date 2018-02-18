package com.xml.analyzer.parser;

import com.xml.analyzer.result.Result;

public interface XmlParser {
    void parseXMLFromUrl(String url);

    void setParsingDate();

    Result getParsingResults();
}
