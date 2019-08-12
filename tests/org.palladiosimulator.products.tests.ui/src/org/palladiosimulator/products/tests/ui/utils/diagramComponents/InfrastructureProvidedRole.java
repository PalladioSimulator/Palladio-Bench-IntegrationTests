package org.palladiosimulator.products.tests.ui.utils.diagramComponents;

import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTBotGefEditor;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTGefBot;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents.ConnectorDiagramComponent;

/**
 * This represents the "InfrastructureProvided Role" diagram component.
 */
public class InfrastructureProvidedRole extends ConnectorDiagramComponent {

	/**
	 * The name of the "InfrastructureProvidedRole"-tool.
	 */
	public static final String TOOL_NAME = "InfrastructureProvidedRole";

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
	public InfrastructureProvidedRole(PalladioSWTGefBot diagramBot, PalladioSWTBotGefEditor diagram) {
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
		diagram.activateTool(InfrastructureProvidedRole.TOOL_NAME);
	}

}