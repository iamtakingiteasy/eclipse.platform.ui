/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.core.commands.operations;

import org.eclipse.core.runtime.IStatus;

/**
 * <p>
 * IOperationApprover defines an interface for approving the undo or redo of a
 * particular operation within an operation history. Operations that are
 * candidates for undo or redo have already been validated against their current
 * state and according to the rules of the history.
 * </p>
 * <p>
 * By the time an IOperationApprover is consulted, the undo has already been
 * requested. Approvers should return <code>true</code> if the operation
 * should proceed, and <code>false</code> if it should not. When an operation
 * is rejected, it is expected that the object rejecting the operation has
 * already consulted the user if necessary or otherwise provided any necessary
 * information to the user about the rejection.
 * </p>
 * <p>
 * Note: This class/interface is part of a new API under development. It has
 * been added to builds so that clients can start using the new features.
 * However, it may change significantly before reaching stability. It is being
 * made available at this early stage to solicit feedback with the understanding
 * that any code that uses this API may be broken as the API evolves.
 * </p>
 * 
 * @since 3.1
 * @experimental
 */
public interface IOperationApprover {

	/**
	 * Return a status indicating whether the specified operation should be
	 * redone. Any status that does not have severity <code>IStatus.OK</code>
	 * will not be approved. Implementers should not assume that the redo will
	 * be performed when the status is <code>OK</code>, since other operation
	 * approvers may veto the redo.
	 * 
	 * @param operation -
	 *            the operation to be redone
	 * @param history -
	 *            the history redoing the operation
	 * @return the IStatus describing whether the operation is approved. The
	 *         redo will not proceed if the status severity is no
	 *         <code>OK</code>, and the caller requesting the redo will be
	 *         returned the status that caused the rejection. Any other status
	 *         severities will not be interpreted by the history.
	 */
	IStatus proceedRedoing(IUndoableOperation operation, IOperationHistory history);

	/**
	 * Return a status indicating whether the specified operation should be
	 * undone. Any status that does not have severity <code>IStatus.OK</code>
	 * will not be approved. Implementers should not assume that the undo will
	 * be performed when the status is <code>OK</code>, since other operation
	 * approvers can veto the undo.
	 * 
	 * @param operation -
	 *            the operation to be undone
	 * @param history -
	 *            the history undoing the operation
	 * @return the IStatus describing whether the operation is approved. The
	 *         undo will not proceed if the status severity is not
	 *         <code>OK</code>, and the caller requesting the undo will be
	 *         returned the status that caused the rejection. Any other status
	 *         severities will not be interpreted by the history.
	 */
	IStatus proceedUndoing(IUndoableOperation operation, IOperationHistory history);

}
