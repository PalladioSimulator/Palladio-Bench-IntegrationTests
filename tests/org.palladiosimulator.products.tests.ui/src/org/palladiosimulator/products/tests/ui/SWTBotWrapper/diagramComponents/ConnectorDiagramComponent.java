package org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents;

import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTBotGefEditor;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTGefBot;

public abstract class ConnectorDiagramComponent extends DiagramComponent {

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
	public ConnectorDiagramComponent(final PalladioSWTGefBot diagramBot, final PalladioSWTBotGefEditor diagram) {
		super(diagramBot, diagram);
	}

	/**
	 * Adds this connector to the diagram at the supplied positions.
	 * 
	 * Uses {@link PalladioSWTBotGefEditor#click(int, int)} to add the component and
	 * {@link DiagramComponent#initEditPart(String)} to verify that it is present.
	 * 
	 * NOTE: You have to supply the name that this component is going to have
	 * immediately after adding it to the diagram! Otherwise
	 * {@link DiagramComponent#editPart} can not be initialized.
	 * 
	 * @param x1          the origin x position of this connector
	 * @param y1          the origin y position of this connector
	 * @param x2          the target x position of this connector
	 * @param y2          the target y position of this connector
	 * @param displayName the name of this component as displayed in the diagram
	 */
	public void addToDiagram(final int x1, final int y1, final int x2, final int y2, String displayName) {

		activateTool();
		super.diagram.click(x1, y1);
		super.diagram.click(x2, y2);
		initEditPart(displayName);
	}

	/**
	 * Adds this connector to the diagram at the supplied positions.
	 * 
	 * Uses {@link PalladioSWTBotGefEditor#click(int, int)} to add the component and
	 * {@link DiagramComponent#initEditPart(String)} to verify that it is present.
	 * 
	 * NOTE: You have to supply the name that this component is going to have
	 * immediately after adding it to the diagram! Otherwise
	 * {@link DiagramComponent#editPart} can not be initialized.
	 * 
	 * @param origin      the label of the origin {@link SWTBotGefEditPart} of this connector
	 * @param target      the label of the target {@link SWTBotGefEditPart} of this connector
	 * @param displayName the name of this component as displayed in the diagram
	 */
	public void addToDiagram(String origin, String target, String displayName) {

		addToDiagram(origin, 0, 0, target, 0, 0, displayName);
	}

	/**
	 * Adds this connector to the diagram at the supplied positions.
	 * 
	 * Uses {@link PalladioSWTBotGefEditor#click(int, int)} to add the component and
	 * {@link DiagramComponent#initEditPart(String)} to verify that it is present.
	 * 
	 * NOTE: You have to supply the name that this component is going to have
	 * immediately after adding it to the diagram! Otherwise
	 * {@link DiagramComponent#editPart} can not be initialized.
	 * 
	 * @param origin        the label of the origin {@link SWTBotGefEditPart} of this connector
	 * @param target        the label of the target {@link SWTBotGefEditPart} of this connector
	 * @param originOffsetX the x-offset of the click on the origin edit part
	 *                      measured from its top left corner
	 * @param originOffsetY the y-offset of the click on the origin edit part
	 *                      measured from its top left corner
	 * @param targetOffsetX the x-offset of the click on the origin edit part
	 *                      measured from its top left corner
	 * @param targetOffsetY the y-offset of the click on the origin edit part
	 *                      measured from its top left corner
	 * @param displayName   the name of this component as displayed in the diagram
	 */
	public void addToDiagram(String origin, int originOffsetX, int originOffsetY, String target,
			int targetOffsetX, int targetOffsetY, String displayName) {

		activateTool();
		super.diagram.click(origin, originOffsetX, originOffsetY);
		super.diagram.click(target, targetOffsetX, targetOffsetY);
		initEditPart(displayName);
	}

}
