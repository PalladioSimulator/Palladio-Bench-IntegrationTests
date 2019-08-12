package org.palladiosimulator.products.tests.ui.SWTBotWrapper;

import java.util.LinkedList;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.hamcrest.Matcher;

public class PalladioSWTree extends SWTBotTree {

	/**
	 * Constructor.
	 * 
	 * @param tree the widget
	 */
	public PalladioSWTree(Tree tree) {

		super(tree);

	}

	/**
	 * Constructor.
	 * 
	 * @param tree    the widget
	 * @param matcher the description of the widget
	 * @throws WidgetNotFoundException if the widget is {@code null} or has been
	 *                                 disposed
	 */
	@SuppressWarnings("rawtypes")
	public PalladioSWTree(Tree tree, Matcher matcher) throws WidgetNotFoundException {

		super(tree, matcher);

	}

	/**
	 * Gets the tree item matching the given regex.
	 *
	 * @param regex a regex the text on the node should match
	 * @return the tree item whose text is matching the supplied regex
	 * @throws WidgetNotFoundException if the node was not found.
	 */
	public SWTBotTreeItem getTreeItemByExpression(final String regex) throws WidgetNotFoundException {

		final SWTBotTreeItem[] allItems = getAllItems();
		int i;

		for (i = 0; i < allItems.length && !allItems[i].getText().matches(regex); i++)
			;

		if (i >= allItems.length) {
			throw new WidgetNotFoundException("no tree item matching \"" + regex + "\" has been found");
		}

		return allItems[i];
	}

	/**
	 * Gets the tree item / node at the end of the specified path.
	 * 
	 * Traverses the tree using the given regular expressions to find the correct
	 * tree item / node (they use these two words for the same thing).
	 * 
	 * Note that this method is only here because it would be much more work to
	 * override the "getNode()" method of a SWTBotTreeItem, but eventually, this
	 * should be done.
	 * 
	 * @param regexes the regexes specifying the path that should be used to
	 *                traverse the tree
	 * @return the item
	 */
	public SWTBotTreeItem getNodeByPath(String... regexes) throws WidgetNotFoundException {

		if (regexes.length == 1) {
			return getTreeItemByExpression(regexes[0]);
		} else if (regexes.length > 1) {

			final SWTBotTreeItem start = getTreeItemByExpression(regexes[0]);
			final LinkedList<String> expressions = new LinkedList<>();
			for (int i = 1; i < regexes.length; i++) {
				expressions.add(regexes[i]);
			}

			return getNodeByPath(expressions, start);
		}

		return null;
	}

	public SWTBotTreeItem getNodeByPath(final LinkedList<String> regexes, final SWTBotTreeItem start) {

		if (regexes.isEmpty()) {
			return start;
		}

		final String regex = regexes.removeFirst();

		for (String nodeText : start.getNodes()) { // why in the name of god do they only return the text of a node?!

			if (nodeText.matches(regex)) {
				return getNodeByPath(regexes, start.getNode(nodeText));
			}

		}

		return null;
	}

}
