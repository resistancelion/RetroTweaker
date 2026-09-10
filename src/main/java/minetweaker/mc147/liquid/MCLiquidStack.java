/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc147.liquid;

import java.util.Collections;
import java.util.List;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemCondition;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IItemTransformer;
import minetweaker.api.item.IngredientOr;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.player.IPlayer;
import net.minecraftforge.liquids.LiquidStack;
import minetweaker.MineTweakerAPI;
import minetweaker.mc147.actions.SetTranslationAction;

/**
 *
 * @author Stan
 */
public class MCLiquidStack implements ILiquidStack {
	private final LiquidStack stack;
	
	public MCLiquidStack(LiquidStack stack) {
		this.stack = stack;
	}

	@Override
	public ILiquidDefinition getDefinition() {
		return new MCLiquidDefinition(stack);
	}
	
	@Override
	public String getName() {
		return stack.asItemStack().getItemName();
	}
	
	@Override
	public String getDisplayName() {
		return stack.asItemStack().getDisplayName();
	}

	@Override
	public void setDisplayName(String name) {
		MineTweakerAPI.apply(new SetTranslationAction(stack.asItemStack().getItemName() + ".name", name));
	}

	@Override
	public int getAmount() {
		return stack.amount;
	}

	@Override
	public ILiquidStack withAmount(int amount) {
		LiquidStack result = new LiquidStack(stack.itemID, amount, stack.itemMeta);
		return new MCLiquidStack(result);
	}

	@Override
	public Object getInternal() {
		return stack;
	}
	
	// ##################################
	// ### IIngredient implementation ###
	// ##################################

	@Override
	public String getMark()
	{
		return null;
	}

	@Override
	public List<IItemStack> getItems()
	{
		return Collections.emptyList();
	}
	
	@Override
	public List<ILiquidStack> getLiquids()
	{
		return Collections.<ILiquidStack>singletonList(this);
	}

	@Override
	public IIngredient amount(int amount)
	{
		return withAmount(amount);
	}

	@Override
	public IIngredient or(IIngredient ingredient)
	{
		return new IngredientOr(this, ingredient);
	}

	@Override
	public IIngredient transform(IItemTransformer transformer)
	{
		throw new UnsupportedOperationException("Liquid stack can't have transformers");
	}

	@Override
	public IIngredient only(IItemCondition condition)
	{
		throw new UnsupportedOperationException("Liquid stack can't have conditions");
	}

	@Override
	public IIngredient marked(String mark)
	{
		throw new UnsupportedOperationException("Liquid stack can't be marked");
	}

	@Override
	public boolean matches(IItemStack item)
	{
		return false;
	}

	@Override
	public boolean matches(ILiquidStack liquid)
	{
		return getDefinition().equals(liquid.getDefinition())
				&& getAmount() <= liquid.getAmount();
	}

	@Override
	public boolean contains(IIngredient ingredient)
	{
		if (!ingredient.getItems().isEmpty())
			return false;
		
		for (ILiquidStack liquid : ingredient.getLiquids())
		{
			if (!matches(liquid))
				return false;
		}
		
		return false;
	}

	@Override
	public IItemStack applyTransform(IItemStack item, IPlayer byPlayer)
	{
		return item;
	}

	@Override
	public boolean hasTransformers()
	{
		return false;
	}
}
