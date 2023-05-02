package com.example.configmanagement.controller;

import com.example.configmanagement.entity.ClientSystem;
import com.example.configmanagement.entity.Config;
import com.example.configmanagement.repository.ConfigRepository;
import com.example.configmanagement.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class SystemController {
    @Autowired
    SystemRepository systemRepository;

    @Autowired
    ConfigRepository configRepository;

    /*
    Adds a new System
     */
    @PostMapping("/system")
    public ClientSystem addNewSystem(@RequestBody ClientSystem clientSystem) {
        return systemRepository.save(clientSystem);
    }

    /*
    Displays a System
     */
    @GetMapping("/{systemId}")
    public Optional<ClientSystem> viewSystem(@PathVariable String systemId) {
        return systemRepository.findById(systemId);
    }

    /*
    Displays a system with all its config
     */
    @GetMapping("/{systemId}/configs")
    public List<Config> viewSystemWithAllConfig(@PathVariable String systemId) {
        List<Config> configs = configRepository.findBySystemId(systemId);
        return configs;
    }

    /*
    Deletes a System and all configs under it
     */
    @DeleteMapping("/system/{systemId}")
    public void deleteSystem(@PathVariable String systemId) {
        List<Config> configs = configRepository.findBySystemId(systemId);
        for (Config c: configs) {
            configRepository.delete(c);
        }
        Optional<ClientSystem> optionalClientSystem = systemRepository.findById(systemId);
        optionalClientSystem.ifPresent(clientSystem -> systemRepository.delete(clientSystem));
    }
}
