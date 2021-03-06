package com.rossotti.basketball.client.service;

import com.rossotti.basketball.util.service.exception.PropertyException;
import com.rossotti.basketball.util.service.PropertyService;
import com.rossotti.basketball.client.dto.GameDTO;
import com.rossotti.basketball.client.dto.RosterDTO;
import com.rossotti.basketball.client.dto.StandingsDTO;
import com.rossotti.basketball.client.dto.StatusCodeDTO.StatusCode;
import com.rossotti.basketball.util.function.DateTimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class FileStatsService {
	private final PropertyService propertyService;

	private final FileClientService fileClientService;

	private final Logger logger = LoggerFactory.getLogger(FileStatsService.class);

	@Autowired
	public FileStatsService(PropertyService propertyService, FileClientService fileClientService) {
		this.propertyService = propertyService;
		this.fileClientService = fileClientService;
	}

	public GameDTO retrieveBoxScore(String event) {
		GameDTO gameDTO = new GameDTO();
		try {
			String path = propertyService.getProperty_Path("xmlstats.fileBoxScore");
			gameDTO = (GameDTO)fileClientService.retrieveStats(path, event, gameDTO);
		}
		catch (PropertyException pe) {
			logger.info("property exception = " + pe);
			gameDTO.setStatusCode(StatusCode.ServerException);
		}
		return gameDTO;
	}

	public RosterDTO retrieveRoster(String event, LocalDate asOfDate) {
		RosterDTO rosterDTO = new RosterDTO();
		try {
			String path = propertyService.getProperty_Path("xmlstats.fileRoster");
			String dateEvent = event + "-" + DateTimeConverter.getStringDateNaked(asOfDate);
			rosterDTO = (RosterDTO)fileClientService.retrieveStats(path, dateEvent, rosterDTO);
		}
		catch (PropertyException pe) {
			logger.info("property exception = " + pe);
			rosterDTO.setStatusCode(StatusCode.ServerException);
		}
		return rosterDTO;
	}

	public StandingsDTO retrieveStandings(String event) {
		StandingsDTO standingsDTO = new StandingsDTO();
		try {
			String path = propertyService.getProperty_Path("xmlstats.fileStandings");
			standingsDTO = (StandingsDTO)fileClientService.retrieveStats(path, event, standingsDTO);
		}
		catch (PropertyException pe) {
			logger.info("property exception = " + pe);
			standingsDTO.setStatusCode(StatusCode.ServerException);
		}
		return standingsDTO;
	}
}