package com.sim_launchermove.model;

import java.io.Serializable;

public class CarStateModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7530588209223357760L;

	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public CarStateModel(String state) {
		super();
		this.state = state;
	}

}
