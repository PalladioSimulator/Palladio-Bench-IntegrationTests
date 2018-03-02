# Palladio-Build-IntegrationTests

Preparation:

- Download RCPTT from https://www.eclipse.org/rcptt/download/
- RCPTT Documentation at https://www.eclipse.org/rcptt/documentation/userguide/getstarted/
- Create new GIT-Project e.g. https://github.com/PalladioSimulator/Palladio-Build-IntegrationTests
- Create new RCPTT Project
- Share Project in GIT (Rechtsklick auf Project > Team > Share Project...) and Initial Commit (Rechtsklick auf Projekt > Team > Commit)
- Downloading Palladio Bench from https://sdqweb.ipd.kit.edu/eclipse/palladiobench/releases/4.1.0/ 
- Create new AUT in RCPTT with Palladio Bench 

Automating RCPTT Testing in Maven Builds with the RCPTT Maven Plugins:

- Generate pom.xml (right click on project > RCPTT > generate pom.xml), for more infos check https://www.eclipse.org/rcptt/documentation/userguide/maven/
- Configurate pom.xml (packaging as "rcpttTest", linking AUT), for more infos check https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
- SCM for SVN-Import: http://maven.apache.org/scm/subversion.html
- SCM for GIT-Import: http://maven.apache.org/scm/git.html
- SCM Plugin - Lists of goal commands : https://maven.apache.org/scm/maven-scm-plugin/index.html
- Example for pom.xml under https://raw.githubusercontent.com/PalladioSimulator/Palladio-Build-IntegrationTests/master/org.palladiosimulator.product.tests.ui/pom.xml

Procedure for a new Testcase:

- Create a new testcase in RCPTT Project with the name on Jira, copying steps from Jira in description of the test in RCPTT
- Creating Contexts and test them with "Apply" 
- Setting Workspace and check "Clear Workspace"
- Collect Workbench in running AUT and take over in RCPTT with "Capture"
- Take over launch configs after project import through "Capture", to run multiple run configs check "Multiple Launches" in run configurations
- Folder to import reference files or other files in project directory
- Write a script or record it through the "Record" function (where possible, record and copy smaller sections with the snippet function),if possible leave AUT open, restart if necessary, or delete completely and add again
- List of predefined commands at https://hudson.eclipse.org/rcptt/job/rcptt-all/ws/releng/doc/target/doc/ecl/index.html
- To analyse your script and run it step by step use the DEBUG perspective


Running maven script:
- Install "m2e - Maven integration for Eclipse" under Help > install new software
- Right click on pom.xml > Run as > Run Configurations > Create new Maven Build Configuration with goal "verify" (+Screenshots) > Run
- Check console for the results

For more Help check the Community: https://www.eclipse.org/forums/index.php/f/281/
