package com.xml.analyzer.parser.posts.sax;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpUrlStreamHandler allows to mock a URLConnection.
 */
public class HttpUrlStreamHandler extends URLStreamHandler {
    private Map<URL, URLConnection> connections = new HashMap<>();

    @Override
    protected URLConnection openConnection(URL url) {
        return connections.get(url);
    }

    void resetConnections() {
        connections = new HashMap<>();
    }

    HttpUrlStreamHandler addConnection(URL url, URLConnection urlConnection) {
        connections.put(url, urlConnection);

        return this;
    }
}
