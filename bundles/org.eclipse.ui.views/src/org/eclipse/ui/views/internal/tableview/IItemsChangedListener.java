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

package org.eclipse.ui.views.internal.tableview;

import java.util.List;


public interface IItemsChangedListener {

	/**
	 * @param additions new items added
	 * @param removals items removed
	 * @param changes changed items
	 */
	public void itemsChanged(List additions, List removals, List changes);

}
