package prisonescape.test;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;

import prisonescape.game.model.TiledModel;

public class TestTiledModel {

	private TiledModel model;
	private TiledMap tilemap;

	@Before
	public void setUp() {	
		//tilemap = new TmxMapLoader().load("data/maps/cell.tmx");
		//model = new TiledModel(tilemap);
	}
	
	@Test
	public void testModel_NotNull() {
		//assertNotNull(model);
	}
}
