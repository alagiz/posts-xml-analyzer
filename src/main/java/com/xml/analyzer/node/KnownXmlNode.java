package com.xml.analyzer.node;

import com.xml.analyzer.result.posts.PostsResult;
import com.xml.analyzer.result.posts.PostsResultDetails;
import com.xml.analyzer.result.Result;
import com.xml.analyzer.result.ResultDetails;

public enum KnownXmlNode {
    POSTS;

    public XmlNodeInfo getNodeInfo() {
        XmlNodeInfo xmlNodeInfo = new XmlNodeInfo();

        switch (this) {
            case POSTS:
                ResultDetails resultDetails = new PostsResultDetails();
                Result result = new PostsResult(resultDetails);
                XmlNode xmlNode = new XmlNode("posts");

                xmlNodeInfo.setResult(result);
                xmlNodeInfo.setXmlNode(xmlNode);

                break;
        }

        return xmlNodeInfo;
    }

}
