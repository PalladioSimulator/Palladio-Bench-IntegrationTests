# Palladio-Build-IntegrationTests


Preparation:

- Download RCPTT from https://www.eclipse.org/rcptt/download/
- RCPTT Documentation at https://www.eclipse.org/rcptt/documentation/userguide/getstarted/
- Create new GIT-Project e.g. https://github.com/PalladioSimulator/Palladio-Build-IntegrationTests
- Create new RCPTT Project
- Share Project in GIT (right click on Project > Team > Share Project...) and Initial Commit 
(right click on Project > Team > Commit)
- Downloading Palladio Bench from https://sdqweb.ipd.kit.edu/eclipse/palladiobench/releases/4.1.0/ 
- Create new AUT in RCPTT with Palladio Bench 


Automating RCPTT Testing in Maven Build with RCPTT Maven Plugin:

- Generate pom.xml (right click on project > RCPTT > generate pom.xml), for more infos check https://www.eclipse.org/rcptt/documentation/userguide/maven/
- Configurate pom.xml (packaging as "rcpttTest", linking AUT), for more infos check https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
- SCM for SVN-Import: http://maven.apache.org/scm/subversion.html
- SCM for GIT-Import: http://maven.apache.org/scm/git.html
- SCM Plugin - Lists of goal commands : https://maven.apache.org/scm/maven-scm-plugin/index.html
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
- use contexts instead of writing it in the script
- pay attention to context order (Workspace Context, Folder, Workbench, Launch Config...)
- select the options "clear workspace context" and "clear launch configs", if it does not affect the test
- where possible avoid selections, especially the ".*" star selections
- make a refresh or key-type f5 before every action in project explorer
- use the simple copy paste commands for copying files, not those in the menu
- record launches additionally to defining launch configs by pressing "Capture" button during a running launch in AUT
- also check test cases in maven script very often
- in graphical tests, avoid mouse movements (e.g. drag and drop) as much as possible; select the elements through the tree of project explorer and edit them via the properties window


For more Help check the Community: https://www.eclipse.org/forums/index.php/f/281/
