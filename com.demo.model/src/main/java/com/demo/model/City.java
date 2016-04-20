package com.demo.model;

import java.io.Serializable;

public class City implements Serializable {

	private static final long serialVersionUID = 1L;

	private short id;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + "]";
	}
}
