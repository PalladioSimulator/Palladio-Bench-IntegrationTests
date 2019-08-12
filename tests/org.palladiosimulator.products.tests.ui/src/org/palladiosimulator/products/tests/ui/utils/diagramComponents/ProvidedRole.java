package org.palladiosimulator.products.tests.ui.utils.diagramComponents;

import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTBotGefEditor;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTGefBot;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents.ConnectorDiagramComponent;

/**
 * This represents the "Provided Role" diagram component.
 */
public class ProvidedRole extends ConnectorDiagramComponent {

	/**
	 * The name of the "ProvidedRole"-tool.
	 */
	public static final String TOOL_NAME = "ProvidedRole";

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
	public ProvidedRole(PalladioSWTGefBot diagramBot, PalladioSWTBotGefEditor diagram) {
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
		diagram.activateTool(ProvidedRole.TOOL_NAME);
	}

}
