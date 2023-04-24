package com.tidal.wave.commands;

import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.exceptions.NoSuchFileException;
import com.tidal.wave.filehandlers.Finder;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.function.Supplier;

public class FileUpload extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();
    private CommandContext context;
    private String fileName;
    private String filePath;

    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
        this.fileName = context.getTextInput();
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.sendKeys();
    }

    public void fileUploadAction() {
        WebElement element = webElement.getElement(context);
        element.sendKeys(filePath);
    }

    public void fileUpload() {
        if (fileName.isEmpty()) throw new IllegalArgumentException("File name should not be null or empty");

        if (Finder.findFileIfExists(fileName).isPresent()) {
            filePath = Finder.findFilePath(fileName);
        } else {
            throw new NoSuchFileException(String.format("No file could be found with the given file name '%s'", fileName));
        }

        timeCounter.restart();
        super.execute(Commands.InputCommands.UPLOAD_FILE.toString(), ignoredExceptions, timeCounter);
    }
}
