package de.livingfire.selenium.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.livingfire.selenium.interfaces.RequestConstant;
import de.livingfire.selenium.service.CrawlService;
import de.livingfire.selenium.service.SeleniumService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SeleniumController implements RequestConstant {

    private SeleniumService seleniumService;

    @GetMapping(REQUEST_API_SELENIUM_WEBPAGE_CRAWL)
    public String webpageCrawl(@RequestParam(value = "html",
            required = false) final boolean renderHtml) {
        webpageOpen(REQUEST_EXTERN_LIVINGFIRE);
        return new CrawlService(seleniumService, renderHtml).openRandomPage();
    }

    @GetMapping(REQUEST_API_SELENIUM_SESSION_CLOSE)
    public String sessionClose() {
        seleniumService.sessionClose();
        return "session closed";
    }

    @GetMapping(REQUEST_API_SELENIUM_SESSION_HTML)
    public String html() {
        return seleniumService.sessionHtml();
    }

    @GetMapping(REQUEST_API_SELENIUM_WEBPAGE_OPEN)
    public void webpageOpen(String webpage) {
        if (!StringUtils.hasText(webpage)) {
            webpage = REQUEST_EXTERN_LIVINGFIRE_SKYLAR;
        }
        seleniumService.navigateTo(webpage);
    }

}
