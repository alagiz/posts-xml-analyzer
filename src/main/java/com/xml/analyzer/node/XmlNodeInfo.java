package com.xml.analyzer.node;

import com.xml.analyzer.result.Result;
import lombok.Getter;
import lombok.Setter;

public class XmlNodeInfo {
    @Getter
    @Setter
    private Result result;

    @Getter
    @Setter
    private XmlNode xmlNode;
}
