package de.livingfire.selenium.service;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import de.livingfire.selenium.interfaces.HtmlConstant;
import de.livingfire.selenium.selenium.SeleniumConnection;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SeleniumService implements HtmlConstant {

    private SeleniumConnection seleniumConnection;

    public void navigateTo(String url) {
        seleniumConnection.getSession()
                          .navigate()
                          .to(url);
    }

    public void sessionClose() {
        seleniumConnection.sessionClose();
    }

    public String sessionHtml() {
        return sessionHtmlTag(TAG_HTML);
    }

    public String sessionHtmlTag(String tag) {
        return new StringBuilder().append("<")
                                  .append(tag)
                                  .append(">")
                                  .append(
                                          seleniumConnection.getSession()
                                                            .findElement(By.tagName(tag))
                                                            .getAttribute(ATTRIBUTE_INNER_HTML))
                                  .append("</")
                                  .append(tag)
                                  .append(">")
                                  .toString();
    }

    public List<WebElement> sessionHtmlElements(String xpath) {
        return seleniumConnection.getSession()
                                 .findElements(By.xpath(xpath));
    }
}
