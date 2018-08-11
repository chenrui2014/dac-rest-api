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

import com.boe.dacrestapi.model.ChainBlock;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChainBlockRepositoryTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	@Test
	public void addChainBlock()throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "{\"blockNum\":12, \"blockHash\":\"adfsafdsfadf\", \"tranNum\":1, \"pacTime\":100, \"genTime\":\"2018-05-29 23:00:00\"}";
		ChainBlock chainBlock = mapper.readValue(jsonString, ChainBlock.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/cb")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(chainBlock)))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
//		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
//		.andExpect(MockMvcResultMatchers.jsonPath("$.blockNum").value(12));
	}	
	@Test
	public void getChainBlockList() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/cb"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
