We perform experiments on 13 websites. 
#### Web pages
Each website folder contains one target web page and its screenshot (old.png), one evolved page and its screenshot (new.png), and three calendar folders. Each calendar folder contains one history web page. 
#### History-based Change Detection Output
The dynamic.txt file is the output of the History-based Change Detection Module.
#### Semantics-based Visual Search 
The inputs of the Semantic-based Visual Search Module are: the target web page and its screenshot (old.png), the evolved page and its screenshot (new.png), and ds1_results.csv. The ds1_results.csv contains all elements outputted by DOM-tree Based Change Detection Module except the elements which are in dynamic.txt.
The outputs of the Semantic-based Visual Search Module are in the "target_img" folder and the "candidate_img" folder. 
#### Experiment Results
The experiment results and the ground truth are in Result and Ground Truth.xlsx.

