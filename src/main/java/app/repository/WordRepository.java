package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Word;

/** 
 * This is the Data Access layer.
 */
@Repository
public interface WordRepository extends JpaRepository<Word, String> {
	

	/** spring-jpa-data understands this method name,
	 *  because it supports the resolution of specific keywords inside method names. **/
    public List<Word> findByRelativeCrypto(String searchRelativeCrypto);


	public List<Word> findByLength(String length);
    
}
