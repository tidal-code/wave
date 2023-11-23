package dev.tidalcode.wave.commands;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.page.Page;
import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.webelement.ElementFinder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

import static dev.tidalcode.wave.browser.Browser.close;
import static dev.tidalcode.wave.webelement.ElementFinder.find;

public class FileUploadTest {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components" + File.separator + "fileUpload" + File.separator + "fileUpload.html"));
    }

    @After
    public void testCleanup() {
        close();
    }

    @Test
    public void fileUploadDragAndDropTest() {
        String fileName = "TestCSVFile.csv";
        ElementFinder.find("id:dropzone").uploadFileByDragAndDrop(fileName);
        String alertText = Page.getAlertText();
        Assert.assertTrue("File upload failed, Alert do not contain the file name " + fileName + " alert text " + alertText, alertText.contains(fileName));
        Page.dismissAlert();
    }
}
