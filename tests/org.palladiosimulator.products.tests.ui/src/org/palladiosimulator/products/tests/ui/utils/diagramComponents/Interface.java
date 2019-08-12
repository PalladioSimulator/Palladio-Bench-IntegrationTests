package org.palladiosimulator.products.tests.ui.utils.diagramComponents;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTBotGefEditor;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTGefBot;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents.NodeDiagramComponent;
import org.palladiosimulator.products.tests.ui.utils.Constants;

/**
 * This represents the "Interface" diagram component.
 */
public class Interface extends NodeDiagramComponent {

	/**
	 * The name of the "Interface"-tool.
	 */
	public static final String TOOL_NAME = "Interface";
	/**
	 * The name base of every "Interface".
	 */
	public static final String BASE_NAME = "<<Interface>>\n";
	/**
	 * The name of the GUI-dialog used to select the return type of methods of this interface.
	 */
	public static final String RETURN_TYPE_SELECT_DIALOG_NAME = "Select Object...";

	/**
	 * Constructor.
	 */
	public Interface(PalladioSWTGefBot diagramBot, PalladioSWTBotGefEditor diagram) {
		super(diagramBot, diagram);
	}

	/**
	 * Activates the tool that belongs to this diagram component.
	 */
	@Override
	protected void activateTool() {
		diagram.activateTool(Interface.TOOL_NAME);
	}

	/**
	 * Add a method with the supplied signature to this interface.
	 * 
	 * @param dataTypeRepository a regular expression describing the
	 *                           data-type-repository
	 * @param dataType           the data type that be selected
	 * 
	 */
	public void addMethod(final String dataTypeRepository, final String dataType) {

		diagram.select(this.editPart);
		diagramBot.viewByTitle(Constants.PROPERTIES_VIEW_LABEL).show();

		SWTBot propertiesBot = diagramBot.viewByTitle(Constants.PROPERTIES_VIEW_LABEL).bot();

		/*
		 * Sadly, there is nothing that identifies this button. Hence, this test fails,
		 * when somebody ever changes the order of those buttons or does similiar things
		 * to the UI. It's the same with the table.
		 */
		propertiesBot.toolbarButtonWithTooltip("").click();
		propertiesBot.table().click(0, 1);
		
		propertiesBot.button("...").click();
		
		SWTBotShell dataTypeDialog = diagramBot.shell(Interface.RETURN_TYPE_SELECT_DIALOG_NAME);
		diagramBot.tree().getTreeItemByExpression(dataTypeRepository).select(dataType);
		diagramBot.button("OK").click();
		diagramBot.waitUntil(Conditions.shellCloses(dataTypeDialog));

	}

}
