/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc147.liquid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mc147.util.MineTweakerHacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import minetweaker.MineTweakerAPI;
import minetweaker.mc147.actions.SetTranslationAction;

/**
 *
 * @author Stan
 */
public class MCLiquidDefinition implements ILiquidDefinition {
	private static final Map<List, LiquidContainerData> containerLiquidMap = MineTweakerHacks.getLiquidContainerMap();
	private static final Map<List, LiquidContainerData> filledContainerMap = MineTweakerHacks.getFilledContainerMap();
	private static final Set<List> emptyContainers = MineTweakerHacks.getEmptyContainers();
	
	private final LiquidStack liquid;
	
	public MCLiquidDefinition(LiquidStack liquid) {
		this.liquid = liquid;
	}
	
	@Override
	public String getName() {
		return liquid.asItemStack().getItemName();
	}
	
	@Override
	public String getDisplayName() {
		return liquid.asItemStack().getDisplayName();
	}

	@Override
	public void setDisplayName(String name) {
		MineTweakerAPI.apply(new SetTranslationAction(liquid.asItemStack().getItemName() + ".name", name));
	}

	@Override
	public ILiquidStack asStack(int millibuckets) {
		return new MCLiquidStack(new LiquidStack(liquid.itemID, millibuckets, liquid.itemMeta));
	}

	@Override
	public List<IItemStack> getContainers() {
		List<IItemStack> result = new ArrayList<IItemStack>();
		for (LiquidContainerData data : LiquidContainerRegistry.getRegisteredLiquidContainerData()) {
			if (data.stillLiquid.isLiquidEqual(liquid)) {
				result.add(MineTweakerMC.getIItemStack(data.filled));
			}
		}
		return result;
	}

	@Override
	public void addContainer(IItemStack filled, IItemStack empty, int amount) {
		MineTweakerAPI.apply(new AddContainerAction(filled, empty, amount));
	}

	@Override
	public void removeContainer(IItemStack filled) {
		MineTweakerAPI.apply(new RemoveContainerAction(filled));
	}
	
	// #######################
	// ### Private methods ###
	// #######################
	
	private void removeContainerInner(IItemStack filled) {
		ItemStack filledItem = MineTweakerMC.getItemStack(filled);
		LiquidContainerData data = filledContainerMap.get(Arrays.asList(filledItem.itemID, filledItem.getItemDamage()));
		if (data != null) {
			filledContainerMap.remove(Arrays.asList(filledItem.itemID, filledItem.getItemDamage()));
			containerLiquidMap.remove(Arrays.asList(data.container.itemID, data.container.getItemDamage()));
			
			// rebuild empty containers set
			emptyContainers.clear();
			for (LiquidContainerData fdata : filledContainerMap.values()) {
				emptyContainers.add(Arrays.asList(fdata.container.itemID, fdata.container.getItemDamage()));
			}
		}
	}
	
	private LiquidContainerData getData(IItemStack filled) {
		ItemStack filledStack = MineTweakerMC.getItemStack(filled);
		return filledContainerMap.get(Arrays.asList(filledStack.itemID, filledStack.getItemDamage()));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private class AddContainerAction implements IUndoableAction {
		private final IItemStack filled;
		private final IItemStack empty;
		private final int amount;
		
		public AddContainerAction(IItemStack filled, IItemStack empty, int amount) {
			this.filled = filled;
			this.empty = empty;
			this.amount = amount;
		}

		@Override
		public void apply() {
			LiquidContainerRegistry.registerLiquid(new LiquidContainerData(new LiquidStack(liquid.itemID, amount, liquid.itemMeta), MineTweakerMC.getItemStack(filled), MineTweakerMC.getItemStack(empty)));
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			removeContainerInner(filled);
		}

		@Override
		public String describe() {
			return "Adding " + filled.getDisplayName() + " as liquid container for " + liquid.asItemStack().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing liquid container " + filled;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private class RemoveContainerAction implements IUndoableAction {
		private final IItemStack filled;
		private final IItemStack empty;
		private final LiquidStack amount;
		
		public RemoveContainerAction(IItemStack filled) {
			this.filled = filled;
			
			LiquidContainerData data = getData(filled);
			empty = MineTweakerMC.getIItemStack(data.container);
			amount = data.stillLiquid;
		}

		@Override
		public void apply() {
			removeContainerInner(filled);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			LiquidContainerRegistry.registerLiquid(new LiquidContainerData(amount, MineTweakerMC.getItemStack(filled), MineTweakerMC.getItemStack(empty)));
		}

		@Override
		public String describe() {
			return "Removing liquid container " + filled;
		}

		@Override
		public String describeUndo() {
			return "Restoring liquid container " + filled;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
