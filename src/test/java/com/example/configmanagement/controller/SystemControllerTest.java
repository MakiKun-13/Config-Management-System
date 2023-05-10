/**
 * End to End Testing Class for SystemController
 */
package com.example.configmanagement.controller;

import com.example.configmanagement.entity.ClientSystem;
import com.example.configmanagement.entity.Config;
import com.example.configmanagement.repository.ConfigRepository;
import com.example.configmanagement.repository.SystemRepository;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class SystemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SystemRepository systemRepository;

    @Mock
    ConfigRepository configRepository;

    @InjectMocks
    SystemController systemController;

    ClientSystem clientSystem = new ClientSystem();

    Config CONFIG_1 = new Config("0001", "key_1", "value_1", "env_1", "SYS001");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(systemController).build();
    }

    public String asJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }


    @Test
    public void testAddNewSystem() throws Exception {
        when(systemRepository.save(clientSystem)).thenReturn(clientSystem);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/system")
                .content(asJsonString(clientSystem))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testViewSystem() throws Exception {
        clientSystem.setSystemId("SYS001");
        when(systemRepository.findById("SYS001")).thenReturn(Optional.ofNullable(clientSystem));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/{systemId}","SYS001"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.systemId").value("SYS001"));
    }


    @Test
    public void testViewSystemWithAllConfig() throws Exception {
        List<Config> configs = new ArrayList<>();
        configs.add(CONFIG_1);
        when(configRepository.findBySystemId("SYS001")).thenReturn(configs);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/{systemId}/configs","SYS001")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSystem() throws Exception {
        List<Config> configs = new ArrayList<>();
        configs.add(CONFIG_1);
        when(configRepository.findBySystemId("SYS001")).thenReturn(configs);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/system/{systemId}","SYS001")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
