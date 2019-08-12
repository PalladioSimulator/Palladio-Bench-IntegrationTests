package org.palladiosimulator.products.tests.ui.utils.diagramComponents;

import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTBotGefEditor;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTGefBot;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents.ConnectorDiagramComponent;

/**
 * This represents the "Required Role" diagram component.
 */
public class RequiredRole extends ConnectorDiagramComponent {

	/**
	 * The name of the "RequiredRole"-tool.
	 */
	public static final String TOOL_NAME = "RequiredRole";

	/**
	 * Constructor.
	 * 
	 * @see ConnectorDiagramComponent#ConnectorDiagramComponent(PalladioSWTGefBot,
	 *      PalladioSWTBotGefEditor)
	 * 
	 * @param diagramBot the {@link PalladioSWTGefBot} that should be used to edit
	 *                   this component
	 * @param diagram    the {@link PalladioSWTBotGefEditor} the component belongs
	 *                   to
	 */
	public RequiredRole(PalladioSWTGefBot diagramBot, PalladioSWTBotGefEditor diagram) {
		super(diagramBot, diagram);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents.
	 * DiagramComponent#activateTool()
	 */
	@Override
	protected void activateTool() {
		diagram.activateTool(RequiredRole.TOOL_NAME);
	}

}