package minetweaker.api.liquid;

import java.util.List;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;
import stanhebben.zenscript.annotations.ZenOperator;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Contains a liquid definition. Liquid definitions provide additional
 * information about liquids.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.liquid.ILiquidDefinition")
public interface ILiquidDefinition {
	/**
	 * Converts this liquid into a liquid stack.
	 * 
	 * @param millibuckets item stack size
	 * @return resulting item stack
	 */
	@ZenOperator(OperatorType.MUL) ILiquidStack asStack(int millibuckets);

	/**
	 * Gets the unlocalized name of this item.
	 * 
	 * @return unlocalized name
	 */
	@ZenGetter("name") String getName();

	@ZenGetter("displayName") String getDisplayName();

	@ZenSetter("displayName") void setDisplayName(String name);

	@ZenGetter("containers") List<IItemStack> getContainers();

	@ZenMethod void addContainer(IItemStack filled, IItemStack empty, int amount);

	@ZenMethod void removeContainer(IItemStack filled);
}
