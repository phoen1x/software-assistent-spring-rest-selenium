package de.livingfire.selenium.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.openqa.selenium.WebElement;

import de.livingfire.selenium.exception.LivingfireRuntimeException;
import de.livingfire.selenium.interfaces.HtmlConstant;
import de.livingfire.selenium.interfaces.RequestConstant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CrawlService implements RequestConstant, HtmlConstant {

    private SeleniumService seleniumService;
    private boolean renderHtml;

    public String openRandomPage() {
        StringBuilder response = new StringBuilder();

        List<WebElement> links = linkGetViaXPath();
        linkPrint(links, response);
        linkClick(links, response);

        if (renderHtml) {
            return "<html><body><pre>" + response.toString() + "</pre></body></html>";
        }
        return response.toString();
    }

    public List<WebElement> linkGetViaXPath() {
        List<WebElement> links = seleniumService.sessionHtmlElements(XPATH_JEKYLL_LINK);
        if (links.size() == 0) {
            throw new LivingfireRuntimeException("xpath not found: " + XPATH_JEKYLL_LINK);
        }
        return links;
    }

    public int linkClick(List<WebElement> links,
                         StringBuilder response) {
        int randomLink = ThreadLocalRandom.current()
                                          .nextInt(0, links.size());

        response.append("\nRandom choose to click link nr: ")
                .append(randomLink)
                .append(" --- ")
                .append(
                        links.get(randomLink)
                             .getAttribute(ATTRIBUTE_INNER_HTML));

        links.get(randomLink)
             .click();

        return randomLink;
    }

    public void linkPrint(List<WebElement> links,
                          StringBuilder response) {
        response.append("\nLinks:");
        IntStream.range(0, links.size())
                 .forEach(i -> {
                     response.append("\n")
                             .append(i)
                             .append(" --- ")
                             .append(
                                     links.get(i)
                                          .getText());
                 });
    }

}
