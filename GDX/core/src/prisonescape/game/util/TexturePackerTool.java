package prisonescape.game.util;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public class TexturePackerTool {

	public static void main(String[] args) {
		// Pack textures into single .png and provide corresponding .atlas 
		TexturePacker.process("assets/data/unpacked" , "assets/data/packed", "textures");
	}
	
}
