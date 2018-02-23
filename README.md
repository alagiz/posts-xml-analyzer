# posts-xml-analyzer
[![Build Status](https://travis-ci.org/ArtemAlagizov/posts-xml-analyzer.svg?branch=master)](https://travis-ci.org/ArtemAlagizov/posts-xml-analyzer)

posts-xml-analyzer is a small service able to analyze xml files of "posts" type; "posts" type is based on posts from https://archive.org/details/stackexchange.

# Usage example
* Clone the project
* Run 
```
$ mvn clean install
```
* Execute produced *.jar file
* Send POST request to http://localhost:8080/analyze
  * Include url to the xml file as a parameter:
   ```
   url: "https://somehost/posts.xml"
   ```
 * Supported xml file structure (every row represents a post):
 ```
 <posts>
    <row Id="1" AnswerCount="5" CreationDate="2015-07-14T20:06:44.950"/>
    <row Id="2" AnswerCount="4"/>
 </posts>
 ```
 * Supported attributes of row:
 ```
Id (any type)
AnswerCount (int)
AcceptedAnswerId (int)
CommentCount (int)
ViewCount (int)
Score (int)
CreationDate (yyyy-MM-dd'T'HH:mm:ss.SSS)
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
