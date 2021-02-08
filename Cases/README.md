# WebEvo
Implementation of the paper WEBEVO: Taming Web Application Evoluation via Detecting Semantic Structure Changes.
## Introduction
In this work, we combined DOM tree based comparison of old and new version of a web page, history based semantic structure comparison, and visual information which identifies relevant structural changes together to monitor changes in a website.

![Overview of Workflow of WebEvo](overview.png)
## Requirements
+ JAVA Version: 1.8
## Usage
The major modules of WebEvo are listed below:

1. DOM tree based comparison.

We use the old and new versions of a web page as inputs to our DOM tree based change detection module to detect changes on a web page based on DOM tree structures.

+ Input.

Old and new version of the web page.

+ Ouput.

Changes in the DOM tree structure.

To run the jar file:
```bash
java -jar api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar -oldpage: <oldpage> -newpage: <newpage>
```

api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar is in [DOM Tree Based Comparison](DOMTreeBasedComparison).

to be continued...
