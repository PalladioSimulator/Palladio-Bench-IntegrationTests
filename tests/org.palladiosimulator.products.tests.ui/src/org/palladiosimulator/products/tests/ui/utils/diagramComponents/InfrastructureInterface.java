package org.palladiosimulator.products.tests.ui.utils.diagramComponents;

import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTBotGefEditor;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTGefBot;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents.NodeDiagramComponent;

/**
 * This represents the "InfrastructureInterface" diagram component.
 */
public class InfrastructureInterface extends NodeDiagramComponent {

	/**
	 * The name of the "InfrastructureInterface"-tool.
	 */
	public static final String TOOL_NAME = "InfrastructureInterface";

	/**
	 * Constructor.
	 * 
	 * @see NodeDiagramComponent#NodeDiagramComponent(PalladioSWTGefBot,
	 *      PalladioSWTBotGefEditor)
	 * 
	 * @param diagramBot the {@link PalladioSWTGefBot} that should be used to edit
	 *                   this component
	 * @param diagram    the {@link PalladioSWTBotGefEditor} the component belongs
	 *                   to
	 */
	public InfrastructureInterface(PalladioSWTGefBot diagramBot, PalladioSWTBotGefEditor diagram) {
		super(diagramBot, diagram);
	}

	/**
	 * Activates the tool that belongs to this diagram component.
	 */
	@Override
	protected void activateTool() {
		diagram.activateTool(InfrastructureInterface.TOOL_NAME);
	}

}
