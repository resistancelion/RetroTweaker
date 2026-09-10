package minetweaker;

import java.util.List;

/**
 * The class registry provides the list of annotated classes to the scripting
 * system.
 * 
 * @author Stan Hebben
 */
public class ClassRegistry {
	public static void getClasses(List<Class> output) {
		// ag -l '^@Zen(Class|Expansion)' |sed 's@^src/main/java/@output.add(@' |tr '/' '.' |sed 's/.java$/.class);/' |wl-copy
		
		output.add(minetweaker.runtime.ITweaker.class);
		output.add(minetweaker.api.formatting.IFormattedText.class);
		output.add(minetweaker.api.vanilla.ISeedRegistry.class);
		output.add(minetweaker.api.vanilla.IVanilla.class);
		output.add(minetweaker.api.vanilla.LootEntry.class);
		output.add(minetweaker.api.chat.IChatMessage.class);
		output.add(minetweaker.api.vanilla.ILootRegistry.class);
		output.add(minetweaker.api.item.WeightedItemStack.class);
		output.add(minetweaker.api.item.IItemStack.class);
		output.add(minetweaker.api.item.IItemDefinition.class);
		output.add(minetweaker.api.item.IItemTransformer.class);
		output.add(minetweaker.api.item.IIngredient.class);
		output.add(minetweaker.api.item.IngredientTransform.class);
		output.add(minetweaker.api.item.IngredientCondition.class);
		output.add(minetweaker.api.item.IItemCondition.class);
		output.add(minetweaker.api.event.PlayerAttackEntityEvent.class);
		output.add(minetweaker.api.event.PlayerInteractEvent.class);
		output.add(minetweaker.api.event.PlayerFillBucketEvent.class);
		output.add(minetweaker.api.event.PlayerPickupXpEvent.class);
		output.add(minetweaker.api.event.PlayerBonemealEvent.class);
		output.add(minetweaker.api.event.PlayerSleepInBedEvent.class);
		output.add(minetweaker.api.event.PlayerLoggedInEvent.class);
		output.add(minetweaker.api.event.PlayerInteractEntityEvent.class);
		output.add(minetweaker.api.event.PlayerCraftedEvent.class);
		output.add(minetweaker.api.event.PlayerDeathDropsEvent.class);
		output.add(minetweaker.api.event.IEventHandle.class);
		output.add(minetweaker.api.event.PlayerUseItemTickEvent.class);
		output.add(minetweaker.api.event.PlayerOpenContainerEvent.class);
		output.add(minetweaker.api.event.PlayerLoggedOutEvent.class);
		output.add(minetweaker.api.event.PlayerChangedDimensionEvent.class);
		output.add(minetweaker.api.event.PlayerPickupEvent.class);
		output.add(minetweaker.api.event.PlayerUseHoeEvent.class);
		output.add(minetweaker.api.event.PlayerUseItemStartEvent.class);
		output.add(minetweaker.api.event.PlayerPickupItemEvent.class);
		output.add(minetweaker.api.event.PlayerRespawnEvent.class);
		output.add(minetweaker.api.liquid.WeightedLiquidStack.class);
		output.add(minetweaker.api.oredict.IOreDict.class);
		output.add(minetweaker.api.oredict.IOreDictEntry.class);
		output.add(minetweaker.api.liquid.ILiquidStack.class);
		output.add(minetweaker.api.liquid.ILiquidDefinition.class);
		output.add(minetweaker.api.container.IContainer.class);
		output.add(minetweaker.api.server.ICommandValidator.class);
		output.add(minetweaker.api.tooltip.IngredientTooltips.class);
		output.add(minetweaker.api.server.ICommandTabCompletion.class);
		output.add(minetweaker.api.server.ICommandFunction.class);
		output.add(minetweaker.api.server.CommandValidators.class);
		output.add(minetweaker.api.server.IServer.class);
		output.add(minetweaker.api.game.IGame.class);
		output.add(minetweaker.api.world.IDimension.class);
		output.add(minetweaker.api.world.IBlockGroup.class);
		output.add(minetweaker.api.data.IData.class);
		output.add(minetweaker.api.block.IBlockDefinition.class);
		output.add(minetweaker.api.block.IBlock.class);
		output.add(minetweaker.api.block.IBlockPattern.class);
		output.add(minetweaker.api.recipes.IFurnaceManager.class);
		output.add(minetweaker.api.recipes.ICraftingRecipe.class);
		output.add(minetweaker.api.recipes.IRecipeManager.class);
		output.add(minetweaker.api.util.Position3f.class);
		output.add(minetweaker.api.recipes.ICraftingInventory.class);
		output.add(minetweaker.api.recipes.ICraftingInfo.class);
		output.add(minetweaker.api.recipes.IRecipeFunction.class);
		output.add(minetweaker.api.mods.ILoadedMods.class);
		output.add(minetweaker.api.player.IPlayer.class);
		output.add(minetweaker.api.client.IClient.class);
		output.add(minetweaker.api.entity.IEntity.class);
		output.add(minetweaker.api.entity.IEntityDefinition.class);
		output.add(minetweaker.api.entity.IEntityXp.class);
		output.add(minetweaker.api.resource.IResourceFile.class);
		output.add(minetweaker.expand.ExpandFloat.class);
		output.add(minetweaker.expand.ExpandByte.class);
		output.add(minetweaker.expand.ExpandBool.class);
		output.add(minetweaker.expand.ExpandShort.class);
		output.add(minetweaker.expand.ExpandItemStack.class);
		output.add(minetweaker.expand.ExpandAnyDict.class);
		output.add(minetweaker.expand.ExpandDouble.class);
		output.add(minetweaker.expand.ExpandLong.class);
		output.add(minetweaker.expand.ExpandAnyArray.class);
		output.add(minetweaker.expand.ExpandInt.class);
		output.add(minetweaker.expand.ExpandString.class);


	}
}
