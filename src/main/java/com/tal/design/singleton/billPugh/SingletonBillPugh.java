package com.tal.design.singleton.billPugh;

/**
 * Bill Pugh Pattern singleton class.
 * <p>
 * final: prevent subclassing
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 7/29/25
 */
public final class SingletonBillPugh {
	private SingletonBillPugh() { }

	private static class Holder {
		private static final SingletonBillPugh INSTANCE = new SingletonBillPugh();
	}

	public SingletonBillPugh getInstance() {
		return Holder.INSTANCE;
	}
}
