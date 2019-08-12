package org.palladiosimulator.products.tests.ui.SWTBotWrapper.diagramComponents;

import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTBotGefEditor;
import org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTGefBot;

/**
 * This class represents a component on the diagram.
 */
public abstract class DiagramComponent {

	/**
	 * The {@link PalladioSWTGefBot} that should be used to edit this component.
	 */
	protected final PalladioSWTGefBot diagramBot;
	/**
	 * The {@link PalladioSWTBotGefEditor} the component belongs to.
	 */
	protected final PalladioSWTBotGefEditor diagram;
	/**
	 * The {@link SWTBotGefEditPart} that belongs to this component.
	 */
	protected SWTBotGefEditPart editPart;

	/**
	 * Consturctor.
	 * 
	 * @param diagramBot the {@link PalladioSWTGefBot} that should be used to edit
	 *                   this component
	 * @param diagram    the {@link PalladioSWTBotGefEditor} the component belongs
	 *                   to
	 */
	public DiagramComponent(final PalladioSWTGefBot diagramBot, final PalladioSWTBotGefEditor diagram) {

		if (diagramBot == null || diagram == null) {
			throw new NullPointerException((diagramBot == null ? "diagramBot" : "diagram") + " must not be null!");
		}

		this.diagramBot = diagramBot;
		this.diagram = diagram;

	}

	/**
	 * Tries to get the edit part corresponding to this diagram component from the
	 * diagram.
	 * 
	 * @param displayName the name of this component as displayed in the diagram
	 */
	protected void initEditPart(final String displayName) {

		SWTBotGefEditPart editPart = diagram.getEditPart(displayName);

		if (editPart == null) {
			throw new WidgetNotFoundException("Edit Part with name \"" + displayName + "\" not found on diagram!");
		}

		this.editPart = editPart;
	}

	/**
	 * Activates the tool that belongs to this diagram component.
	 */
	protected abstract void activateTool();

}
