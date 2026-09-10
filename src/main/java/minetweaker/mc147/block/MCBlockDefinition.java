/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc147.block;

import minetweaker.api.block.IBlockDefinition;
import net.minecraft.block.Block;
import minetweaker.MineTweakerAPI;
import minetweaker.mc147.actions.SetTranslationAction;

/**
 *
 * @author Stan
 */
public class MCBlockDefinition implements IBlockDefinition {
	private final Block block;
	
	public MCBlockDefinition(Block block) {
		if (block == null)
			throw new IllegalArgumentException("block cannot be null");
		
		this.block = block;
	}
	
	public Block getInternalBlock() {
		return block;
	}
	
	public int getInternalId() {
		return block.blockID;
	}

	@Override
	public String getId() {
		return block.getBlockName();
	}
	
	@Override
	public String getDisplayName() {
		return block.translateBlockName();
	}

	@Override
	public void setDisplayName(String name) {
		MineTweakerAPI.apply(new SetTranslationAction(block.getBlockName() + ".name", name));
	}
}
