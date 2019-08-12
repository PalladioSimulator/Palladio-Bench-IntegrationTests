package org.palladiosimulator.products.tests.ui.programtests;

import java.nio.file.Paths;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotAssert;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTestCase;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTree;

/**
 * Beschreibung aus dazugehörigem RCPTT-Testcase: PCM Instanz starten, neues
 * Palladio Projekt "Minimum Example Template" laden, bei Run Konfigurationen
 * unter SimuBench eine neue erstellen mit folgenden Eigenschaften:
 * default.allocation als Allocation file, default.usagemodel als Usage file,
 * unter Simulation Tab neue Data Source hinzufügen mit "In-Memory data source",
 * mit SimuBench Konfiguration starten, Konsolen Ausgabe auf folgenden Text
 * prüfen: "Workflow engine completed task" Experiments View öffnen, "Usage
 * Scenario: EspressoOrderingUsageScenario (Response Time Tuple)" als Histogramm
 * anzeigen lassen, keine Ergebnisse definiert??? testet Funktionalität und
 * Gültigkeit des Beispiels; Hier sollen nicht die Ergebnisse der Simulation
 * geprüft, sondern lediglich die Funktionalität der Tool-Chain getestet werden.
 * Man müsste hier nur prüfen, ob ein Ergebnis herauskommt. unterschiedliche
 * Ausgaben, nur Prüfung ob Datei erstellt wurde!!!
 */
public class PALLADIO193MinimumProjectTest extends PalladioSWTestCase {

	/**
	 * The directory where the example projects are located in.
	 */
	private final String EXAMPLES_DIR;
	/**
	 * The name of the project that should be imported for testing purposes.
	 */
	private final String PROJECT_NAME;

	/**
	 * Constructor.
	 * 
	 * Initializes this testcase using the super class constructor and initializes
	 * {@link this#EXAMPLES_DIR} and {@link this#PROJECT_NAME}.
	 */
	public PALLADIO193MinimumProjectTest() {
		super();
		this.EXAMPLES_DIR = System.getProperty("examplesDir").replace('"', '\u0000');
		this.PROJECT_NAME = "Minimum_Project_Example";
	}

	/**
	 * The actual TESTCASE.
	 */
	public void testPalladio193() throws Exception {

		importTestProject();

		createEDP2Storage();

		minimumProjectTestLaunch();

		exportResults();

		cleanUpResults(); // FIXME is this really meant as part of the test?

	}

	/**
	 * Import the project specified by {@link this#PROJECT_NAME} from
	 * {@link this#EXAMPLES_DIR}.
	 */
	private void importTestProject() {

		bot.menu("File").menu("Import...").click();
		SWTBotShell importDialog = bot.activeShell();
		bot.tree().getTreeItem("General").expand();
		bot.tree().getTreeItem("General").getNode("Existing Projects into Workspace").select();
		bot.button("Next >").click();
		bot.comboBox().setText(Paths.get(this.EXAMPLES_DIR, this.PROJECT_NAME).toAbsolutePath().toString());
		bot.button("Refresh").click();
		bot.checkBox("Copy projects into workspace").select();
		bot.button("Finish").click();

		bot.waitUntil(Conditions.shellCloses(importDialog));

		// verify import was successfully executed
		bot.viewByTitle("Model Explorer").show();
		SWTBotAssert.assertVisible(bot.tree().getTreeItem("Minimum_Project"));

	}

	/**
	 * In the EDP2 perspective there is an experiment view where all edp2 databases
	 * are listed. This code section creates a new database and set its name to
	 * "EDP2_DB".
	 */
	private void createEDP2Storage() {

		bot.viewByTitle("Experiments").show();
		bot.toolbarButtonWithTooltip("Add Data Source").click();
		SWTBotShell openDataSourceDialog = bot.activeShell();
		bot.comboBox().setSelection("In-Memory data source");
		bot.button("Finish").click();

		bot.waitUntil(Conditions.shellCloses(openDataSourceDialog));

		bot.viewByTitle("Experiments").show();
		((PalladioSWTree) bot.tree()).getTreeItemByExpression("Local Memory.*").select();
		bot.viewByTitle("Properties").show();

		SWTBot propertiesBot = bot.viewByTitle("Properties").bot();
		propertiesBot.tree().getTreeItem("Id").select();
		propertiesBot.tree().getTreeItem("Id").click();
		propertiesBot.text().setText("EDP2_DB");
		propertiesBot.activeShell().pressShortcut(Keystrokes.CR);

	}

	/**
	 * Im Menü Run Konfigurationen unter SimuBench eine neue erstellen mit folgenden
	 * Eigenschaften: default.allocation als Allocation file, default.usagemodel als
	 * Usage file, unter Simulation Tab neue Data Source hinzufügen mit "In-Memory
	 * data source", mit SimuBench Konfiguration starten.
	 */
	private void minimumProjectTestLaunch() throws Exception {

		bot.viewByTitle("Model Explorer").show();
		bot.tree().getTreeItem("Minimum_Project").contextMenu("Run As").menu("Run Configurations...").click();

		SWTBotShell runConfigDialog = bot.shell("Run Configurations").activate();
		runConfigDialog.bot().tree().getTreeItem("SimuBench").select();
		runConfigDialog.bot().toolbarButtonWithTooltip("New launch configuration").click();
		runConfigDialog.bot().textWithLabel("&Name:").setText("A_Minimum_Configuration");
		runConfigDialog.bot().button("Apply").click();
		runConfigDialog.bot().cTabItem("Architecture Model(s)").show();

		runConfigDialog.bot().buttonInGroup("Workspace...", "Allocation File").click();
		SWTBotShell fileSelectionDialog = bot.shell("File Selection").activate();
		fileSelectionDialog.bot().tree().getTreeItem("Minimum_Project").expand();
		fileSelectionDialog.bot().tree().getTreeItem("Minimum_Project").getNode("default.allocation").select();
		fileSelectionDialog.bot().button("OK").click();
		bot.waitUntil(Conditions.shellCloses(fileSelectionDialog));

		assertEquals("Allocation File has not been set correctly",
				"platform:/resource/Minimum_Project/default.allocation",
				runConfigDialog.bot().textInGroup("Allocation File").getText());

		runConfigDialog.bot().buttonInGroup("Workspace...", "Usage File").click();
		fileSelectionDialog = bot.shell("File Selection").activate();
		fileSelectionDialog.bot().tree().getTreeItem("Minimum_Project").expand();
		fileSelectionDialog.bot().tree().getTreeItem("Minimum_Project").getNode("default.usagemodel").select();
		fileSelectionDialog.bot().button("OK").click();
		bot.waitUntil(Conditions.shellCloses(fileSelectionDialog));

		assertEquals("Usagemodel has not been set correctly", "platform:/resource/Minimum_Project/default.usagemodel",
				runConfigDialog.bot().textInGroup("Usage File").getText());

		runConfigDialog.bot().button("Apply").click();

		runConfigDialog.bot().cTabItem("Simulation").activate();
		runConfigDialog.bot().button("Browse...").click();
		SWTBotShell selectDataSourceDialog = bot.shell("Select Datasource...").activate();
		selectDataSourceDialog.bot().table().click(0, 0);
		selectDataSourceDialog.bot().button("OK").click();
		bot.waitUntil(Conditions.shellCloses(selectDataSourceDialog));

		assertTrue("Data source has not been set",
				runConfigDialog.bot().textWithLabel("Data source:").getText().matches("LocalMemory.*"));

		runConfigDialog.bot().button("Apply").click();
		runConfigDialog.bot().button("Run").click();

		// increase timeout by two minutes
		SWTBotPreferences.TIMEOUT += 120000;

		final SWTBotView consoleView = bot.viewByTitle("Console");
		bot.waitUntil(new DefaultCondition() {

			@Override
			public boolean test() throws Exception {
				consoleView.show();
				return consoleView.bot().styledText().getText().endsWith("Workflow engine completed task\n");
			}

			@Override
			public String getFailureMessage() {
				return "Palladio test run did not terminate or not in an excpeted way!";
			}
		});

		// restore default timeout
		SWTBotPreferences.TIMEOUT -= 120000;

	}

	/**
	 * Helper method that exports the results from the testrun.
	 * 
	 * Note: The calculated results are not checked!
	 */
	private void exportResults() {

		bot.viewByTitle("Experiments").show();
		PalladioSWTree experimentsTree = (PalladioSWTree) bot.tree();

		experimentsTree.getNodeByPath("Local Memory.*").expand();
		experimentsTree.getNodeByPath("Local Memory.*", "Experiment Group.*").expand();
		experimentsTree.getNodeByPath("Local Memory.*", "Experiment Group.*", "Experiment Setting.*").expand();
		experimentsTree
				.getNodeByPath("Local Memory.*", "Experiment Group.*", "Experiment Setting.*", "Experiment Run.*")
				.expand();
		experimentsTree.getNodeByPath("Local Memory.*", "Experiment Group.*", "Experiment Setting.*",
				"Experiment Run.*", "Usage Scenario.*").doubleClick();

		SWTBotShell selectVisualizationDialog = bot.activeShell();
		bot.table().getTableItem("Histogram").select();
		bot.button("Finish").click();
		bot.waitUntil(Conditions.shellCloses(selectVisualizationDialog));

		assertEquals("Chart did not open", "JFreeChartEditor", bot.activeEditor().getTitle());

		experimentsTree.getNodeByPath("Local Memory.*").select();
		bot.toolbarButtonWithTooltip("Export Repository to CSV files").click();
		SWTBotShell selectBatchExportTargetFolderDialog = bot.activeShell();
		bot.button("Workspace...").click();
		SWTBotShell folderSelectionDialog = bot.activeShell();
		bot.tree().getTreeItem("Minimum_Project").select();
		bot.button("OK").click();
		bot.waitUntil(Conditions.shellCloses(folderSelectionDialog));

		assertEquals("Unable to select correct batch export target folder", "platform:/resource/Minimum_Project",
				bot.text().getText());

		bot.button("Finish").click();
		bot.waitUntil(Conditions.shellCloses(selectBatchExportTargetFolderDialog));

		// check if the results are exported

		bot.viewByTitle("Model Explorer").show();
		PalladioSWTree tree = (PalladioSWTree) bot.tree();
		tree.getNodeByPath("Minimum_Project").expand();
		tree.getNodeByPath("Minimum_Project").contextMenu("Refresh").click();
		bot.sleep(2000);
		tree.getNodeByPath("Minimum_Project", "MyRun.*").expand();
		tree.getNodeByPath("Minimum_Project", "MyRun.*", "Default.*").expand();
		tree.getNodeByPath("Minimum_Project", "MyRun.*", "Default.*", ".*").expand();
		tree.getNodeByPath("Minimum_Project", "MyRun.*", "Default.*", ".*",
				"Usage_Scenario_defaultUsageScenario_Response_Time_Tuple.csv").contextMenu("Open With")
				.menu("Text Editor").click();

		assertEquals("generated CSV file could not be opened",
				"Usage_Scenario_defaultUsageScenario_Response_Time_Tuple.csv", bot.activeEditor().getTitle());

	}

	/**
	 * Helper method that deletes the exported CSV-files.
	 */
	private void cleanUpResults() {

		bot.saveAllEditors();
		bot.closeAllEditors();

		bot.viewByTitle("Model Explorer").show();
		PalladioSWTree tree = (PalladioSWTree) bot.tree();
		tree.getNodeByPath("Minimum_Project", "MyRun.*").contextMenu("Delete").click();
		bot.button("OK").click();
		bot.waitUntil(Conditions.shellCloses(bot.activeShell()));

		// check if removal was successful
		for (String nodeText : tree.getNodeByPath("Minimum_Project").getNodes()) {
			if (nodeText.matches("MyRun.*")) {
				fail("Results have not been deleted!");
			}
		}

	}

}
