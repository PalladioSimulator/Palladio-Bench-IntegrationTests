package org.palladiosimulator.products.tests.ui.SWTBotWrapper;

import java.lang.reflect.Field;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;

public class PalladioSWTBotGefEditor extends SWTBotGefEditor {

	/**
	 * Constructor.
	 * 
	 * @param reference the editor reference
	 * @param bot       the workbench bot
	 * @throws WidgetNotFoundException if widget could not be found
	 */
	public PalladioSWTBotGefEditor(final IEditorReference reference, final SWTWorkbenchBot bot)
			throws WidgetNotFoundException {

		super(reference, bot);

		GraphicalViewer graphicalViewer = UIThreadRunnable.syncExec(new Result<GraphicalViewer>() {
			public GraphicalViewer run() {
				final IEditorPart editor = partReference.getEditor(true);
				return editor.getAdapter(GraphicalViewer.class);
			}
		});

		try {
			final Field field = SWTBotGefEditor.class.getDeclaredField("viewer");
			field.setAccessible(true);
			field.set(this, new PalladioSWTGefViewer(graphicalViewer));
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		((PalladioSWTGefViewer) viewer).init();

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
	public void click(final String label, final int x, final int y) {
		((PalladioSWTGefViewer) viewer).click(label, x, y);
	}

}
