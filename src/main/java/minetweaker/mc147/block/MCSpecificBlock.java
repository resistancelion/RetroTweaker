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
import minetweaker.MineTweakerAPI;
import minetweaker.mc147.actions.SetTranslationAction;

/**
 *
 * @author Stan
 */
public class MCSpecificBlock implements IBlock {
	private final Block block;
	private final int meta;
	
	public MCSpecificBlock(Block block, int meta) {
		this.block = block;
		this.meta = meta;
	}
	
	@Override
	public IBlockDefinition getDefinition() {
		return MineTweakerMC.getBlockDefinition(block);
	}

	@Override
	public int getMeta() {
		return meta;
	}

	@Override
	public IData getTileData() {
		return null;
	}

	@Override
	public List<IBlock> getBlocks() {
		return Collections.<IBlock>singletonList(this);
	}

	@Override
	public boolean matches(IBlock block) {
		return block.getDefinition() == getDefinition()
				&& (meta == -1 || block.getMeta() == meta);
	}

	@Override
	public IBlockPattern or(IBlockPattern pattern) {
		return new BlockPatternOr(this, pattern);
	}

	@Override
	public String getDisplayName() {
		return block.translateBlockName();
	}
	
	@Override
	public void setDisplayName(String name) {
		MineTweakerAPI.apply(new SetTranslationAction(block.getBlockName() + ".name", name));
	}

	@Override
	public String toString() {
		return "<block:" + getBlockId(block) + ":" + (meta == -1 ? '*' : meta) + ">";
	}
	
	private static String getBlockId(Block block)
	{
		return block.getBlockName();
	}
}
