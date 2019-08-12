package org.palladiosimulator.products.tests.ui.SWTBotWrapper;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.swt.finder.SWTBotTestCase;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.palladiosimulator.products.tests.ui.utils.Constants;

/**
 * Superclass for all test cases.
 */
public class PalladioSWTestCase extends SWTBotTestCase {

	/**
	 * The logger used by instances of this class.
	 */
	private static final Logger log = Logger.getLogger(PalladioSWTestCase.class);

	/**
	 * The initial {@link PalladioSWTGefBot} instance that should be used by the
	 * test case.
	 */
	protected PalladioSWTGefBot bot;

	/**
	 * Constructor.
	 * 
	 * Initializes {@link PalladioSWTestCase#bot} and reads and sets the default
	 * timeout specified by the system property {@code defTimeout}.
	 */
	public PalladioSWTestCase() {

		log.info("Setting up PalladioSWTestCase ...");
		log.debug("initializing bot");
		this.bot = new PalladioSWTGefBot();

		log.debug("setting default timeout");
		final String defTimeout = System.getProperty("defTimeout");
		if (defTimeout != null) {
			try {
				SWTBotPreferences.TIMEOUT = Long.parseLong(defTimeout);
			} catch (final NumberFormatException e) {
				log.warn("Could not parse value of property \"defTimeout\"");
				log.debug(e);
			}
		} else {
			log.warn("could not find system property \"defTimeout\"");
		}

	}

	/**
	 * Setup method for any test.
	 * 
	 * Calls super method, then closes "Welcome"-tab (if present) and afterwards
	 * switches to "Palladio"-perspective.
	 * 
	 * @throws Exception      when setUp-method in super class does
	 * @throws AssertionError if \"Palladio\"-perspective could not be opened
	 */
	@Override
	public void setUp() throws Exception {

		log.info("Setting up test...");
		log.debug("Calling super method");
		super.setUp();

		log.debug("Closing welcome page");
		long timeout = SWTBotPreferences.TIMEOUT;
		try {
			SWTBotPreferences.TIMEOUT = Constants.WELCOME_PAGE_TIMEOUT;
			bot.viewByTitle("Welcome").close();
		} catch (WidgetNotFoundException ignored) {
			log.debug("\"Welcome\"-tab had not been present.");
		} finally {
			SWTBotPreferences.TIMEOUT = timeout;
		}

		log.debug("Activating \"Palladio\"-perspective");
		bot.perspectiveByLabel(Constants.PALLADIO_PERSPECTIVE_LABEL).activate();
		assertEquals("\"Palladio\" perspective not opened", Constants.PALLADIO_PERSPECTIVE_LABEL,
				bot.activePerspective().getLabel());

	}

	/**
	 * Tear down method for every test case.
	 * 
	 * Cleans up everything inside the workspace.
	 */
	@Override
	public void tearDown() {

		log.info("Tearing down after test...");
		log.debug("Resetting workbench...");
		bot.resetWorkbench();
		try {
			log.debug("Trying to clean workspace ...");
			ResourcesPlugin.getWorkspace().getRoot().delete(true, true, null);
		} catch (CoreException e) {
			log.error(e);
		}

		log.info("Tear down complete.");
	}

}
