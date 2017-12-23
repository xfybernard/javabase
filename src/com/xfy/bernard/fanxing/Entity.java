package com.xfy.bernard.fanxing;

import java.io.Serializable;

public class Entity<PK extends Serializable> {

	private PK id;

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}
}
