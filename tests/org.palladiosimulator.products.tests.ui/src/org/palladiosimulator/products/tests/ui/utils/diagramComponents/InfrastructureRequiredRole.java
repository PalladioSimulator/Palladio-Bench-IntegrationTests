package org.palladiosimulator.products.tests.ui.utils.diagramComponents;

import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTBotGefEditor;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTGefBot;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents.ConnectorDiagramComponent;

/**
 * This represents the "InfrastructureRequired Role" diagram component.
 */
public class InfrastructureRequiredRole extends ConnectorDiagramComponent {

	/**
	 * The name of the "InfrastructureRequiredRole"-tool.
	 */
	public static final String TOOL_NAME = "InfrastructureRequiredRole";

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
	public InfrastructureRequiredRole(PalladioSWTGefBot diagramBot, PalladioSWTBotGefEditor diagram) {
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
		diagram.activateTool(InfrastructureRequiredRole.TOOL_NAME);
	}

}