package com.acronym.tech.common.tileentity;

import com.acronym.base.util.IOrientable;
import com.acronym.base.util.IRotatable;
import com.acronym.base.util.TileHelper;
import com.acronym.tech.common.integration.waila.IWailaHeadMessage;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityBase extends TileEntity implements IWailaHeadMessage, IOrientable, IRotatable, ITickable {
    private String customName;
    private int renderedFragment = 0;
    private NBTTagCompound machineItemData;
    protected EnumFacing forward = EnumFacing.NORTH;

    @Override
    public void update() {

    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound data = new NBTTagCompound();
        writeToNBT(data);
        initMachineData();
        return new SPacketUpdateTileEntity(this.pos, 1, data);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity s35PacketUpdateTileEntity) {
        readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
        worldObj.markBlockRangeForRenderUpdate(this.pos, this.pos);
        markForUpdate();
    }

    public void initMachineData() {

    }

    public void markForUpdate() {
        if (this.renderedFragment > 0) {
            this.renderedFragment |= 0x1;
        } else if (this.worldObj != null) {
            Block block = worldObj.getBlockState(this.pos).getBlock();
            this.worldObj.notifyBlockUpdate(this.pos, worldObj.getBlockState(this.pos), worldObj.getBlockState(this.pos), 3);

            int xCoord = this.pos.getX();
            int yCoord = this.pos.getY();
            int zCoord = this.pos.getZ();

            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord + 1, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord - 1, yCoord, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord + 1, yCoord, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord - 1), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord + 1), block);
        }
    }

    public void markForLightUpdate() {
        if (this.worldObj.isRemote) {
            this.worldObj.notifyBlockUpdate(this.pos, worldObj.getBlockState(this.pos), worldObj.getBlockState(this.pos), 3);
        }

        this.worldObj.checkLightFor(EnumSkyBlock.BLOCK, this.pos);
    }

    public void onChunkLoad() {
        if (this.isInvalid())
            this.validate();

        markForUpdate();
    }

    @Override
    public void onChunkUnload() {
        if (!this.isInvalid())
            this.invalidate();
    }

    public TileEntity getTile() {
        return this;
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public boolean hasCustomName() {
        return (this.customName != null) && (this.customName.length() > 0);
    }

    public String getUnlocalizedName() {
        Item item = Item.getItemFromBlock(worldObj.getBlockState(this.pos).getBlock());
        ItemStack itemStack = new ItemStack(item, 1, getBlockMetadata());
        return itemStack.getUnlocalizedName() + ".name";
    }

    public void setName(String name) {
        this.customName = name;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.customName = nbtTagCompound.hasKey("CustomName") ? nbtTagCompound.getString("CustomName") : null;
        this.machineItemData = nbtTagCompound.hasKey("MachineItemData") ? nbtTagCompound.getCompoundTag("MachineItemData") : null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        if (this.customName != null)
            nbtTagCompound.setString("CustomName", this.customName);
        if (this.machineItemData != null)
            nbtTagCompound.setTag("MachineItemData", machineItemData);
        if (canBeRotated()) {
            nbtTagCompound.setInteger("forward", this.forward.ordinal());
        }
        return super.writeToNBT(nbtTagCompound);
    }

    public NBTTagCompound getMachineItemData() {
        return machineItemData;
    }

    public void setMachineItemData(NBTTagCompound machineItemData) {
        this.machineItemData = machineItemData;
    }

    public IBlockState getBlockState() {
        if (worldObj == null)
            return null;
        return worldObj.getBlockState(pos);
    }

    @Override
    public List<String> getWailaHeadToolTip(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (customName != null)
            currentTip.add(String.format("%s%s%s", TextFormatting.BLUE, TextFormatting.ITALIC, customName));
        return currentTip;
    }

    @Override
    public boolean canBeRotated() {
        return false;
    }

    @Override
    public EnumFacing getForward() {
        return forward;
    }

    @Override
    public void setOrientation(EnumFacing forward) {
        this.forward = forward;
        markDirty();
        markForUpdate();
    }

    public void dropItems() {
        TileHelper.DropItems(this);
    }

    @Override
    public EnumFacing getDirection() {
        return getForward();
    }
}