package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Word;

/** 
 * This is the Data Access layer. Simple huh?
 * PLease note that no need for any dao implementation. This is an interface!
 */
@Repository
public interface WordRepository extends JpaRepository<Word, String> {
	
	/** No need to define findAll() here, because 
	 * 	inherited from JpaRepository with many other basic JPA operations.**/  
	//public List<Product> findAll();


	/** spring-jpa-data understands this method name,
	 *  because it supports the resolution of specific keywords inside method names. **/
    public List<Word> findByRelativeCrypto(String searchRelativeCrypto);


	public List<Word> findByLength(String length);
    
}
