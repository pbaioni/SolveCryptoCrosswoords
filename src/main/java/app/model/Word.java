package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import app.model.helper.WordHelper;

/** 
 * 	This is a data structure, so
 *  fields can be public. (Clean-Code)
 */
@Entity
@Table(name="italianDictionnary")
public class Word {
	
    @Id
    @Column
    public String word;
    @Column
    private int length;
    @Column
    private String relativeCrypto;
	
	public Word() {
		//Default constructor needed for JPA.
	}
	public Word(String word) {

		this.word = word;
		this.length = word.length();
		this.relativeCrypto = WordHelper.computeRelativeCrypto(word);
		
	}

	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getRelativeCrypto() {
		return relativeCrypto;
	}
	public void setRelativeCrypto(String relativeCrypto) {
		this.relativeCrypto = relativeCrypto;
	}
	
	@Override
	public String toString() {
		return "Word [word=" + word + ", length=" + length + ", relativeCrypto=" + relativeCrypto + "]";
	}	
}
