package org.palladiosimulator.products.tests.ui.SWTBotWrapper;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramEdgeEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramEdgeEditPart.ViewEdgeFigure;
import org.eclipse.sirius.diagram.ui.tools.api.figure.SiriusWrapLabel;
import org.eclipse.sirius.diagram.ui.tools.api.figure.SiriusWrapLabelWithAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefConnectionEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefViewer;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;

/**
 * Wrapper for {@link SWTBotGefViewer}.
 */
public class PalladioSWTGefViewer extends SWTBotGefViewer {

	/**
	 * Constructor.
	 * 
	 * @param gv the graphical viewer to wrap
	 */
	public PalladioSWTGefViewer(final GraphicalViewer gv) {
		super(gv);
	}

	@Override
	protected void init() {
		super.init();
	}

	/**
	 * get this edit part with the label as a single selection.
	 */
	@Override
	public SWTBotGefEditPart getEditPart(String label) {
		List<SWTBotGefEditPart> allEditParts = new LinkedList<>();
		getAllEditParts(mainEditPart(), allEditParts);
		return getEditpart(label, allEditParts);
	}

	/**
	 * Helper method that recursivly finds all editparts and adds it to a supplied
	 * List
	 */
	private void getAllEditParts(final SWTBotGefEditPart ep, final List<SWTBotGefEditPart> list) {

		// add this edit part himself
		list.add(ep);

		// add all source connections of this editpart
		for (SWTBotGefConnectionEditPart con : ep.sourceConnections()) {
			list.add(con);
		}

		// recursively check children
		for (SWTBotGefEditPart child : ep.children()) {
			getAllEditParts(child, list);
		}

	}

	/**
	 * get the edit part that owns the edit part with the supplied label
	 */
	@Override
	public SWTBotGefEditPart getEditpart(String label, List<SWTBotGefEditPart> allEditParts) {
		for (SWTBotGefEditPart ep : allEditParts) {
			IFigure figure = ((GraphicalEditPart) ep.part()).getFigure();

			if (isLabel(figure, label)) {
				if (ep instanceof SWTBotGefConnectionEditPart) {
					return ep;
				} else {
					return ep.parent();
				}
			}
		}
		return null;
	}

	/**
	 * Mostly copied from orgiginal class, but "case 3" and "case 4" is added
	 * 
	 * @return if the figure is a label
	 */
	private boolean isLabel(IFigure figure, String label) {

		// case 1 : gef label
		if ((figure instanceof Label && ((Label) figure).getText().equals(label))) {
			return true;
		}

		// case 2 : no gef label
		if ((figure instanceof TextFlow && ((TextFlow) figure).getText().equals(label))) {
			return true;
		}

		// case 3 : SiriusWrapLabel
		if ((figure instanceof SiriusWrapLabel) && (((SiriusWrapLabel) figure).getText().equals(label))) {
			return true;
		}

		// case 4 : SiriusWrapLabelWithAttachment
		if (figure instanceof AbstractDiagramEdgeEditPart.ViewEdgeFigure) {
			ViewEdgeFigure f = (ViewEdgeFigure) figure;
			for (Object child : f.getChildren()) {
				if (child instanceof SiriusWrapLabelWithAttachment) {
					if (((SiriusWrapLabelWithAttachment) child).getText().equals(label)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Click on the editor at the specified position relative to the supplied
	 * component.
	 * 
	 * This method can be used if clicking on the edit part directly does not work
	 * because it's top left corner is overlaid by other stuff.
	 * 
	 * @param label the edit part to click on
	 * @param x     the x value that should be added to the edit part's x value
	 * @param y     the y value that should be added to the edit part's y value
	 */
	public void click(final String label, int x, int y) {
		SWTBotGefEditPart editPart = getEditPart(label);
		if (editPart == null) {
			throw new WidgetNotFoundException(String.format("Expected to find widget %s", label));
		}
		Rectangle bounds = getAbsoluteBounds(editPart);
		click(bounds.x + x, bounds.y + y);
	}

	/**
	 * Get absolute bounds of the edit part. (method copied from superclass)
	 * 
	 * @param editPart edit part
	 * @return the absolute bounds
	 */
	private Rectangle getAbsoluteBounds(final SWTBotGefEditPart editPart) {
		IFigure figure = ((GraphicalEditPart) editPart.part()).getFigure();
		Rectangle bounds = figure.getBounds().getCopy();
		figure.translateToAbsolute(bounds);
		return bounds;
	}

}
