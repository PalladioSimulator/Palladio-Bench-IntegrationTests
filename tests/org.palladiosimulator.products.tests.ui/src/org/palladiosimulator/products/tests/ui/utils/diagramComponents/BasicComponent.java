package org.palladiosimulator.products.tests.ui.utils.diagramComponents;

import org.eclipse.gef.EditPart;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTBotGefEditor;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTGefBot;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents.NodeDiagramComponent;

/**
 * This represents the "BasicComponent" diagram component.
 */
public class BasicComponent extends NodeDiagramComponent {

	/**
	 * The name base of every BasicComponent.
	 */
	public static final String BASE_NAME = "<<BasicComponent>>\n";
	/**
	 * The name of the "SEFF" tool.
	 */
	public static final String SEFF_TOOL_NAME = "SEFF";
	/**
	 * The name of the "SEFFCompartment".
	 */
	public static final String SEFF_COMPARTMENT_NAME = "SEFFCompartment";
	/**
	 * The name of the "Passive Resource" tool.
	 */
	public static final String PASSIVE_RESOURCE_TOOL_NAME = "Passive Resource";
	/**
	 * The name of the "PassiveResourcesCompartment".
	 */
	public static final String PASSIVE_RESOURCES_COMPARTMENT_NAME = "PassiveResourcesCompartment";
	/**
	 * The name of the "BasicComponent" tool.
	 */
	public static final String TOOL_NAME = "BasicComponent";

	/**
	 * Constructor.
	 */
	public BasicComponent(PalladioSWTGefBot diagramBot, PalladioSWTBotGefEditor diagram) {
		super(diagramBot, diagram);
	}

	/**
	 * Activates the tool that belongs to this diagram component.
	 */
	@Override
	protected void activateTool() {
		super.diagram.activateTool(BasicComponent.TOOL_NAME);
	}

	/**
	 * Adds a SEFF with the specified path to this BasicComponent.
	 * 
	 * @param pathToSignature the path to the signature in the corresponding shell
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void addSEFF(String... pathToSignature) {

		super.diagram.activateTool(BasicComponent.SEFF_TOOL_NAME);
		super.diagram.getEditpart(BasicComponent.SEFF_COMPARTMENT_NAME,
				this.editPart.descendants((Matcher<? extends EditPart>) IsAnything.anything())).click();

		SWTBotShell signatureDialog = super.diagramBot.activeShell();
		diagramBot.tree().getNodeByPath(pathToSignature).select();
		diagramBot.button("OK").click();
		super.diagramBot.waitUntil(Conditions.shellCloses(signatureDialog));

	}

	/**
	 * Adds a Passive Resource to this BasicComponent.
	 * 
	 * @param text          the text that should be set
	 * @param confirmDialog {@code true}, if the dialog should be closed by pressing
	 *                      "OK", {@code false} if the dialog should be closed by
	 *                      pressing "Cancel"
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void addPassiveResource(String text, boolean confirmDialog) {

		super.diagram.activateTool(BasicComponent.PASSIVE_RESOURCE_TOOL_NAME);
		super.diagram.getEditpart(BasicComponent.PASSIVE_RESOURCES_COMPARTMENT_NAME,
				this.editPart.descendants((Matcher<? extends EditPart>) IsAnything.anything())).click();

		SWTBotShell stochasticExpressionDialog = super.diagramBot.activeShell();
		stochasticExpressionDialog.bot().styledText().setText(text);
		stochasticExpressionDialog.bot().button(confirmDialog ? "OK" : "Cancel").click();
		super.diagramBot.waitUntil(Conditions.shellCloses(stochasticExpressionDialog));

	}

}
