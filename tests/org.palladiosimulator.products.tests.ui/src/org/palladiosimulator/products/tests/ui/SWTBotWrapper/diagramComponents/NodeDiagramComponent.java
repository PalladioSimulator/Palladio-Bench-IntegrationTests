package org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents;

import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTBotGefEditor;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTGefBot;

public abstract class NodeDiagramComponent extends DiagramComponent {

	/**
	 * Constructor.
	 * 
	 * Calls
	 * {@link DiagramComponent#DiagramComponent(PalladioSWTGefBot, PalladioSWTBotGefEditor)}.
	 * 
	 * @param diagramBot the {@link PalladioSWTGefBot} that should be used to edit
	 *                   this component
	 * @param diagram    the {@link PalladioSWTBotGefEditor} the component belongs
	 *                   to
	 */
	public NodeDiagramComponent(final PalladioSWTGefBot diagramBot, final PalladioSWTBotGefEditor diagram) {
		super(diagramBot, diagram);
	}

	/**
	 * Adds this node to the diagram at the supplied position.
	 * 
	 * Uses {@link PalladioSWTBotGefEditor#click(int, int)} to add the component and
	 * {@link DiagramComponent#initEditPart(String)} to verify that it is present.
	 * 
	 * NOTE: You have to supply the name that this component is going to have
	 * immediately after adding it to the diagram! Otherwise
	 * {@link DiagramComponent#editPart} can not be initialized.
	 * 
	 * @param x           the x position of this node
	 * @param y           the y position of this node
	 * @param displayName the name of this component as displayed in the diagram
	 */
	public void addToDiagram(final int x, final int y, String displayName) {

		activateTool();
		super.diagram.click(x, y);
		initEditPart(displayName);

	}

}
