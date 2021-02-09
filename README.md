# WebEvo
Implementation of the paper WEBEVO: Taming Web Application Evoluation via Detecting Semantic Structure Changes.
## Introduction
WebEvo combines two main modules to find semantic structure changes occuring between different versions of a web page. First module is called the Semantic Structure Change Detection module. The module first performs DOM-tree based change detection by comparing the DOM trees of two pages to find content-based changes and structural changes. Then the detected changes are further pruned via our History-based semantic structure change detection technique to output only semantic structure changes. Finally, these detected changes are used as input to our Semantics-based visual search module, which outputs the semantic structure changes with their mappings using content similarity analysis.

![Overview of Workflow of WebEvo](overview.png)
## Requirements
+ JAVA Version: 1.8
## Usage
The major modules of WebEvo are listed below:

1. DOM-tree based change detection.

We use the old and new versions of a web page as inputs to our DOM-tree based change detection module to detect changes on a web page based on DOM tree structures.

+ Input.

Old and new version of the web page.

+ Ouput.

Changes in the DOM tree structure.

To run the jar file:
```bash
java -jar api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar -oldpage: <oldpage> -newpage: <newpage>
```

api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar is in [DOM-tree based change detection](DOM-tree based change detection).

to be continued tomorrow...
