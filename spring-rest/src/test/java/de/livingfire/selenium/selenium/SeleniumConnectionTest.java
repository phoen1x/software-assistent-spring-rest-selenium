package de.livingfire.selenium.selenium;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import de.livingfire.selenium.configuration.WebdriverConfiguration;
import de.livingfire.selenium.exception.LivingfireRuntimeException;

@RunWith(MockitoJUnitRunner.class)
public class SeleniumConnectionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private WebDriver webDriver;

    @Mock
    private WebdriverConfiguration webdriverConfiguration;

    private SeleniumConnection seleniumConnection;

    @Before
    public void setUp() {
        seleniumConnection = new SeleniumConnection(webdriverConfiguration);
    }

    @Test
    public void testIsSessionWithoutWebdriverShouldReturnFalse() throws Exception {
        boolean actual = seleniumConnection.isSession();
        assertThat(actual).isFalse();
    }

    @Test
    public void testIsSessionWithWebdriverShouldReturnTrue() throws Exception {
        seleniumConnection.setWebDriver(webDriver);
        boolean actual = seleniumConnection.isSession();
        assertThat(actual).isTrue();
    }

    @Test
    public void testGetSessionWithoutWebdriverShouldThrowExeption() throws Exception {
        when(webdriverConfiguration.getUrlSeleniumServer()).thenReturn("no server here!");

        try {
            seleniumConnection.getSession();
            fail("Exception expected");
        } catch (Throwable e) {
            this.thrown.expect(LivingfireRuntimeException.class);
            this.thrown.expectMessage("selenium unable to connect server: no server here!");
            throw e;
        }
    }

    @Test
    public void testGetSessionWithWebdriverShouldReturnValue() throws Exception {
        seleniumConnection.setWebDriver(webDriver);

        WebDriver actual = seleniumConnection.getSession();
        assertThat(actual).isNotNull();
    }

    @Test
    public void testSessionCloseWithoutWebdriverShouldNullWebdriver() throws Exception {
        seleniumConnection.sessionClose();
        assertThat(seleniumConnection.getWebDriver()).isNull();
    }

    @Test
    public void testSessionCloseWithWebdriverAndNoSessionShouldNullWebdriver() throws Exception {
        seleniumConnection.setWebDriver(webDriver);

        seleniumConnection.sessionClose();
        assertThat(seleniumConnection.getWebDriver()).isNull();
    }

    @Test
    public void testSessionCloseWithWebdriverFailingCloseShouldThrowException() throws Exception {
        doThrow(new LivingfireRuntimeException("foo")).when(webDriver)
                                                      .close();

        seleniumConnection.setWebDriver(webDriver);

        try {
            seleniumConnection.sessionClose();
            fail("Exception expected");
        } catch (Throwable e) {
            this.thrown.expect(LivingfireRuntimeException.class);
            this.thrown.expectMessage("failed to close selenium session");
            throw e;
        }
    }
}
