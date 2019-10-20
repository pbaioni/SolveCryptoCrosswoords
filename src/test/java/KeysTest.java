import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import app.model.Key;
import app.model.properties.GridProperties;

public class KeysTest {
	

	private GridProperties gridProperties;
	
	@Before
	public void init(){
		gridProperties = new GridProperties("grid.properties");
	}
	
	@Test
	public void testCompatibility() {

		Key key = new Key(gridProperties);
		
		// 1:o 3:t (first result, it is accepted)
		assertTrue(key.mergeResult("1 3 3 1", "otto"));
		
		// 1:o 3:t 12:m 8:e (compatible with common letters, accepted)
		assertTrue(key.mergeResult("1 12 8 3 3 1", "ometto"));
		
		// 1:o 3:s 4:s 8:e (incompatible with common letters, refused)
		assertFalse(key.mergeResult("1 12 8 3 3 1", "omesso"));
		
		// 15:o 16:t (letters already decrypted but different numbers, refused)
		assertFalse(key.mergeResult("15 16 16 15", "otto"));
		
		// 1:o 3:t 4:s 8:e (compatible with no common letters, accepted)
		assertTrue(key.mergeResult("20 21 22", "pia"));
	}

}
