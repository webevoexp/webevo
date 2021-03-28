We perform experiments on 13 websites. 
### Experiment Steps

#### DOMTree-based Change Detection Module
+ Input.

The target page and the evolved page.
+ Output.

dom_changes.csv

The columns of dom_changes.csv are:

ChangedType - Three values: NODE_REMOVED, NODE_ADDED and PROP_CHANGED.

AddedTag - Applicable for NODE_ADDED. The tag of the added node in the evolved page. E.g., span, li, a, etc.

addedXpath - Applicable for NODE_ADDED. The XPath of the added node in the evolved page.

typeChanged - Applicable for PROP_CHANGED. The attribute of the updated node. E.g., id, class, etc.

oldType - Applicable for PROP_CHANGED. The value of the updated node's attribute in the target page.

oldTag - Applicable for PROP_CHANGED. The tag of the updated node in the target page.

oldXPath - Applicable for PROP_CHANGED. The XPath of the updated node in the target page.

newType - Applicable for PROP_CHANGED. The value of the updated node's attribute in the evolved page.

newTag - Applicable for PROP_CHANGED. The tag of the updated node in the evolved page.

newXPath - Applicable for PROP_CHANGED. The XPath of the updated node in the evolved page.

removedTag - Applicable for NODE_REMOVED. The tag of the removed node in the target page. E.g., span, li, a, etc.

removedXPath - Applicable for NODE_REMOVED. The XPath of the removed node in the target page.

+ Example to execute the program.

```bash
java -jar api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar -oldpage: w3schools/2016.html -newpage: w3schools/2019.html
```

api-monitor-0.0.1-SNAPSHOT-jar-with-dependencies.jar locates in [DOM-tree-based-change-detection](DOM-tree-based-change-detection).

#### History-based Change Detection Output
The dynamic.txt file is the output of the History-based Change Detection Module.
#### Semantics-based Visual Search 
The inputs of the Semantic-based Visual Search Module are: the target web page and its screenshot (old.png), the evolved page and its screenshot (new.png), and ds1_results.csv. The ds1_results.csv contains all elements outputted by DOM-tree Based Change Detection Module except the elements which are in dynamic.txt.
The outputs of the Semantic-based Visual Search Module are in the "target_img" folder and the "candidate_img" folder. 
#### Experiment Results
The experiment results and the ground truth are in Result and Ground Truth.xlsx.

