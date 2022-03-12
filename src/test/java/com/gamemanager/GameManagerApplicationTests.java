package com.gamemanager;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class GameManagerApplicationTests {


	@Autowired
	private MockMvc mockMvc;


	@Test
	@DisplayName("AnswerQuestion Test")
	void AnswerQuestion1() throws Exception {
		for (int i = 0; i < 10 ; i++){
			this.mockMvc.perform(
							get("http://localhost:8050/games/AnswerQuestio")
									.queryParam("userName", "1")
									.queryParam("gameId", "1")
									.queryParam("answerId", "1")
									.queryParam("questionId", i + "")
					)
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("gameId")))
					.andDo(print());
		}
	}

	@Test
	@DisplayName("GetLeaderboard Test")
	void GetLeaderboard() throws Exception {
		this.mockMvc.perform(
						get("/games/GetLeaderboard")
								.queryParam("gameId", "1")
				)
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("gameId")))
				.andDo(print());
	}

}