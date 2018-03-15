# Palladio-Build-IntegrationTests


Preparation:

- Download RCPTT from https://www.eclipse.org/rcptt/download/
- RCPTT Documentation at https://www.eclipse.org/rcptt/documentation/userguide/getstarted/
- Download Palladio Bench from https://sdqweb.ipd.kit.edu/eclipse/palladiobench/releases/4.1.0/ 

- To Add an Application-under-Test(AuT), go to "Applications" View in RCPTT Suite and click "New..." Button on View Toolbar. In the opening Dialog, browse for an AuT location on a disk. AuT name is automatically set to AuT's product-id. Click Finish and double click on the AuT to launch it. 
For more Details check: https://www.eclipse.org/rcptt/documentation/userguide/getstarted/#add-an-application-under-test

- To Configure your AuT click on a created AuT in the list, and click on "Configure..." on View Toolbar. Here you can change the automatically selected name or change the launch config for the AuT by click on "Advancded".

Recommended VM Arguments:
-Dosgi.requiredJavaVersion=1.7 -Xms512m -Xmx2048m -XX:MaxPermSize=128m
-DexamplesDir=USER.HOME\git\Palladio-Build-IntegrationTests\org.palladiosimulator.product.tests.ui\target/examples (folder containing example projects to test)

Structure of a Testcase:

Create a new Testcase:
Reihenfolge der Schaltflächen  
Kontexten sollten alle üblicherweise notwendigen Kontexte und deren Reihenfolge, inkl EDP2 Kontext

Extend a Testcase:

Maintain a Testcase:


Automating RCPTT Testing in Maven Build with RCPTT Maven Plugin:

- Generate pom.xml (right click on project > RCPTT > generate pom.xml), for more infos check https://www.eclipse.org/rcptt/documentation/userguide/maven/
- Configurate pom.xml (packaging as "rcpttTest", linking AUT), for more infos check https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
- SCM for SVN-Import: http://maven.apache.org/scm/subversion.html
- SCM for GIT-Import: http://maven.apache.org/scm/git.html
- SCM Plugin - Lists of goal commands : https://maven.apache.org/scm/maven-scm-plugin/index.html
- In this example the goal "export" was chosen
- Example for pom.xml under https://raw.githubusercontent.com/PalladioSimulator/Palladio-Build-IntegrationTests/master/org.palladiosimulator.product.tests.ui/pom.xml


Procedure for a new Testcase:

- Create a new testcase in RCPTT Project with its name on Jira, copying steps from Jira in description of the test in RCPTT
- Creating Contexts and test them with "Apply" 
  - Setting Workspace and check "Clear Workspace"
  - Collect Workbench in running AUT and take over in RCPTT with "Capture"
  - Take over launch configs after project import through "Capture", to run multiple run configs check "Multiple Launches" in run  configurations
  - Folder to import reference files or other files in project directory
- Write a script or record it through the "Record" function (where possible, record and copy smaller sections with the snippet function), if possible leave AUT open, restart if necessary, or delete completely and add again
- List of predefined commands at https://hudson.eclipse.org/rcptt/job/rcptt-all/ws/releng/doc/target/doc/ecl/index.html
- To analyse your script and run it step by step use DEBUG perspective


Running Maven Script:
- Install "m2e - Maven integration for Eclipse" under Help > install new software
- Right click on pom.xml > Run as > Run Configurations > Create new Maven Build Configuration with goal "verify" (check Screenshots for details about errors) > Run
- Check console for the results


Tips to make the Tests more robust: 
- Use contexts feature instead of writing a script doing this
- Pay attention to context order (Workspace Context, Folder, Workbench, Launch Config...)
- Select the options "clear workspace context" and "clear launch configs", if it does not affect the test
- Where possible avoid selections, especially the ".*" star selections
- Make a refresh or key-type f5 before every action in project explorer
- Use the simple copy paste commands for copying files, not those in the menu
- Record launches additionally to defining launch configs by pressing "Capture" button during a running launch in AUT
- Also check test cases in maven script very often
- In graphical tests, avoid mouse movements (e.g. drag and drop) as much as possible; select the elements through the tree of project explorer and edit them via the properties window


For more Help check the Community: https://www.eclipse.org/forums/index.php/f/281/
