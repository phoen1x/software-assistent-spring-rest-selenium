package de.livingfire.selenium.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "webdriver")
@Data
@NoArgsConstructor
@Validated
public class WebdriverConfigurationSpring implements WebdriverConfiguration {
    private String urlSeleniumServer;
    private String urlDefaultWebpageToOpen;
}
