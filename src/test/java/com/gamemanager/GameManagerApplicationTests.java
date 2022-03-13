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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameManagerApplicationTests {


	@Autowired
	private MockMvc mockMvc;


//	@BeforeEach
//	void setUp(WebApplicationContext wac) throws Exception {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
//				.alwaysExpect(status().isOk())
//				.alwaysDo(print())
//				.build();
//	}

	List<String> initAnswerQuestionTestList(int testCount){
		Vector<String> ret = new Vector(testCount + 2);
		String restMap = "/games/AnswerQuestion/%s/%d/%d/%d";
		Random rand = new Random();
		for(int i = 0; i < testCount; i++ ){
			ret.add(String.format(restMap, "aaa" + rand.nextInt(4) ,
													rand.nextInt(4) ,
													rand.nextInt(4),
													rand.nextInt(4) ,
													rand.nextInt(10) ));
		}
		for(int i = 0; i < 2; i++ ){
			ret.add(String.format(restMap, "aaa" + rand.nextInt(2) ,
					rand.nextInt(4) ,
					rand.nextInt(4),
					0 ,
					rand.nextInt(10) ));
		}
		return ret;
	}

	@Test
	@DisplayName("Init server Test")
	@Order(1)
	void initServer() {

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
	@Order(2)
	void answerQuestion() {

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
		Vector<String> ret = new Vector<>(testCount);
		String restMap = "/games/GetLeaderboard/%d";
		Random rand = new Random();
		for(int i = 0; i < testCount; i++ ){
			ret.add(String.format(restMap, rand.nextInt(2)));
		}
		return ret;
	}


	@Test
	@DisplayName("GetLeaderboard Test")
	@Order(3)
	void getLeaderboard(){

		List<String>  getLeaderboardTestList = initGetLeaderboardTestList(2);

		List<String> collect = getLeaderboardTestList.parallelStream().map(a -> {
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

	@Test
	@DisplayName("Check AnswerQuestion Test")
	@Order(5)
	void checkAnswerQuestion() {

		String[]  answerQuestionTestList = {
								"/games/AnswerQuestion/bbb/1/0/1",
								"/games/AnswerQuestion/bbb/1/0/2",
								"/games/AnswerQuestion/bbb/1/0/3",
								"/games/AnswerQuestion/ccc/2/0/1",
								"/games/AnswerQuestion/ccc/2/0/2",
								"/games/AnswerQuestion/ccc/2/0/3"
								};
		List<String> collect = List.of(answerQuestionTestList).parallelStream().map(a -> {
			try {
				this.mockMvc.perform(get(a))
						.andExpect(status().isOk())
						.andExpect(content().string(containsString("GameEarnedPoints")))
						.andDo(print());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}).collect(Collectors.toList());

	}

	@Test
	@DisplayName("Check GetLeaderboard Test")
	@Order(6)
	void checkGetLeaderboard(){

		String[]  getLeaderboardTestList = {
				"/games/GetLeaderboard/1",
				"/games/GetLeaderboard/2"
		};

		List<String> collect2 = List.of(getLeaderboardTestList).parallelStream().map(a -> {
			try {
				this.mockMvc.perform(get(a))
						.andExpect(status().isOk())
						.andExpect(content().string(
								anyOf(containsString("User Name\":\"ccc\""),
										containsString("User Name\":\"bbb\""),
										containsString("userName")
								)))
						.andDo(print());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}).collect(Collectors.toList());
	}


}