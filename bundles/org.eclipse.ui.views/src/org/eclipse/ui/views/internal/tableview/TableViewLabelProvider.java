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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.internal.markers.IField;


public class TableViewLabelProvider extends LabelProvider implements ITableLabelProvider {

	IField[] fields;
	
	public TableViewLabelProvider(IField[] fields) {
		this.fields = fields;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		if (fields == null || columnIndex < 0 || columnIndex >= fields.length) {
			return null;
		}
		return fields[columnIndex].getImage(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		if (fields == null || columnIndex < 0 || columnIndex >= fields.length) {
			return null;
		}
		return fields[columnIndex].getValue(element);
	}

}
