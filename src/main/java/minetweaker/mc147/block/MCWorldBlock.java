/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc147.block;

import java.util.Collections;
import java.util.List;
import minetweaker.api.block.BlockPatternOr;
import minetweaker.api.block.IBlock;
import minetweaker.api.block.IBlockDefinition;
import minetweaker.api.block.IBlockPattern;
import minetweaker.api.data.IData;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import minetweaker.MineTweakerAPI;
import minetweaker.mc147.actions.SetTranslationAction;

/**
 *
 * @author Stan
 */
public class MCWorldBlock implements IBlock {
	private final IBlockAccess blocks;
	private final int x;
	private final int y;
	private final int z;
	
	public MCWorldBlock(IBlockAccess blocks, int x, int y, int z) {
		this.blocks = blocks;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public IBlockDefinition getDefinition() {
		return MineTweakerMC.getBlockDefinition(Block.blocksList[blocks.getBlockId(x, y, z)]);
	}

	@Override
	public int getMeta() {
		return blocks.getBlockMetadata(x, y, z);
	}

	@Override
	public IData getTileData() {
		TileEntity tileEntity = blocks.getBlockTileEntity(x, y, z);
		
		if (tileEntity == null)
			return null;
		
		NBTTagCompound nbt = new NBTTagCompound();
		tileEntity.writeToNBT(nbt);
		return MineTweakerMC.getIData(nbt);
	}

	@Override
	public String getDisplayName() {
		int blockId = blocks.getBlockId(x, y, z);
		if (Item.itemsList[blockId] != null) {
			return (new ItemStack(blockId, 1, getMeta())).getDisplayName();
		} else {
			return Block.blocksList[blockId].translateBlockName();
		}
	}

	@Override
	public void setDisplayName(String name) {
		int blockId = blocks.getBlockId(x, y, z);
		if (Item.itemsList[blockId] != null) {
			 MineTweakerAPI.apply(new SetTranslationAction((new ItemStack(blockId, 1, getMeta())).getItemName() + ".name", name));
		}
	}


	@Override
	public List<IBlock> getBlocks() {
		return Collections.<IBlock>singletonList(this);
	}

	@Override
	public boolean matches(IBlock block) {
		return getDefinition() == block.getDefinition()
				&& (getMeta() == -1 || getMeta() == block.getMeta())
				&& (getTileData() == null || (block.getTileData() != null && block.getTileData().contains(getTileData())));
	}

	@Override
	public IBlockPattern or(IBlockPattern pattern) {
		return new BlockPatternOr(this, pattern);
	}
}
