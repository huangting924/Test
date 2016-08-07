package com.sim_launchermove.model;

import java.io.Serializable;

public class CarModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3672965118315881890L;

	private String carName;
	private String carState;

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getCarState() {
		return carState;
	}

	public void setCarState(String carState) {
		this.carState = carState;
	}

	public CarModel(String carName, String carState) {
		super();
		this.carName = carName;
		this.carState = carState;
	}

	
}
