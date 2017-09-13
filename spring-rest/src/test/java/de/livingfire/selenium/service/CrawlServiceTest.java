package de.livingfire.selenium.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

import de.livingfire.selenium.exception.LivingfireRuntimeException;

@RunWith(MockitoJUnitRunner.class)
public class CrawlServiceTest {

    public static final String CONTENT_LINK_PRINT = String.join(
            "\n",
                "",
                "Links:",
                "0 --- Element_0",
                "1 --- Element_1",
                "2 --- Element_2");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private SeleniumService seleniumService;

    private CrawlService crawlService;

    private List<WebElement> fakeLinks;

    @Before
    public void setUp() {
        crawlService = new CrawlService(seleniumService, true);

        fakeLinks = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            WebElement element = mock(WebElement.class);

            // set element text
            when(element.getText()).thenReturn("Element_" + i);

            // disable clicking of element
            doNothing().when(element)
                       .click();

            fakeLinks.add(element);
        }
    }

    @Test
    public void testLinkPrint() throws Exception {
        StringBuilder actual = new StringBuilder();
        crawlService.linkPrint(fakeLinks, actual);

        assertThat(actual.toString()).isEqualTo(CONTENT_LINK_PRINT);
    }

    @Test
    public void testLinkClick() throws Exception {
        StringBuilder actual = new StringBuilder();
        int selectedLink = crawlService.linkClick(fakeLinks, actual);
        assertThat(actual.toString()).matches("\nRandom choose to click link nr: " + selectedLink + " --- null");
    }

    @Test
    public void testLinkGetViaXPathWithoutElementsShouldThrowExeption() throws Exception {
        fakeLinks = new ArrayList<>();
        try {
            crawlService.linkGetViaXPath();
            fail("Exception expected");
        } catch (Throwable e) {
            this.thrown.expect(LivingfireRuntimeException.class);
            this.thrown.expectMessage("xpath not found: //h2/a");
            throw e;
        }
    }

    @Test
    public void testLinkGetViaXPathWithElementsShouldReturnValues() throws Exception {
        when(seleniumService.sessionHtmlElements(any())).thenReturn(fakeLinks);

        List<WebElement> actual = crawlService.linkGetViaXPath();
        assertThat(actual).hasSize(3);
    }

    @Test
    public void testOpenRandomPageHtmlFalseShouldReturnNoTags() throws Exception {
        crawlService = new CrawlService(seleniumService, false);

        when(seleniumService.sessionHtmlElements(any())).thenReturn(fakeLinks);
        String actual = crawlService.openRandomPage();

        assertThat(actual).startsWith(CONTENT_LINK_PRINT);
    }

    @Test
    public void testOpenRandomPageHtmlTrueShouldReturnTags() throws Exception {
        crawlService = new CrawlService(seleniumService, true);

        when(seleniumService.sessionHtmlElements(any())).thenReturn(fakeLinks);
        String actual = crawlService.openRandomPage();

        assertThat(actual).startsWith("<html><body><pre>" + CONTENT_LINK_PRINT);
    }
}
