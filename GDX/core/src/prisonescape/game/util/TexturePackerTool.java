package prisonescape.game.util;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * This class is used to pack all textures from the assets/data/unpacked folder
 * into the assets/data/packed folder, outputting both an .atlas and .png file.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class TexturePackerTool {

	public static void main(String[] args) {
		// Pack textures into single .png and provide corresponding .atlas 
		TexturePacker.process("assets/data/unpacked" , "assets/data/packed", "textures");
	}
	
}
