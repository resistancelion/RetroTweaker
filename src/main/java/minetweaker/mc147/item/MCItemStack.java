/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc147.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import minetweaker.MineTweakerAPI;
import minetweaker.api.block.IBlock;
import minetweaker.api.data.DataMap;
import minetweaker.api.data.IData;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemCondition;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IItemTransformer;
import minetweaker.api.item.IngredientItem;
import minetweaker.api.item.IngredientOr;
import minetweaker.api.item.WeightedItemStack;
import minetweaker.api.liquid.ILiquidStack;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.api.player.IPlayer;
import minetweaker.mc147.actions.SetTranslationAction;
import minetweaker.mc147.block.MCItemBlock;
import minetweaker.mc147.data.NBTConverter;
import minetweaker.mc147.liquid.MCLiquidStack;
import minetweaker.util.ArrayUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author Stan
 */
public class MCItemStack implements IItemStack {
	private final ItemStack stack;
	private final List<IItemStack> items;
	private IData tag = null;
	private boolean wildcardSize;
	
	public MCItemStack(ItemStack itemStack) {
		if (itemStack == null) throw new IllegalArgumentException("stack cannot be null");
		
		stack = itemStack.copy();
		items = Collections.<IItemStack>singletonList(this);
	}
	
	public MCItemStack(ItemStack itemStack, boolean wildcardSize) {
		this(itemStack);
		
		this.wildcardSize = wildcardSize;
	}
	
	private MCItemStack(ItemStack itemStack, IData tag) {
		if (itemStack == null) throw new IllegalArgumentException("stack cannot be null");
		
		stack = itemStack;
		items = Collections.<IItemStack>singletonList(this);
		this.tag = tag;
	}
	
	private MCItemStack(ItemStack itemStack, IData tag, boolean wildcardSize) {
		stack = itemStack;
		items = Collections.<IItemStack>singletonList(this);
		this.tag = tag;
		this.wildcardSize = wildcardSize;
	}

	@Override
	public IItemDefinition getDefinition() {
		return new MCItemDefinition(stack.getItem());
	}

	@Override
	public String getName() {
		return stack.getItemName();
	}

	@Override
	public String getDisplayName() {
		return stack.getDisplayName();
	}
	
	@Override
	public void setDisplayName(String name) {
		MineTweakerAPI.apply(new SetTranslationAction(stack.getItemName() + ".name", name));
	}

	@Override
	public int getDamage() {
		return stack.getItemDamage();
	}

	@Override
	public IData getTag() {
		if (tag == null) {
			if (stack.stackTagCompound == null) {
				return DataMap.EMPTY;
			}
			
			tag = NBTConverter.from(stack.stackTagCompound, true);
		}
		return tag;
	}

	@Override
	public int getMaxDamage() {
		return stack.getMaxDamage();
	}

	@Override
	public int getMaxStackSize() {
		return stack.getMaxStackSize();
	}

	@Override
	public void setMaxStackSize(int size) {
		stack.getItem().setMaxStackSize(size);
	}

	@Override
	public float getBlockHardness() {
		if (stack.getItem() instanceof ItemBlock) {
			try {
				return Block.blocksList[((ItemBlock)stack.getItem()).getBlockID()].getBlockHardness(null, 0, 0, 0);
			} catch (NullPointerException e) {
				return 0;
			}
		}
		return 0;
	}

	@Override
	public void setBlockHardness(float hardness) {
		if (stack.getItem() instanceof ItemBlock) {
			Block.blocksList[((ItemBlock)stack.getItem()).getBlockID()].setHardness(hardness);
		}
	}
	
	@Override
	public ILiquidStack getLiquid() {
		LiquidStack liquid = LiquidContainerRegistry.getLiquidForFilledItem(stack);
		return liquid == null ? null : new MCLiquidStack(liquid);
	}

	@Override
	public IIngredient anyDamage() {
		if (stack.getItem().getHasSubtypes()) {
			MineTweakerAPI.logWarning("subitems don't have damaged states");
			return this;
		} else {
			ItemStack result = new ItemStack(stack.getItem(), stack.stackSize, -1);
			result.stackTagCompound = stack.stackTagCompound;
			return new MCItemStack(result, tag);
		}
	}

	@Override
	public IItemStack withDamage(int damage) {
		if (stack.getItem().getHasSubtypes()) {
			MineTweakerAPI.logWarning("subitems don't have damaged states");
			return this;
		} else {
			ItemStack result = new ItemStack(stack.getItem(), stack.stackSize, damage);
			result.stackTagCompound = stack.stackTagCompound;
			return new MCItemStack(result, tag);
		}
	}

	@Override
	public IItemStack withAmount(int amount) {
		ItemStack result = new ItemStack(stack.getItem(), amount, stack.getItemDamage());
		result.stackTagCompound = stack.stackTagCompound;
		return new MCItemStack(result, tag);
	}
	
	@Override
	public IItemStack anyAmount() {
		ItemStack result = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
		result.stackTagCompound = stack.stackTagCompound;
		return new MCItemStack(result, tag, true);
	}

	@Override
	public IItemStack withTag(IData tag) {
		ItemStack result = new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage());
		if (tag == null) {
			result.stackTagCompound = null;
		} else {
			result.stackTagCompound = (NBTTagCompound) NBTConverter.from(tag);
		}
		return new MCItemStack(result, tag);
	}

	@Override
	public IItemStack updateTag(IData tagUpdate) {
		if (tag == null) {
			if (stack.stackTagCompound == null) {
				return withTag(tagUpdate);
			}
			
			tag = NBTConverter.from(stack.stackTagCompound, true);
		}
		
		IData updated = tag.update(tagUpdate);
		return withTag(updated);
	}

	@Override
	public String getMark() {
		return null;
	}

	@Override
	public int getAmount() {
		return stack.stackSize;
	}

	@Override
	public List<IItemStack> getItems() {
		return items;
	}
	
	@Override
	public List<ILiquidStack> getLiquids() {
		return Collections.emptyList();
	}

	@Override
	public IItemStack amount(int amount) {
		return withAmount(amount);
	}
	
	@Override
	public WeightedItemStack percent(float chance) {
		return new WeightedItemStack(this, chance * 0.01f);
	}
	
	@Override
	public WeightedItemStack weight(float chance) {
		return new WeightedItemStack(this, chance);
	}

	@Override
	public IIngredient transform(IItemTransformer transformer) {
		return new IngredientItem(this, null, ArrayUtil.EMPTY_CONDITIONS, new IItemTransformer[] { transformer });
	}

	@Override
	public IIngredient only(IItemCondition condition) {
		return new IngredientItem(this, null, new IItemCondition[] { condition }, ArrayUtil.EMPTY_TRANSFORMERS);
	}

	@Override
	public IIngredient marked(String mark) {
		return new IngredientItem(this, mark, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS);
	}
	
	@Override
	public IIngredient or(IIngredient ingredient) {
		return new IngredientOr(this, ingredient);
	}

	@Override
	public boolean matches(IItemStack item) {
		if (item == null)
			return false;
		
		ItemStack internal = getItemStack(item);
		if (internal == null) {
			throw new RuntimeException("Invalid item: " + item);
		}
		
		return internal.getItem() == stack.getItem()
				&& (wildcardSize || internal.stackSize >= stack.stackSize)
				&& (stack.getItemDamage() == -1
					|| stack.getItemDamage() == internal.getItemDamage()
					|| (!stack.getHasSubtypes() && !stack.getItem().isDamageable()));
	}
	
	@Override
	public boolean matches(ILiquidStack liquid) {
		return false;
	}

	@Override
	public boolean contains(IIngredient ingredient) {
		if (ingredient == null)
			return false;
		
		List<IItemStack> iitems = ingredient.getItems();
		if (iitems == null || iitems.size() != 1) return false;
		return matches(iitems.get(0));
	}

	@Override
	public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
		return item;
	}

	@Override
	public boolean hasTransformers() {
		return false;
	}

	@Override
	public IBlock asBlock() {
		if (stack.itemID >= Block.blocksList.length || Block.blocksList[stack.itemID] == null) {
			throw new ClassCastException("This item is not a block");
		} else {
			return new MCItemBlock(stack);
		}
	}

	@Override
	public Object getInternal() {
		return stack;
	}
	
	@Override
	public List<IOreDictEntry> getOres() {
		List<IOreDictEntry> result = new ArrayList<IOreDictEntry>();
		
		for (String key : OreDictionary.getOreNames()) {
			for (ItemStack is : OreDictionary.getOres(key)) {
				if (is.getItem() == stack.getItem()
						&& (is.getItemDamage() == -1 || is.getItemDamage() == stack.getItemDamage())) {
					result.add(MineTweakerAPI.oreDict.get(key));
					break;
				}
			}
		}
		
		return result;
	}
	
	// #############################
	// ### Object implementation ###
	// #############################
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 41 * hash + stack.itemID;
		hash = 41 * hash + stack.getItemDamage();
		hash = 41 * hash + stack.stackSize;
		hash = 41 * hash + (stack.stackTagCompound == null ? 0 : stack.stackTagCompound.hashCode());
		hash = 41 * hash + (this.wildcardSize ? 1 : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MCItemStack other = (MCItemStack) obj;
		if (this.stack.itemID != other.stack.itemID) return false;
		if (this.stack.getItemDamage() != other.stack.getItemDamage()) return false;
		if (this.stack.stackSize != other.stack.stackSize) return false;
		if (this.stack.stackTagCompound != other.stack.stackTagCompound && (this.stack == null || this.stack.equals(other.stack))) return false;
		if (this.wildcardSize != other.wildcardSize) return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append('<');
		result.append(stack.getItem().getItemName());

		if (stack.getItemDamage() == -1) {
			result.append(":*");
		} else if (stack.getItemDamage() > 0) {
			result.append(':').append(stack.getItemDamage());
		}
		result.append('>');

		if (stack.getTagCompound() != null) {
			result.append(".withTag(");
			result.append(NBTConverter.from(stack.getTagCompound(), wildcardSize).toString());
			result.append(")");
		}
		
		if (!wildcardSize) {
			result.append(" * ").append(stack.stackSize);
		}

		return result.toString();
	}
}
