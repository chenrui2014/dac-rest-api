package com.boe.dacrestapi.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.boe.dacrestapi.model.Painting;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaintingRepositoryTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void addNodePeer()throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "{\"depCerticateId\":\"qw124\", \"paintHash\":\"123asdasd3fsd322\", \"paintName\":\"星星之火\", \"author\":\"老高\", \"regTime\":\"2018-05-30 11:00:00\",\"status\":\"1\"}";
		Painting chainBlock = mapper.readValue(jsonString, Painting.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/painting")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(chainBlock)))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}	
	@Test
	public void getChainBlockList() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/painting"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
