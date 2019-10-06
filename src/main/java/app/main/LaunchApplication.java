package app.main;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.controller.DictionnaryController;
import app.main.properties.ApplicationProperties;
import app.service.WordService;

/** 
 * Simple application using Spring-Data-JPA and embedded hsqldb.
 **/
public class LaunchApplication {

	private final static Logger LOGGER = Logger.getLogger(LaunchApplication.class);
	
	public static void main(String[] args) {

		//Create Spring application context
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
		
		//Get service from context.
		WordService wordService = ctx.getBean(WordService.class);
		
		Application app = new Application(wordService);
		app.start();
	
		ctx.close();
	}
}
