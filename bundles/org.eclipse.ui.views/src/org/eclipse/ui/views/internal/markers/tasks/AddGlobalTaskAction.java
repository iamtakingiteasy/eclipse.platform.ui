/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.views.internal.markers.tasks;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.internal.markers.ImageFactory;


public class AddGlobalTaskAction extends Action {
	
	private static final String ENABLED_IMAGE_PATH = "elcl16/addtsk_tsk.gif"; //$NON-NLS-1$
	private static final String HOVER_IMAGE_PATH = "clcl16/addtsk_tsk.gif"; //$NON-NLS-1$
	
	private IWorkbenchPart part;
	
	public AddGlobalTaskAction(IWorkbenchPart part) {
		setText(Messages.getString("addGlobalTaskAction.title")); //$NON-NLS-1$
		setImageDescriptor(ImageFactory.getImageDescriptor(ENABLED_IMAGE_PATH));
		setHoverImageDescriptor(ImageFactory.getImageDescriptor(HOVER_IMAGE_PATH));
		this.part = part;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		TaskPropertiesDialog dialog = new TaskPropertiesDialog(part.getSite().getShell(), 
				Messages.getString("addGlobalTaskDialog.title")); //$NON-NLS-1$
		dialog.open();
	}


}
