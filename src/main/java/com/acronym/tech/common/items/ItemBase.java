package com.acronym.tech.common.items;

import com.acronym.base.util.IItemRenderer;
import com.acronym.tech.ModInfo;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemBase extends Item implements IItemRenderer {
    protected String resourcePath = "";
    protected String internalName = "";

    public ItemBase(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public String getUnlocalizedName() {
        String itemName = getUnwrappedUnlocalizedName(super.getUnlocalizedName());

        String test = String.format("item.%s.%s", ModInfo.MODID, itemName);
        return test;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String itemName = getUnwrappedUnlocalizedName(super.getUnlocalizedName(stack));

        String test = String.format("item.%s.%s", ModInfo.MODID, itemName);
        return test;
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerItemRenderer() {
        final String resourcePath = String.format("%s:%s", ModInfo.MODID, this.resourcePath);
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(resourcePath, "inventory"));
    }
}
