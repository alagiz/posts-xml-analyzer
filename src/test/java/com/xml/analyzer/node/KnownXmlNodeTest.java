package com.xml.analyzer.node;

import org.junit.Test;

import static org.junit.Assert.*;

public class KnownXmlNodeTest {

    @Test
    public void getNodeInfo() {
        XmlNodeInfo nodeInfo = KnownXmlNode.POSTS.getNodeInfo();

        assertEquals(nodeInfo.getXmlNode().getName(), "posts");
        assertFalse(nodeInfo.getXmlNode().isInsideThisNode());
    }
}