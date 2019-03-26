
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import prisonescape.game.model.TiledModel;

@RunWith(GdxTestRunner.class)
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
		Gdx.files.internal("dgdfgdfg");
	}
}
