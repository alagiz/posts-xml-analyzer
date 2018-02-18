package com.xml.analyzer.node;

import lombok.Getter;
import lombok.Setter;

public class XmlNode {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private boolean insideThisNode;

    XmlNode(String name) {
        setName(name);
        setInsideThisNode(false);
    }
}
