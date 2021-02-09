# WebEvo
Implementation of the paper WEBEVO: Taming Web Application Evoluation via Detecting Semantic Structure Changes.
## Introduction
WebEvo combines two main modules to find semantic structure changes occuring between different versions of a web page. First module is called the Semantic Structure Change Detection module. The module first performs DOM-tree based change detection by comparing the DOM trees of two pages to find content-based changes and structural changes. Then the detected changes are further pruned via our History-based semantic structure change detection technique to output only semantic structure changes. Finally, these detected changes are used as input to our Semantics-based visual search module, which outputs the semantic structure changes with their mappings using content similarity analysis.

![Overview of Workflow of WebEvo](overview.png)
## Requirements
+ JAVA Version: 1.8
## Usage
The major modules of WebEvo are listed below:

### Semantic structure change detection module.

##### DOM-tree based change detection.

We use the old and new versions of a web page as inputs to our DOM-tree based change detection module to detect changes on a web page based on DOM tree structures.

+ Input.

Old and new version of the web page.

+ Ouput.

Changes in the DOM tree structure.

To run the jar file:
```bash
java -jar api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar -oldpage: <oldpage> -newpage: <newpage>
```

api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar is in [DOM-tree-based-change-detection](DOM-tree-based-change-detection).

##### History-based semantic structure change detection.

The goal of History-based semantic structure change detection is to prune the detected changes from the previous step to find only semantic structure changes.

+ Input.

Old version of the web page, and three history pages.

+ Output.

XPaths labeled as dynamic or static.

To run the jar files:
```bash
java -jar api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar -oldpage: oldpage -historypage1: <historypage1> -historypage2: <historypage2> -historypage3: <historypage3>
```

api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar is in [History-based-change-detection](History-based-change-detection).

### Semantics-based Visual Search
This module detects the position and structure of different graphical elements through the use of optical character recognition (OCR), edge detection, and contour processing techniques to generate graphical areas that correspond to pictures or text areas on the web page. Then it is used to extract the visual hierarchical relationship among the web pages layout elements. The source code is in [graphic-image-analysis](graphic-image-analysis).

## Acknowledgement
[Vista](https://github.com/saltlab/vista)


