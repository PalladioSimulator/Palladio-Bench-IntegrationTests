# PalladioSWTBotWrapper
A wrapper for SWTBot to make it easier to use for testing the PalladioBench.

## How to use
Every test case should extend ```org.palladiosimulator.products.tests.ui.SWTBotWrapper.PalladioSWTestCase``` in order to use the 
custom bot and the custom GefEditor. On the one hand, they ensure that SWTBot functions with the ```SiriusWrapLabel```s, on the 
other hand they provide some useful features, like for instance traversing a tree structure using a regular expression rather than
the exact labels.

A custom value for ```org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences.TIMEOUT``` can be set via the system property 
```defTimeout```. If some tests fail, I'd recommend to try fiddle around a bit with this setting first (raise it first).

The ```PalladioSWTestCase``` provides a setup method that closes the "Welcome"-tab and opens the "Palladio"-perspective. Note that 
you may want to extend this a bit by - for instance - deleting projects from ealier tests from the workspace.

Global contants that affect all test cases, like for instance the timeout for closing the "Welcome"-tab can be set and adjusted in ```org.palladiosimulator.products.tests.ui.utils.Constants```.

## Examples
#### Editor test example
For an editor test example, test case EDITORS98 is used. You can find it under ```org.palladiosimulator.products.tests.ui.editortests.EDITORS98RepositoryDiagramEditorTest```.

#### Program test example
For a program test example, test case PALLADIO193 is used. You can find it under 
```org.palladiosimulator.products.tests.ui.programtests.PALLADIO193MinimumProjectTest```.
