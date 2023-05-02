package com.example.configmanagement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Config<T> {
    @Id
    private String configId;
    private String key;
    private T value; //Will store config inside of config
    private String environment;
    private String systemId;

    public Config() {
    }

    public Config(String configId, String key, T value, String environment, String systemId) {
        this.configId = configId;
        this.key = key;
        this.value = value;
        this.environment = environment;
        this.systemId = systemId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
