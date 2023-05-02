/**
 * End to End Testing Class
 */
package com.example.configmanagement.controller;

import com.example.configmanagement.entity.Config;
import com.example.configmanagement.repository.ConfigRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class ConfigControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    ConfigController configController;

    @Mock
    private ConfigRepository configRepository;

     Config CONFIG_1 = new Config("0001", "key_1", "value_1", "env_1", "systemId_1");

    Optional<Config> CONFIG_2 = Optional.of(new Config("0002", "key_2", "value_2", "env_2", "systemId_2"));

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(configController).build();
    }

    @Test
    public void testViewConfig() throws Exception {
        when(configRepository.findById("0001")).thenReturn(Optional.ofNullable(CONFIG_1));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/config/{configId}","0001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.configId").value("0001"));
    }

    @Test
    public void testViewAllConfigs() throws Exception {
        List<Config> configList = new ArrayList<>(Arrays.asList(CONFIG_1));
        when(configRepository.findAll()).thenReturn(configList);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/configs")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddConfig() throws Exception {
        when(configRepository.save(CONFIG_1)).thenReturn(CONFIG_1);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/config")
                .content(asJsonString(CONFIG_1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()));
    }

    public String asJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    @Test
    public void testDeleteConfig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/configId", "0001"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateConfig() throws Exception {
        when(configRepository.findById("0001")).thenReturn(CONFIG_2);
        //Assertions.assertEquals(CONFIG_2.isPresent());
        when(CONFIG_2.isPresent()).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/config/{configId}", "0001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}