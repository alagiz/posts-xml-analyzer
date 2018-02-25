# posts-xml-analyzer
[![Build Status](https://travis-ci.org/ArtemAlagizov/posts-xml-analyzer.svg?branch=master)](https://travis-ci.org/ArtemAlagizov/posts-xml-analyzer)

posts-xml-analyzer is a small service able to analyze xml files of "posts" type; "posts" type is based on posts from https://archive.org/details/stackexchange.

Github repository of the app: 
````
https://github.com/ArtemAlagizov/posts-xml-analyzer
````
Docker container with the app: 
````
https://hub.docker.com/r/alagiz/posts-xml-analyzer/
````

# Usage with docker
* Run
```
$ docker pull alagiz/posts-xml-analyzer
```
```
$ docker run -p 8080:8080 alagiz/posts-xml-analyzer
```

* Send POST request to http://localhost:8080/analyze or http://host-ip:8080/analyze in your browser
  * Include url to the xml file as a parameter:
     ```
     url: "https://somehost/posts.xml"
     ```

# Usage without docker
* Clone the github repository
```
https://github.com/ArtemAlagizov/posts-xml-analyzer
```
* Run 
```
$ mvn exec:java
```
* Send POST request to http://localhost:8080/analyze or http://host-ip:8080/analyze in your browser
    * Include url to the xml file as a parameter:
       ```
       url: "https://somehost/posts.xml"
       ```
***
 * Supported xml file structure (every row represents a post):
 ```
 <?xml version="1.0" encoding="utf-8"?>
 <posts>
    <row Id="1" AnswerCount="5" CreationDate="2015-07-14T20:06:44.950"/>
    <row Id="2" AnswerCount="4"/>
 </posts>
 ```
 * Supported attributes of a row:
 ```
Id (any type)
AnswerCount (int)
AcceptedAnswerId (int)
CommentCount (int)
ViewCount (int)
Score (int)
CreationDate (string of the following format: yyyy-MM-dd'T'HH:mm:ss.SSS)
 ```
 * Analysis results example
 ```
 {
    "analysisDate": "2018-02-23T14:42:43+01:00",
    "details": {
        "totalPosts": 655,
        "totalAnswers": 360,
        "totalAcceptedAnswers": 102,
        "totalComments": 793,
        "totalViews": 11198,
        "averageScore": 3.273,
        "firstPostDate": "2016-01-12T18:45:19.963+01:00",
        "lastPostDate": "2016-03-04T13:30:22.410+01:00"
    }
}
 ```
