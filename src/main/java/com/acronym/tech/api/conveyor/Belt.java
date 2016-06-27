package com.acronym.tech.api.conveyor;

import net.minecraft.item.Item;

public class Belt extends Item {
	private String name;
	private float speed;
	private int stacksize;

	public Belt(String name, float speed, int stacksize) {
		this.name = name;
		this.speed = speed;
		this.stacksize = stacksize;
	}

	public String getName() {
		return this.name;
	}

	public int getStacksize() {
		return stacksize;
	}

	public float getSpeed() {
		return speed;
	}

	public Item getBelt() {
		return this;
	}
}
