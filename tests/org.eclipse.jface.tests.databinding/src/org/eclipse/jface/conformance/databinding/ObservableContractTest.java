/*******************************************************************************
 * Copyright (c) 2007 Brad Reynolds and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Brad Reynolds - initial API and implementation
 ******************************************************************************/

package org.eclipse.jface.conformance.databinding;

import junit.framework.TestCase;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.Realm;

/**
 * Tests for IObservable that don't require mutating the observable.
 * <p>
 * This class is experimental and can change at any time. It is recommended to
 * not subclass or assume the test names will not change. The only API that is
 * guaranteed to not change are the constructors. The tests will remain public
 * and not final in order to allow for consumers to turn off a test if needed by
 * subclassing.
 * </p>
 * 
 * @since 3.2
 */
public class ObservableContractTest extends TestCase {
	private Realm previousRealm;

	private IObservableContractDelegate delegate;

	public ObservableContractTest(IObservableContractDelegate delegate) {
		super();

		this.delegate = delegate;
	}

	public ObservableContractTest(String testName,
			IObservableContractDelegate delegate) {
		super(testName);
		this.delegate = delegate;
	}

	protected void setUp() throws Exception {
		super.setUp();

		previousRealm = Realm.getDefault();
		delegate.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		delegate.tearDown();
		DummyRealm.setDefaultRealm(previousRealm);
	}

	public void testRealmIsNotNull() throws Exception {
		IObservable observable = delegate.createObservable();

		assertNotNull("The observable's realm should not be null.", observable
				.getRealm());
	}

	public void testChangeFiresChangeEvent() throws Exception {
		ChangeListener listener = new ChangeListener();
		IObservable observable = delegate.createObservable();

		observable.addChangeListener(listener);
		delegate.change(observable);

		assertEquals(
				"A change in the observable should notify change listeners.",
				listener.count, 1);
	}

	public void testChangeEventObservable() throws Exception {
		ChangeListener listener = new ChangeListener();
		IObservable observable = delegate.createObservable();

		observable.addChangeListener(listener);
		delegate.change(observable);

		ChangeEvent event = listener.event;
		assertNotNull("change event was null", event);

		assertSame(
				"In the change event the source of the change should be the observable.",
				observable, event.getObservable());
	}

	public void testObservableRealmIsTheCurrentRealmOnChange() throws Exception {
		IObservable observable = delegate.createObservable();
		ChangeListener listener = new ChangeListener();
		observable.addChangeListener(listener);

		delegate.change(observable);
		assertTrue(
				"On change the current realm should be the realm of the observable.",
				listener.isCurrentRealm);
	}

	public void testRemoveChangeListenerRemovesListener() throws Exception {
		ChangeListener listener = new ChangeListener();
		IObservable observable = delegate.createObservable();

		observable.addChangeListener(listener);
		delegate.change(observable);

		// precondition check
		assertEquals("change did not notify listeners", 1, listener.count);

		observable.removeChangeListener(listener);
		delegate.change(observable);

		assertEquals(
				"When a change listener is removed it should not still receive change events.",
				1, listener.count);
	}

	public void testIsNotStale() throws Exception {
		IObservable observable = delegate.createObservable();

		delegate.setStale(observable, false);
		assertFalse(
				"When an observable is not stale isStale() should return false.",
				observable.isStale());
	}

	/**
	 * Asserts that ObservableTracker.getterCalled(...) is invoked when the
	 * provided <code>runnable</code> is invoked.
	 * 
	 * @param runnable
	 * @param methodName
	 *            method name to display when displaying a message
	 * @param observable
	 *            observable that should be collected by ObservableTracker
	 */
	/* package */static void assertGetterCalled(Runnable runnable,
			String methodName, IObservable observable) {
		IObservable[] observables = ObservableTracker.runAndMonitor(runnable,
				null, null);

		assertEquals(methodName
				+ " should invoke ObservableTracker.getterCalled() once.", 1,
				observables.length);

		assertEquals(
				methodName
						+ " should invoke ObservableTracker.getterCalled() for the observable.",
				observable, observables[0]);
	}

	/**
	 * Workaround to be able to set the default realm outside a runnable. The
	 * setDefaultRealm(...) method is the only usable method.
	 * 
	 * @since 3.2
	 */
	/* package */static class DummyRealm extends Realm {
		/**
		 * Can't be instantiated.
		 */
		private DummyRealm() {
		}

		static void setDefaultRealm(Realm realm) {
			setDefault(realm);
		}

		public boolean isCurrent() {
			throw new UnsupportedOperationException();
		}
	}

	/* package */static class ChangeListener implements IChangeListener {
		int count;

		ChangeEvent event;

		boolean isCurrentRealm;

		public void handleChange(ChangeEvent event) {
			count++;
			this.event = event;
			this.isCurrentRealm = event.getObservable().getRealm().isCurrent();
		}
	}
}
