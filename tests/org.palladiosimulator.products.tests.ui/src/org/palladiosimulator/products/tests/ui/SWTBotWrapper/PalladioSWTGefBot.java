package org.palladiosimulator.products.tests.ui.SWTBotWrapper;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.ui.IEditorReference;
import org.hamcrest.Matcher;

/**
 * Wrapper for {@link SWTGefBot} that works with {@link PalladioSWTBotGefEditor}
 * instead of {@link SWTBotGefEditor} and also with {@link PalladioSWTree}
 * instead of {@link SWTBotTree}. Furthermore it provides a method for
 * simulating key typing using {@link Robot}.
 */
public class PalladioSWTGefBot extends SWTGefBot {

	/**
	 * The logger used by instances of this class.
	 */
	private static final Logger log = Logger.getLogger(PalladioSWTGefBot.class);

	/**
	 * The {@link Robot} instance used by {@link #typeKey(int)}.
	 */
	private final Robot robbie;

	/**
	 * Constructor.
	 * 
	 * Initializes this instance.
	 */
	public PalladioSWTGefBot() {

		super();

		log.debug("Trying to initialize java.awt.Robot instance...");
		try {
			this.robbie = new Robot();
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Creates an editor.
	 * 
	 * Uses the wrapper instead of the default SWTBot implementation.
	 */
	@Override
	protected PalladioSWTBotGefEditor createEditor(final IEditorReference ref, final SWTWorkbenchBot bot) {

		log.info("Creating new editor ...");
		return new PalladioSWTBotGefEditor(ref, bot);

	}

	/**
	 * Wrapper for {@link PalladioSWTGefBot#tree(int)}.
	 * 
	 * Just delegates to {@link PalladioSWTGefBot#tree(int)} with param {@code 0}.
	 */
	@Override
	public PalladioSWTree tree() {
		return this.tree(0);
	}

	/**
	 * Getter for the specified tree.
	 * 
	 * Works like the super method, but returns an instance of
	 * {@link PalladioSWTree} instead of {@link SWTBotTree}.
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PalladioSWTree tree(int index) {

		log.info("Getting tree index " + index);
		final Matcher matcher = allOf(widgetOfType(Tree.class));
		return new PalladioSWTree((Tree) widget(matcher, index), matcher);

	}

	/**
	 * Simulates a key typing on the specified key.
	 * 
	 * Uses {@link Robot}.
	 * 
	 * @param keyCode the keyCode of the key that should be typed
	 */
	public void typeKey(final int keyCode) {

		robbie.keyPress(keyCode);
		robbie.keyRelease(keyCode);

	}

	/**
	 * Simulates typing of a whole char sequence.
	 * 
	 * Uses {@link #typeKey(int)} and {@link KeyStroke#getKeyCode()} internally.
	 * 
	 * @param sequence the sequence that should be typed
	 */
	public void typeCharSequence(final CharSequence sequence) {

		sequence.chars().map(i -> KeyEvent.getExtendedKeyCodeForChar((char) i)).forEach(this::typeKey);

	}

	/**
	 * Attempts to locate the Gef editor matching the given name. If no match is
	 * found an exception will be thrown. The name is the name as displayed on the
	 * editor's tab in eclipse.
	 * 
	 * @param fileName the name of the file.
	 * @return an editor for the specified fileName.
	 * @throws WidgetNotFoundException if the editor is not found.
	 */
	public PalladioSWTBotGefEditor gefEditor(String fileName) throws WidgetNotFoundException {
		return (PalladioSWTBotGefEditor) gefEditor(fileName, 0);
	}

}
