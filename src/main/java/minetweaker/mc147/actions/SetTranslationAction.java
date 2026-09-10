/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc147.actions;

import java.nio.charset.Charset;
import java.util.Properties;
import java.lang.reflect.Field;
import minetweaker.MineTweakerAPI;
import cpw.mods.fml.common.registry.LanguageRegistry;
import minetweaker.IUndoableAction;
import minetweaker.mc147.util.MineTweakerHacks;
import net.minecraft.util.StringTranslate;

/**
 *
 * @author Stan
 */
public class SetTranslationAction implements IUndoableAction {
	private static final StringTranslate INSTANCE = MineTweakerHacks.getStringTranslateInstance();
	private static final Charset UTF8 = Charset.forName("utf-8");
	
	private final String key;
	private final String newValue;
	private final String oldValue;
	
	public SetTranslationAction(String key, String value) {
		this.key = key;
		newValue = value;
		oldValue = INSTANCE.translateKey(key);
	}

	@Override
	public void apply() {
		set(key, newValue);
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void undo() {
		set(key, oldValue);
	}

	@Override
	public String describe() {
		return "Translating " + key + " to " + newValue;
	}

	@Override
	public String describeUndo() {
		return "Reverting " + key + " to " + oldValue;
	}
	
	private static void set(String key, String value) {
		// 1. Register with Forge
		LanguageRegistry.instance().addStringLocalization(key, "en_US", value);
		LanguageRegistry.instance().addStringLocalization(key, INSTANCE.getCurrentLanguage(), value);

		// 2. Bruteforce injection into live memory
		try {
			for (Field field : StringTranslate.class.getDeclaredFields()) {

				if (field.getType() == Properties.class) {
					field.setAccessible(true);
					Properties props = (Properties) field.get(INSTANCE);
					props.setProperty(key, value);
					MineTweakerAPI.logInfo("[INFO] Injected into memcache .lang for: '" + key + "'");
					break;
				}
			}
		} catch (Exception e) {
			MineTweakerAPI.logInfo("[ERROR] Injection into memcache .lang for: '" + key + "' FAILED");
		}
	}

	@Override
	public Object getOverrideKey() {
		return null;
	}
}
