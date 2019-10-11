package app.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.model.Grid;
import app.model.helper.GridHelper;
import app.model.properties.GridProperties;
import app.repository.WordRepository;

/** 
 * Service layer.
 * Specify transactional behavior and mainly
 * delegate calls to Repository.
 */
@Component
public class GridService {
	
	private final static Logger LOGGER = Logger.getLogger(GridService.class);

	@Autowired
	private WordRepository wordRepository;
	
	private GridProperties gridProperties;
	
	public GridService() {
		
		gridProperties = new GridProperties("grid.properties");
		
	}

	public void loadGrid(String filename) {
		GridHelper.solveGrid(new Grid(filename, gridProperties), wordRepository, gridProperties);
	}
	

}
