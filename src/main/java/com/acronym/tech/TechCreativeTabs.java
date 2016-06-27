package com.acronym.tech;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class TechCreativeTabs {
	public static final CreativeTabs tab = new CreativeTabs(ModInfo.MODID) {
		@Override
		public Item getTabIconItem() {
			return Items.APPLE;
		}
		@Override
		public String getTabLabel() {
			return ModInfo.MODID;
		}
	};
}
