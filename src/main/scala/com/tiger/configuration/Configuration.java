package com.tiger.configuration;

import com.tiger.configuration.model.ActiveProfile;
import com.tiger.configuration.model.Application;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

/**
 * @author 王澎
 */
@Getter
@Setter
public class Configuration {
    private static Configuration configuration = new Configuration();

    private Application application;

    private ActiveProfile activeProfile;

    public static final String DEFAULT_APPLICATION_CONFIG_FILE = "application.yml";

    public static final String KAFKA_BOOTSTRAP_SERVERS = configuration.getActiveProfile().getKafka().getBootstrapServers();

    public static final String AUTO_OFFSET_RESET_CONFIG = "earliest";

    public static final String KAFKA_AUDIT_GROUP_ID = configuration.getActiveProfile().getKafka().getAudit().getGroupId();

    public static final String KAFKA_AUDIT_TOPIC = configuration.getActiveProfile().getKafka().getAudit().getTopic();

    private Configuration() {
        // Default application configuration.
        Yaml yaml = new Yaml(new Constructor(Application.class));
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(DEFAULT_APPLICATION_CONFIG_FILE);
        this.application = yaml.load(inputStream);
        // Active profile
        yaml = new Yaml(new Constructor(ActiveProfile.class));
        inputStream = this.getClass().getClassLoader().getResourceAsStream(DEFAULT_APPLICATION_CONFIG_FILE.replace(".",
                "-" + application.getProfiles().getActive() + "."));
        this.activeProfile = yaml.load(inputStream);
    }
}
