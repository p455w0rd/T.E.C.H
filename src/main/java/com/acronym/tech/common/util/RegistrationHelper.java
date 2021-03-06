package com.acronym.tech.common.util;

import com.acronym.base.util.IBlockRenderer;
import com.acronym.base.util.IItemRenderer;
import com.acronym.base.util.Platform;
import com.acronym.tech.ModInfo;
import com.acronym.tech.TECH;
import com.acronym.tech.common.blocks.BlockBase;
import com.acronym.tech.common.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Locale;

public class RegistrationHelper {
    public static Block registerBlock(Class<? extends Block> blockClass) {
        return registerBlock(blockClass, ItemBlock.class);
    }

    public static Block registerBlock(Class<? extends Block> blockClass, Class<? extends ItemBlock> itemBlockClass) {
        Block block = null;
        ItemBlock itemBlock;
        String internalName;

        try {
            block = blockClass.getConstructor().newInstance();
            itemBlock = itemBlockClass.getConstructor(Block.class).newInstance(block);

            internalName = ((BlockBase) block).getInternalName();

            if (!internalName.equals(internalName.toLowerCase(Locale.US)))
                throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Item: %s", internalName));

            if (internalName.isEmpty())
                throw new IllegalArgumentException(String.format("Unlocalized name cannot be blank! Item: %s", blockClass.getCanonicalName()));

            block.setRegistryName(ModInfo.MODID, internalName);
            block.setUnlocalizedName(internalName);
            itemBlock.setRegistryName(block.getRegistryName());

            GameRegistry.register(block);
            GameRegistry.register(itemBlock);

            if (block instanceof IBlockRenderer && Platform.isClient()) {
                ((IBlockRenderer) block).registerBlockRenderer();
                ((IBlockRenderer) block).registerBlockItemRenderer();
            }

            TECH.LogHelper.info(String.format("Registered block (%s)", blockClass.getCanonicalName()));
        } catch (Exception ex) {
            TECH.LogHelper.fatal(String.format("Fatal Error while registering block (%s)", blockClass.getCanonicalName()));
            ex.printStackTrace();
        }

        return block;
    }

    public static Item registerItem(Class<? extends Item> itemClass) {
        Item item = null;
        String internalName;

        try {
            item = itemClass.getConstructor().newInstance();

            internalName = ((ItemBase) item).getInternalName();

            if (!internalName.equals(internalName.toLowerCase(Locale.US)))
                throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Item: %s", internalName));

            if (internalName.isEmpty())
                throw new IllegalArgumentException(String.format("Unlocalized name cannot be blank! Item: %s", itemClass.getCanonicalName()));

            item.setRegistryName(ModInfo.MODID, internalName);
            item.setUnlocalizedName(internalName);

            GameRegistry.register(item);

            if (item instanceof IItemRenderer && Platform.isClient()) {
                ((IItemRenderer) item).registerItemRenderer();
            }

            TECH.LogHelper.info(String.format("Registered item (%s)", itemClass.getCanonicalName()));
        } catch (Exception ex) {
            TECH.LogHelper.fatal(String.format("Fatal Error while registering item (%s)", itemClass.getCanonicalName()));
            ex.printStackTrace();
        }

        return item;
    }
}