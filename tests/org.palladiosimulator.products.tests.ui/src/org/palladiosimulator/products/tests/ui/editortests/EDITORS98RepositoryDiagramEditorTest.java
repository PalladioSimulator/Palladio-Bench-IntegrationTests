package org.palladiosimulator.products.tests.ui.editortests;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBotAssert;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTBotGefEditor;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTestCase;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents.DiagramComponent;
import org.palladiosimulator.products.tests.ui.utils.diagramComponents.BasicComponent;
import org.palladiosimulator.products.tests.ui.utils.diagramComponents.InfrastructureInterface;
import org.palladiosimulator.products.tests.ui.utils.diagramComponents.InfrastructureProvidedRole;
import org.palladiosimulator.products.tests.ui.utils.diagramComponents.InfrastructureRequiredRole;
import org.palladiosimulator.products.tests.ui.utils.diagramComponents.Interface;
import org.palladiosimulator.products.tests.ui.utils.diagramComponents.ProvidedRole;
import org.palladiosimulator.products.tests.ui.utils.diagramComponents.RequiredRole;

/**
 * Beschreibung aus dazugehörigem RCPTT Testcase: PCM Instanz starten,
 * Repository Diagramm from scratch erstellen und wizard folgen, Repository
 * Model mit "Repository Model Editor" Tree Editor öffnen, Basic Component "BC1"
 * erstellen, OperationInterface "IP1" erstellen, Provided Role erstellen die
 * die beiden verbindet, Signatur "Sig1" zu Interface "IP1" hinzufügen mit
 * Rückgabetyp "bool" in der properties view, "BC1" löschen, undo löschen, "IP1"
 * löschen, undo löschen, SEFF zu "BC1" hinzufügen und "Sig1" im Dialog als
 * Signatur wählen, Passive Ressource zu "BC1" 's PassiveResourceCompartment
 * hinzufügen, InfrastructureInterface "IP2" erstellen,
 * InfrastructureProvidedRole erstellen die "BC1" mit "IP2" verbindet, Basic
 * Component "BC2" erstellen, RequiredRole erstellen die "BC2" mit "IP1"
 * verbindet, InfrastructureRequiredRole erstellen die "BC2" mit "IP2"
 * verbindet; bei den Änderungen auf Elemente im EMF Tree Editor und
 * Fehlermeldungen in der Konsole achten.
 */
public class EDITORS98RepositoryDiagramEditorTest extends PalladioSWTestCase {

	/**
	 * A map containing all components accessed by their displayed names.
	 */
	private final HashMap<String, DiagramComponent> diagramComponents = new HashMap<>();

	/**
	 * The actual TESTCASE
	 */
	public void testEditors98() {

		createNewModelingProject();

		createRepositoryModelAndDiagram();

		addBasicComponentAndInterfaceToDiagram();

		addProvidedRoleFromBasicComponentToInterface();

		addMethodToInterface();

		deleteAndRestoreComponents();

		bot.saveAllEditors();

		addSEFFToBasicComponent();

		addPassiveResourceToBasicComponent();

		bot.saveAllEditors();

		addInfrastructureInterfaceToDiagram();

		addInfrastructureProvidedRoleFromBasicComponent1ToInfrastructureInterface();

		addBasicComponent2ToDiagram();

		addRequiredRoleFromBasicComponent2ToInterface1();

		addInfrastructureRequiredRoleFromBasicComponent2ToInfrastructureInterface();

		bot.saveAllEditors();

		// end of this test. you could rearrange the diagram components, if you want to

	}

	/**
	 * Helper method that creates the new "modeling project".
	 */
	private void createNewModelingProject() {

		// create a modeling project named "Editor Test"

		bot.menu("File").menu("New").menu("Project...").click();
		SWTBotShell newProjectDialog = bot.activeShell();
		bot.tree().getTreeItem("Sirius").expand();
		bot.tree().getTreeItem("Sirius").getNode("Modeling Project").select();
		bot.button("Next >").click();
		bot.textWithLabel("&Project name:").setText("Editor Test");
		bot.button("Finish").click();

		bot.waitUntil(Conditions.shellCloses(newProjectDialog));

		// check if the project has been created
		bot.viewByTitle("Model Explorer").show();
		SWTBotAssert.assertVisible(bot.tree().getTreeItem("Editor Test"));

	}

	/**
	 * Helper method that creates the repository model and diagram
	 */
	private void createRepositoryModelAndDiagram() {

		String editorTest = "Editor Test";

		// use wizard to generate repository model and diagram

		bot.viewByTitle("Model Explorer").show();
		bot.tree().getTreeItem(editorTest).contextMenu("New").menu("Other...").click();
		SWTBotShell shell = bot.activeShell();
		/*
		 * This was a good menu structure, but for some reasons it got reverted. So back
		 * to the roots ... bot.tree().getTreeItem("Palladio Modeling").expand();
		 * bot.tree().getTreeItem("Palladio Modeling").
		 * getNode("Repository Model and Diagram - Sirius").select();
		 */
		shell.bot().tree().getTreeItem("Other").expand();
		shell.bot().tree().getTreeItem("Other").getNode("Repository Model and Diagram - Sirius").select();
		shell.bot().button("Next >").click();
		shell.bot().button("Finish").click();

		bot.waitUntil(Conditions.shellCloses(shell));

		// check if repository model has been created and if diagram tab is opened

		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			// it seems that on windows the "*" that indicates contents of a folder have
			// changed is "hardcoded" whereas
			// on linux it is just displayed but can be ignored by this test
			editorTest = "*" + editorTest;
		}

		bot.viewByTitle("Model Explorer").show();
		bot.tree().getTreeItem(editorTest).expand();
		SWTBotAssert.assertVisible(bot.tree().getTreeItem(editorTest).getNode("newRepository.repository"));
		bot.editorByTitle("new Repository Diagram").show();
		assertEquals("\"new Repository Diagram\"-editor is not active", "new Repository Diagram",
				bot.activeEditor().getTitle());

	}

	/**
	 * Helper method that adds Basic Component ("B1") and Interface ("IP1") to
	 * diagram.
	 */
	private void addBasicComponentAndInterfaceToDiagram() {

		bot.editorByTitle("new Repository Diagram").show();
		PalladioSWTBotGefEditor diagram = bot.gefEditor("new Repository Diagram");

		BasicComponent bc1 = new BasicComponent(bot, diagram);
		this.diagramComponents.put("<<BasicComponent>>\nBasicComponent1", bc1);

		bc1.addToDiagram(10, 50, "<<BasicComponent>>\nBasicComponent1");

		assertNotNull("BasicComponent1 is not present", diagram.getEditPart("<<BasicComponent>>\nBasicComponent1"));

		Interface if1 = new Interface(bot, diagram);
		this.diagramComponents.put("<<Interface>>\nInterface1", if1);

		if1.addToDiagram(400, 50, "<<Interface>>\nInterface1");

		assertNotNull("Interface1 is not present", diagram.getEditPart("<<Interface>>\nInterface1"));
	}

	/**
	 * Helper method that adds the ProvidedRole between BasicComponent1 and
	 * Interface1.
	 */
	private void addProvidedRoleFromBasicComponentToInterface() {

		// setup

		final String bc1_key = "<<BasicComponent>>\nBasicComponent1";
		final String iface_key = "<<Interface>>\nInterface1";
		final String pRole_key = "<<Provides>>\nBasicComponent1.Interface1.OperationProvidedRole1";

		bot.editorByTitle("new Repository Diagram").show();
		PalladioSWTBotGefEditor diagram = bot.gefEditor("new Repository Diagram");

		// These two checks are actually not necessary, because clicking on not present
		// EditPart would cause an
		// exception; but I use them because of the custom error message.
		assertNotNull("BasicComponent1 is not present", diagram.getEditPart(bc1_key));
		assertNotNull("Interface1 is not present", diagram.getEditPart(iface_key));

		// create the role

		ProvidedRole pRole = new ProvidedRole(bot, diagram);
		this.diagramComponents.put(pRole_key, pRole);
		pRole.addToDiagram(bc1_key, iface_key, pRole_key);

		// validate

		assertNotNull("ProvidedRole not present", diagram.getEditPart(pRole_key));

	}

	/**
	 * Helper method that adds a method to the interface using the properties view.
	 */
	private void addMethodToInterface() {

		// setup

		final String iface_key = "<<Interface>>\nInterface1";

		bot.editorByTitle("new Repository Diagram").show();
		SWTBotGefEditor diagram = bot.gefEditor("new Repository Diagram");

		// add method

		Interface if1 = (Interface) this.diagramComponents.get(iface_key);
		if1.addMethod("PrimitiveDataTypes <Repository>.*", "BOOL <PrimitiveDataType> ");

		// if the method has been added successfully, we should find its EditPart via
		// the
		// method name
		assertNotNull("method has not been added successfully", diagram.getEditPart("bool method1()"));

	}

	/**
	 * Helper method that deletes BC1 and undoes this and then deletes Interface1
	 * and undoes that.
	 * 
	 * After each undo this test verifies that all components are present.
	 */
	private void deleteAndRestoreComponents() {

		// setup

		final String bc1 = "<<BasicComponent>>\nBasicComponent1";
		final String iface = "<<Interface>>\nInterface1";
		final String pRole = "<<Provides>>\nBasicComponent1.Interface1.OperationProvidedRole1";

		bot.editorByTitle("new Repository Diagram").show();
		SWTBotGefEditor diagram = bot.gefEditor("new Repository Diagram");

		// test

		// delete "BasicComponent1" using "DEL" key
		diagram.select(bc1);
		bot.activeShell().pressShortcut(Keystrokes.DELETE);
		assertNull("BasicComponent1 has not been deleted", diagram.getEditPart(bc1));
		assertNull("ProvidedRole has not been deleted", diagram.getEditPart(pRole));

		// undo deletion of BC1 using CTRL+Z
		bot.activeShell().pressShortcut(SWT.CTRL, 'z');
		assertNotNull("BasicComponent1 has not been restored", diagram.getEditPart(bc1));
		assertNotNull("ProvidedRole has not been restored", diagram.getEditPart(pRole));

		// delete "Interface1" using "Delete from Model" toolbar button
		diagram.select(iface);
		bot.toolbarButtonWithTooltip("Delete from Model").click();
		assertNull("Interface1 has not been deleted", diagram.getEditPart(iface));
		assertNull("ProvidedRole has not been deleted", diagram.getEditPart(pRole));

		// undo deletion of "Interface1" using menu "Edit"->"Undo Delete element"
		bot.menu("Edit").menu("Undo Delete element").click();
		assertNotNull("Interface1 has not been restored", diagram.getEditPart(iface));
		assertNotNull("ProvidedRole has not been restored", diagram.getEditPart(pRole));

	}

	/**
	 * Helper method that adds a SEFF to BC1 and chooses Sig1 (method from
	 * Interface1) as signature
	 */
	private void addSEFFToBasicComponent() {

		// setup

		bot.editorByTitle("new Repository Diagram").show();
		PalladioSWTBotGefEditor diagram = bot.gefEditor("new Repository Diagram");
		BasicComponent bc1 = (BasicComponent) this.diagramComponents.get("<<BasicComponent>>\nBasicComponent1");

		// test

		bc1.addSEFF("New Repository <Repository>.*", "Interface1 <OperationInterface>.*",
				"method1 <OperationSignature>.*");

		assertNotNull("SEFF not present", diagram.getEditPart("Interface1.method1"));

	}

	/**
	 * Helper method that adds a PassiveResource to BC1.
	 */
	private void addPassiveResourceToBasicComponent() {

		// setup

		final String prc = "PassiveResourcesCompartment";
		final String pr1 = "PassiveResource1<Capacity: >";

		bot.editorByTitle("new Repository Diagram").show();
		SWTBotGefEditor diagram = bot.gefEditor("new Repository Diagram");

		// test

		diagram.activateTool("Passive Resource");
		diagram.click(prc);

		SWTBotShell shell = bot.activeShell();
		bot.styledText().setText("test");
		bot.button("Cancel").click();
		bot.waitUntil(Conditions.shellCloses(shell));

		assertNotNull("passive resource not present", diagram.getEditPart(pr1));

	}

	/**
	 * Helper method that adds a new InfrastructureInterface to the diagram.
	 */
	private void addInfrastructureInterfaceToDiagram() {

		// setup

		final String iface = "<<InfrastructureInterface>>\nInfrastructureInterface1";

		bot.editorByTitle("new Repository Diagram").show();
		PalladioSWTBotGefEditor diagram = bot.gefEditor("new Repository Diagram");

		// test

		InfrastructureInterface i = new InfrastructureInterface(bot, diagram);
		i.addToDiagram(400, 150, iface);

		this.diagramComponents.put(iface, i);

		assertNotNull("InfrastructureInterface has not been created", diagram.getEditPart(iface));

	}

	/**
	 * Helper method that adds an InfrastructureProvidedRole between BC1 and
	 * InfrastructureInterface1.
	 */
	private void addInfrastructureProvidedRoleFromBasicComponent1ToInfrastructureInterface() {

		// setup

		final String iface = "<<InfrastructureInterface>>\nInfrastructureInterface1";
		final String bc1 = "<<BasicComponent>>\nBasicComponent1";
		final String ipRole = "<<Provides>>\nBasicComponent1.InfrastructureInterface1.InfrastructureProvidedRole1";

		bot.editorByTitle("new Repository Diagram").show();
		PalladioSWTBotGefEditor diagram = bot.gefEditor("new Repository Diagram");

		// test

		// check if the two components are present
		// this is actually not needed because clicking into the nirvana would cause a
		// fail as well, but this gives nicer error msgs
		assertNotNull("BasicComponent1 not present", diagram.getEditPart(bc1));
		assertNotNull("InfrastructureInterface1 not present", diagram.getEditPart(iface));

		// draw connection

		InfrastructureProvidedRole role = new InfrastructureProvidedRole(bot, diagram);
		role.addToDiagram(bc1, iface, ipRole);
		this.diagramComponents.put(ipRole, role);

		assertNotNull("InfrastructureProvidedRole1 has not been created", diagram.getEditPart(ipRole));

	}

	/**
	 * Helper method that adds BasicComponent2 to diagram.
	 */
	private void addBasicComponent2ToDiagram() {

		// setup

		final String bc2 = "<<BasicComponent>>\nBasicComponent2";

		bot.editorByTitle("new Repository Diagram").show();
		PalladioSWTBotGefEditor diagram = bot.gefEditor("new Repository Diagram");

		// test

		BasicComponent bc = new BasicComponent(bot, diagram);
		bc.addToDiagram(10, 270, bc2);
		this.diagramComponents.put(bc2, bc);

		assertNotNull("BasicComponent2 has not been created", diagram.getEditPart(bc2));

	}

	/**
	 * Helper method that adds a RequiredRole between BC2 and IF1
	 */
	private void addRequiredRoleFromBasicComponent2ToInterface1() {

		// setup

		final String bc2 = "<<BasicComponent>>\nBasicComponent2";
		final String iface = "<<Interface>>\nInterface1";
		final String rRole = "<<Requires>>\nBasicComponent2.Interface1.OperationRequiredRole1";

		bot.editorByTitle("new Repository Diagram").show();
		PalladioSWTBotGefEditor diagram = bot.gefEditor("new Repository Diagram");

		// test

		// These two checks are actually not necessary, because clicking on not present
		// EditPart would cause an
		// exception; but I use them because of the custom error message.
		assertNotNull("BasicComponent2 is not present", diagram.getEditPart(bc2));
		assertNotNull("Interface1 is not present", diagram.getEditPart(iface));

		// create the role

		RequiredRole r = new RequiredRole(bot, diagram);
		r.addToDiagram(bc2, 0, 0, iface, 0, 10, rRole);
		this.diagramComponents.put(rRole, r);

		// validate

		assertNotNull("RequiredRole1 not present", diagram.getEditPart(rRole));

	}

	/**
	 * Helper method that adds an InfrastructureRequiredRole between BC2 and
	 * InfrastructureInterface1
	 */
	private void addInfrastructureRequiredRoleFromBasicComponent2ToInfrastructureInterface() {

		// setup

		final String iface = "<<InfrastructureInterface>>\nInfrastructureInterface1";
		final String bc2 = "<<BasicComponent>>\nBasicComponent2";
		final String irRole = "<<Requires>>\nBasicComponent2.InfrastructureInterface1.InfrastructureRequiredRole1";

		bot.editorByTitle("new Repository Diagram").show();
		PalladioSWTBotGefEditor diagram = bot.gefEditor("new Repository Diagram");

		// test

		// check if the two components are present
		// this is actually not needed because clicking into the nirvana would cause a
		// fail as well, but this gives nicer error msgs
		assertNotNull("BasicComponent2 not present", diagram.getEditPart(bc2));
		assertNotNull("InfrastructureInterface1 not present", diagram.getEditPart(iface));

		// draw connection
		InfrastructureRequiredRole ifr = new InfrastructureRequiredRole(bot, diagram);
		ifr.addToDiagram(bc2, 10, 0, iface, 10, 0, irRole);
		this.diagramComponents.put(irRole, ifr);

		assertNotNull("InfrastructureRequiredRole1 has not been created", diagram.getEditPart(irRole));

	}

}
