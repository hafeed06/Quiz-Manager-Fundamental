package fr.epita.service.data.configurations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Config configInstance;

    public static Config getInstance() {
        if(configInstance == null) {
            try {
                configInstance = new Config();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    return configInstance;
    }

    private final Properties props;

    private Config() throws IOException {
        this.props = new Properties();
        props.load(new FileInputStream(new File("config.ini")));
    }

    public String getConf(String entry) {
        return (String) this.props.get(entry);
    }

}
