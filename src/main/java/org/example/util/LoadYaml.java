package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.example.config.AppConfig;

import java.io.File;
import java.io.IOException;

public class LoadYaml{

    public static AppConfig loadConfig(String fileName) throws IOException{
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(fileName), AppConfig.class);
    }
}


