import static org.junit.Assert.assertTrue;

import org.junit.Test;

import app.model.helper.WordHelper;

public class RelativeCryptoTest {
	
	@Test
	public void testRCAlgorithm() {

		assertTrue(compareRC("abbattetelo", "ABBACCDCDEF"));
		assertTrue(compareRC("zitto", "ABCCD"));
		assertTrue(compareRC("ignoranti", "ABCDEFCGA"));
		
		
	}
	
	private boolean compareRC(String word, String expectedRC) {
		String computedRC = WordHelper.computeRelativeCrypto(word);
		System.out.println("expectedRC: " + expectedRC + ", computedRC: " + computedRC);
		return expectedRC.equals(computedRC);
	}
	
	@Test
	public void testNRCAlgorithm() {

		assertTrue(compareNRC("3 7 7 3 12 14 3", "ABBACDA"));
		
		assertTrue(compareNRC("1 2 3 4 5 6 6 12", "ABCDEFFG"));
		
	}

	private boolean compareNRC(String numericalCrypto, String expectedRC) {
		String computedRC = WordHelper.numericalToAlphabetic(numericalCrypto);
		System.out.println("expectedRC: " + expectedRC + ", computedRC: " + computedRC);
		return (expectedRC.contentEquals(computedRC));
	}

}
