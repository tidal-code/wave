package com.tidal.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.exceptions.NoSuchFileException;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class FileUploadWRC extends CommandAction implements Command<Void> {

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
    public CommandContext getCommandContext() {
        return context;
    }

    Function<CommandContext, Void> function = e -> {
        if (fileName.isEmpty()) throw new IllegalArgumentException("File name should not be null or empty");

        if (Finder.findFileIfExists(fileName).isPresent()) {
            filePath = Finder.findFilePath(fileName);
        } else {
            throw new NoSuchFileException(String.format("No file could be found with the given file name '%s'", fileName));
        }

        WebElement element = webElement.getElement(context);
        element.click();

        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            throw new RuntimeException(ex);
        }
        StringSelection stringSelection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);

        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        return Void.TYPE.cast(null);
    };

    @Override
    public Function getFunction() {
        return function;
    }


    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }


    public void fileUploadAction() throws AWTException {
        function.apply(context);
    }

    public void fileUploadWRC() {
        timeCounter.restart();
        super.execute(Commands.InputCommands.UPLOAD_FILE.toString(), ignoredExceptions, timeCounter);
    }

}
