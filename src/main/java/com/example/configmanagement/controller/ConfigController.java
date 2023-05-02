package com.example.configmanagement.controller;

import com.example.configmanagement.entity.Config;
import com.example.configmanagement.repository.ConfigRepository;
import com.example.configmanagement.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping
public class ConfigController {

    @Autowired
    ConfigRepository configRepository;

    SystemRepository systemRepository;

    public ConfigController(ConfigRepository configRepository, SystemRepository systemRepository) {
        this.configRepository = configRepository;
        this.systemRepository = systemRepository;
    }

    /*
        Adds a new config
     */
    @PostMapping("/config")
    public Config addConfig(@RequestBody Config config) {
        return configRepository.save(config);
    }

    @GetMapping("/configs")
    public List<Config> viewAllConfigs() {
        return configRepository.findAll();
    }

    /*
    Displays a config
     */
    @GetMapping("/config/{configId}")
    public Optional<Config> viewConfig(@PathVariable String configId) {
        return configRepository.findById(configId);
    }

    /*
    Deletes a config
     */
    @DeleteMapping("/{configId}")
    public void deleteConfig(@PathVariable String configId) {
        Optional<Config> optionalConfig = configRepository.findById(configId);
        optionalConfig.ifPresent(config -> configRepository.delete(config));
    }

    /*
    Updates a config
     */
    @PatchMapping("/config/{configId}")
    public void updateConfig(@PathVariable String configId, @RequestBody Map<String, Object> updates) {
        Optional<Config> optionalConfig = configRepository.findById(configId);
        if(optionalConfig.isPresent()) {
            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                Field field = ReflectionUtils.findField(Config.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, optionalConfig.get(), value);
            }
            configRepository.save(optionalConfig.get());
        }
    }
}
