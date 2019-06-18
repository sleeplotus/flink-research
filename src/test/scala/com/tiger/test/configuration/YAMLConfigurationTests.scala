package com.tiger.test.configuration

import java.io.InputStream

import com.tiger.configuration.Configuration
import com.tiger.configuration.model.{ActiveProfile, Application}
import org.junit.Test
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

/**
  * @author 王澎
  */
class YAMLConfigurationTests {

  @Test
  def yamlConfiguration(): Unit = {
    // Flink
    // Default application configuration.
    var yaml: Yaml = new Yaml(new Constructor(classOf[Application]))
    var inputStream: InputStream = getClass.getClassLoader.getResourceAsStream(
      Configuration.DEFAULT_APPLICATION_CONFIG_FILE)
    val application: Application = yaml.load(inputStream)

    val activeConfig: String = application.getProfiles.getActive
    println("config.profiles.active: " + activeConfig)

    // Active profile.
    yaml = new Yaml(new Constructor(classOf[ActiveProfile]))
    inputStream = getClass.getClassLoader.getResourceAsStream(
      Configuration.DEFAULT_APPLICATION_CONFIG_FILE.replace(".", "-" + activeConfig + "."))
    val activeProfile: ActiveProfile = yaml.load(inputStream)
  }

  @Test
  def configuration(): Unit = {
    println("kafka.bootstrapServers: " + Configuration.KAFKA_BOOTSTRAP_SERVERS)
    println("kafka.audit.groupId: " + Configuration.KAFKA_AUDIT_GROUP_ID)
    println("kafka.audit.topic: " + Configuration.KAFKA_AUDIT_TOPIC)
  }

}
