package de.livingfire.selenium.selenium;

import java.net.URL;

import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.livingfire.selenium.configuration.WebdriverConfiguration;
import de.livingfire.selenium.exception.LivingfireRuntimeException;
import de.livingfire.selenium.interfaces.RequestConstant;
import lombok.Getter;
import lombok.Setter;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Getter
@Setter
public class SeleniumConnection implements RequestConstant {

    private WebDriver webDriver;
    private WebdriverConfiguration webdriverConfiguration;

    public SeleniumConnection(WebdriverConfiguration webdriverConfiguration) {
        super();
        this.webdriverConfiguration = webdriverConfiguration;
    }

    public boolean isSession() {
        return webDriver != null;
    }

    public WebDriver getSession() {
        if (!isSession()) {
            try {
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                webDriver = new RemoteWebDriver(new URL(webdriverConfiguration.getUrlSeleniumServer()), capabilities);
                webDriver.get(webdriverConfiguration.getUrlDefaultWebpageToOpen());
            } catch (Throwable e) {
                webDriver = null;
                throw new LivingfireRuntimeException(
                        "selenium unable to connect server: " + webdriverConfiguration.getUrlSeleniumServer());
            }
        }
        return webDriver;
    }

    public void sessionClose() {
        try {
            if (isSession()) {
                webDriver.close();
            }
        } catch (NoSuchSessionException e) {
            // do noting loop
        } catch (Throwable e) {
            throw new LivingfireRuntimeException("failed to close selenium session", e);
        } finally {
            webDriver = null;
        }
    }

}
