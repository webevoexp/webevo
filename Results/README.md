We perform experiments on 13 websites. 
### Experiment Steps

#### 1. DOMTree-based Change Detection Module

This module detects whether a part of the web page has changed using Levenshtein Edit Distance to compare the attributes and the structure of the corresponding DOM-trees. The inputs of DOM-tree based change detection module are the target page and the evolved page, the output contains changes in the DOM tree structures.

+ Input:

The target page and the evolved page.
+ Output:

dom_changes.csv

+ Example to execute the program:

```bash
java -jar api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar -oldpage: w3schools/2016.html -newpage: w3schools/2019.html
```
api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar is in [DOM-tree-based-change-detection](/DOM-tree-based-change-detection).

#### 2. History-based Change Detection Module.

The goal of this module is to prune the content-based changes from the previous step to find only semantic structure changes. We define content-based chagnes as web contents being constantly updated based on what a web server delivers to the client browser. This types of changes usually do not cause RPA or test scripts failures therefore need to be identified and filtered. 

We compare the target page with its historical pages to identify the content-based changes.

+ Input:

The target page and three historical pages.

+ Output:

dynamic.txt

+ Example to execute the program:

```bash
java -jar pi-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar -oldpage: w3schools/2016.html
-historypage1: 2016-01-13_history1/index.html -historypage2: 2016-01-12_history2/index.html -historypage3: 2016-01-11_history3/index.html
```
api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar is in [History-based-change-detection](/History-based-change-detection).

+ Example:

Below is an example of the content-based change.

Apple website -  In the output (dynamic.txt) of History-based semantic structure change detection module, the promotion section in the target page is identified as a content-based change (dynamic  /body/main[1]/section[2]) because it is constantly updated in a very short period of time, therefore it will not be passed to the Semantics-based Visual Search module to have further analysis.

<table>
  <tr>
    <td>The promotion section in the target page:<br><img src="apple2018.png" width="358"></td>
    <td>The promotion section in the historical page:<br><img src="apple2018history.png" width="358"></td>
  </tr>
</table>

#### 3. Manually check every single XPath reported in dom_changes.csv on both target page and evolved page.
Copy "dom_changes.csv" to "Result and Ground Truth.csv". Add a few more columns to store the results of each module. The columns are listed as below:

+ ChangedType - Three change types: NODE_REMOVED, NODE_ADDED and PROP_CHANGED.

+ AddedTag - Applicable for NODE_ADDED. The tag of the added node in the evolved page. E.g., span, li, a, etc.

+ addedXpath - Applicable for NODE_ADDED. The XPath of the added node in the evolved page.

+ typeChanged - Applicable for PROP_CHANGED. The attribute of the updated node. E.g., id, class, etc.

+ oldType - Applicable for PROP_CHANGED. The value of the updated node's attribute in the target page.

+ oldTag - Applicable for PROP_CHANGED. The tag of the updated node in the target page.

+ oldXPath - Applicable for PROP_CHANGED. The XPath of the updated node in the target page.

+ newType - Applicable for PROP_CHANGED. The value of the updated node's attribute in the evolved page.

+ newTag - Applicable for PROP_CHANGED. The tag of the updated node in the evolved page.

+ newXPath - Applicable for PROP_CHANGED. The XPath of the updated node in the evolved page.

+ removedTag - Applicable for NODE_REMOVED. The tag of the removed node in the target page. E.g., span, li, a, etc.

+ removedXPath - Applicable for NODE_REMOVED. The XPath of the removed node in the target page.

+ Dynamic? - Based on "dynamic.txt", mark Y or N to indicate whether a change reported by the DOMTree-based detection module is a content-based change or not.

+ DOM - Manually check whether a change reported by the DOMTree-based detection module is correct. Mark it as true positive or false positive.

+ CV - After running Step 4, manually check the outputs of Semantics-based Visual Search Module. Mark it TP, FP or FN.

+ Result - The final result after combining the results of DOM and CV.

+ Vista - The result of Vista.

+ GroundTruth - The notes indicate the ground truth of the webpage element the XPath refers to. A(Added)/U(Updated)/D(Deleted) in the notes indicates the ground truth ChangedType of the element. The notes without A/U/D means the element was already counted. Do not double count it.

During the process of the results verification, we are able to know what changed elements were missed by WebEvo and mark them as false negatives.

#### 4. Semantics-based Visual Search 
This module focuses on detecting the elements which have their locations changed in the web pages. Rather that analyzing the screenshots of whole web pages, WebEvo obtains the screenshots of the candidate changes and combines both text and image similarities to identify mappings between the original elements in old web page and the changed elements in new web page.

The inputs of the Semantic-based Visual Search Module are: the target web page and its screenshot (old.png), the evolved page and its screenshot (new.png) and "ds1_results.csv". The "ds1_results.csv" is the subset of "dom_changes.csv", which excludes the XPaths reported as "dynamic" in "dynamic.txt".

The outputs of the Semantic-based Visual Search Module are the screenshots of the webpage elements located in the "target_img" folder and the "candidate_img" folder. 

The source code is in [graphic-image-analysis](/graphic-image-analysis).

+ Example:

The location of the link "EXAMPLES" on w3schools is different between the target page and the evolved page.

The location on the target page:

<img src="w3schools2016.png">

The location on the evolved page:

<img src="w3schools2019.png">

By DOMTree-based change detection module, the "EXAMPLES" link is incorrectly marked as "NODE_REMOVED". Semantics-based Visual Search can fix this error, it correctly identifies this link on the evolved page. Refer to [target screenshot](w3schools/target_img/target130.png) and [evolved screenshot](w3schools/candidate_img/target130_folder/candidate1.png).


