package com.xml.analyzer.node;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class XmlNode {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private boolean insideThisNode;
}
