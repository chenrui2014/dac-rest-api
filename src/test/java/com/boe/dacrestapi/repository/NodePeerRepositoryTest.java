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

import com.boe.dacrestapi.model.NodePeer;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NodePeerRepositoryTest {

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
		String jsonString = "{\"nodeName\":\"北京\", \"nodeIp\":\"192.168.1.1\", \"nodePosition\":\"华北\", \"status\":\"1\", \"blockHeight\":2222}";
		NodePeer chainBlock = mapper.readValue(jsonString, NodePeer.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/node")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(chainBlock)))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}	
	@Test
	public void getChainBlockList() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/node"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
