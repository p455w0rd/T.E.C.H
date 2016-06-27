package com.acronym.tech.api.registry;

import com.acronym.tech.TECH;
import com.acronym.tech.api.conveyor.Belt;

import java.util.ArrayList;
import java.util.List;

public class ConveyorRegistry {
	private static List<Belt> belts = new ArrayList<>();


	public static void registerBelt(Belt belt) {
		if(!belts.contains(belt)) {
			belts.add(belt);
			TECH.LogHelper.info("Registered new Conveyor Belt: "+belt.getName());
		} else {
			TECH.LogHelper.error("Mod tried to register new Belt "+belt.getName()+" Already registered");
		}
	}

	public static boolean isBeltRegistered(Belt belt) {
		return belts.contains(belt);
	}

	public static Belt[] getBeltArray() {
		return belts.toArray(new Belt[belts.toArray().length]);
	}

	public static List<Belt> getRegisteredBelts() {
		return belts;
	}
}
