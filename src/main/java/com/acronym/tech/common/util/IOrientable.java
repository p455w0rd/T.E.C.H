package com.acronym.tech.common.util;

import net.minecraft.util.EnumFacing;

public interface IOrientable {
    boolean canBeRotated();

    EnumFacing getForward();

    void setOrientation(EnumFacing forward);
}