package minetweaker.api.block;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 * Blocks definitions provide additional information about blocks.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.block.IBlockDefinition")
public interface IBlockDefinition {
	@ZenGetter("id")
	public String getId();

	@ZenGetter("displayName")
	public String getDisplayName();

	@ZenSetter("displayName")
	public void setDisplayName(String name);

}
