package com.acronym.tech.common.items;

import com.acronym.tech.common.util.RegistrationHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum Items {

    ;

    private final Class<? extends Item> itemClass;
    private Item item;

    Items(Class<? extends Item> itemClass) {
        this.itemClass = itemClass;
    }

    public static void registerItems() {
        for (Items i : Items.values()) {
            i.registerItem();
        }
    }

    public ItemStack getStack() {
        return new ItemStack(item);
    }

    public ItemStack getStack(int size) {
        return new ItemStack(item, size);
    }

    public ItemStack getStack(int size, int damage) {
        return new ItemStack(item, size, damage);
    }

    public Item getItem() {
        return this.item;
    }

    private void registerItem() {
        item = RegistrationHelper.registerItem(itemClass);
    }
}