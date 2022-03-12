package com.gamemanager.controllers;

import com.gamemanager.beens.Answer;
import com.gamemanager.services.GameManagerService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;

@RestController
@RequestMapping(value = "/games")
public class GameControllers {

	Logger logger = LoggerFactory.getLogger(GameControllers.class);

	@Autowired
	GameManagerService gameManagerService;

	@GetMapping("/AnswerQuestion/{userName}/{gameId}/{answerId}/{questionId}")
	public ResponseEntity<Object>  AnswerQuestion(@PathVariable String userName , @PathVariable Long gameId,
												@PathVariable Integer answerId, @PathVariable Integer questionId) {
		logger.info("Start userName:{}, gameId:{}, answerId:{}, questionId:{} ", userName, gameId, answerId, questionId);

		Hashtable<String, Object> ret = gameManagerService.AnswerQuestion(new Answer(gameId, questionId, answerId, "" ), userName);

		HttpStatus httpStatus = HttpStatus.OK;
		if ( gameManagerService.isError(ret) ) {
			httpStatus = HttpStatus.NOT_MODIFIED;
		}

		return ResponseEntity //
				.status(httpStatus) //
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.ALPS_JSON_VALUE) //
				.body(ret);
    }

	@GetMapping("/GetLeaderboard/{gameId}")
	public ResponseEntity<Object>  getLeaderboard(@PathVariable Long gameId ) {
		logger.info("Start gameId:{}", gameId );

		Hashtable<String, Object> ret = gameManagerService.getLeaderboard(gameId);

		HttpStatus httpStatus = HttpStatus.OK;
		if ( gameManagerService.isError(ret) ) {
			httpStatus = HttpStatus.NOT_FOUND;
		}

		return ResponseEntity //
				.status(httpStatus) //
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.ALPS_JSON_VALUE) //
				.body(ret);
	}

}
