/*******************************************************************************
 * Copyright (c) 2000, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.NewWizard;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;
import org.eclipse.ui.internal.registry.WizardsRegistryReader;

/**
 * Standard action for launching the create project selection
 * wizard.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class NewExampleAction extends Action {

    /**
     * The wizard dialog width
     */
    private static final int SIZING_WIZARD_WIDTH = 500;

    /**
     * The wizard dialog height
     */
    private static final int SIZING_WIZARD_HEIGHT = 500;

    /**
     * The workbench window this action will run in
     */
    private IWorkbenchWindow window;

    /**
     * This default constructor allows the the action to be called from the welcome page.
     */
    public NewExampleAction() {
        this(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
    }

    /**
     * Creates a new action for launching the new project
     * selection wizard.
     *
     * @param window the workbench window to query the current
     * 		selection and shell for opening the wizard.
     */
    public NewExampleAction(IWorkbenchWindow window) {
        super(IDEWorkbenchMessages.NewExampleAction_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.window = window;
        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
        setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
        setToolTipText(IDEWorkbenchMessages.NewExampleAction_toolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				org.eclipse.ui.internal.IWorkbenchHelpContextIds.NEW_ACTION);
    }

    @Override
	public void run() {
        // Create wizard selection wizard.
        IWorkbench workbench = PlatformUI.getWorkbench();
        NewWizard wizard = new NewWizard();
        wizard
                .setCategoryId(WizardsRegistryReader.FULL_EXAMPLES_WIZARD_CATEGORY);

        ISelection selection = window.getSelectionService().getSelection();
        IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
        if (selection instanceof IStructuredSelection) {
			selectionToPass = (IStructuredSelection) selection;
		}
        wizard.init(workbench, selectionToPass);
        IDialogSettings workbenchSettings = IDEWorkbenchPlugin.getDefault()
                .getDialogSettings();
        IDialogSettings wizardSettings = workbenchSettings
                .getSection("NewWizardAction"); //$NON-NLS-1$
        if (wizardSettings == null) {
			wizardSettings = workbenchSettings.addNewSection("NewWizardAction"); //$NON-NLS-1$
		}
        wizard.setDialogSettings(wizardSettings);
        wizard.setForcePreviousAndNextButtons(true);

        // Create wizard dialog.
        Shell parent = window.getShell();
        WizardDialog dialog = new WizardDialog(parent, wizard);
        dialog.create();
        wizard.setWindowTitle(IDEWorkbenchMessages.NewExample_title);
        dialog.getShell().setSize(
                Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x),
                SIZING_WIZARD_HEIGHT);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
                IIDEHelpContextIds.NEW_PROJECT_WIZARD);

        // Open wizard.
        dialog.open();
    }
}
