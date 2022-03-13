package com.gamemanager;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
class GameManagerApplicationTests {


	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp(WebApplicationContext wac) throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				.alwaysExpect(status().isOk())
				.alwaysDo(print())
				.build();
	}

	List<String> initAnswerQuestionTestList(int testCount){
		Vector<String> ret = new Vector();
		String restMap = "/games/AnswerQuestion/%s/%d/%d/%d";
		Random rand = new Random();
		for(int i = 0; i < testCount; i++ ){
			ret.add(String.format(restMap, "aaa" + rand.nextInt(4) ,
													rand.nextInt(4) ,
													rand.nextInt(4),
													rand.nextInt(4) ,
													rand.nextInt(10) ));
		}
		return ret;
	}

	@Test
	@DisplayName("Init server Test")
	@Order(1)
	void InitServer() throws Exception {

		try {
			this.mockMvc.perform(get("/games/AnswerQuestion/aaa/0/1/1"))
					.andExpect(status().isOk())
					.andExpect(content().string(
							anyOf(containsString("GameEarnedPoints"),
									containsString("gameId"),
									containsString("userName")
							)))
					.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	@DisplayName("AnswerQuestion Test")
	void AnswerQuestion() throws Exception {

		List<String>  answerQuestionTestList = initAnswerQuestionTestList(20);

		List<String> collect = answerQuestionTestList.parallelStream().map(a -> {
			try {
				this.mockMvc.perform(get(a))
						.andExpect(status().isOk())
						.andExpect(content().string(
								anyOf(containsString("GameEarnedPoints"),
										containsString("gameId"),
										containsString("userName")
													)))
						.andDo(print());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}).collect(Collectors.toList());
	}

	List<String> initGetLeaderboardTestList(int testCount){
		Vector<String> ret = new Vector();
		String restMap = "/games/GetLeaderboard/%d";
		Random rand = new Random();
		for(int i = 0; i < testCount; i++ ){
			ret.add(String.format(restMap, rand.nextInt(10)));
		}
		return ret;
	}


	@Test
	@DisplayName("GetLeaderboard Test")
	void GetLeaderboard() throws Exception {

		List<String>  answerQuestionTestList = initGetLeaderboardTestList(2);

		List<String> collect = answerQuestionTestList.parallelStream().map(a -> {
			try {
				this.mockMvc.perform(get(a))
						.andExpect(status().isOk())
						.andExpect(content().string(containsString("GameId")))
						.andDo(print());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}).collect(Collectors.toList());

	}

}