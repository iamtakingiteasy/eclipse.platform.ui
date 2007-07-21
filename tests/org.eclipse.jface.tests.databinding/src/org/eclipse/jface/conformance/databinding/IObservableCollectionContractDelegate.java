/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.jface.conformance.databinding;

import org.eclipse.core.databinding.observable.IObservableCollection;

/**
 * Delegate interface for an IObservableCollection.
 * 
 * <p>
 * This interface is not intended to be implemented by clients. Clients should
 * instead subclass one of the classes that implement this interface. Note that
 * direct implementers of this interface outside of the framework will be broken
 * in future releases when methods are added to this interface.
 * </p>
 * 
 * @since 1.1
 */
public interface IObservableCollectionContractDelegate extends
		IObservableContractDelegate {
	/**
	 * Creates a new observable collection with the provided
	 * <code>elementCount</code>.
	 * 
	 * @param elementCount
	 *            number of elements to initialize the collection with
	 * @return new observable collection
	 */
	public IObservableCollection createObservableCollection(int elementCount);

	/**
	 * Creates a new element of the appropriate type for the provided
	 * <code>collection</code>. This element will be employed to assert the
	 * addition and removal of elements in the collection.
	 * 
	 * @param collection
	 * @return valid element for the collection
	 */
	public Object createElement(IObservableCollection collection);

	/**
	 * Returns the expected type of the elements in the collection.
	 * 
	 * @param collection
	 * @return element type
	 */
	public Object getElementType(IObservableCollection collection);
}
