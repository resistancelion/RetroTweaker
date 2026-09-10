/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc147;

import java.util.List;

/**
 *
 * @author Stan
 */
public class MineTweakerRegistry {
	public static void getClasses(List<Class> output) {
		// ag -l '^@BracketHandler' |sed 's@^src/main/java/@output.add(@' |tr '/' '.' |sed 's/.java$/.class);/' |wl-copy
		
		output.add(minetweaker.mc147.brackets.ItemBracketHandler.class);
		output.add(minetweaker.mc147.brackets.OreBracketHandler.class);
		output.add(minetweaker.mc147.brackets.LiquidBracketHandler.class);

	}
}
