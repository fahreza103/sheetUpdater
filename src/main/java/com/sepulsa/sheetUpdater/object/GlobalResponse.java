package com.sepulsa.sheetUpdater.object;

import java.io.Serializable;


public class GlobalResponse implements Serializable {

	private static final long serialVersionUID = -2961771275317558594L;
	
	private Meta meta;
	private Object result;
	
	public Meta getMeta() {
		return meta;
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	

}
