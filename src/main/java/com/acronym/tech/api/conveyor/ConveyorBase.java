package com.acronym.tech.api.conveyor;

import com.acronym.tech.common.blocks.BlockBase;
import net.minecraft.block.material.Material;

public class ConveyorBase extends BlockBase {

	public ConveyorBase() {
		super(Material.IRON, "transport/conveyor");
		this.setInternalName("conveyor");
	}
}
