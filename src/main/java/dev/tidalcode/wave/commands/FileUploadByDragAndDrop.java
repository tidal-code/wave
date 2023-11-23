package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.exceptions.NoSuchFileException;
import dev.tidalcode.wave.javascript.Scripts;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.webelement.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;


@SuppressWarnings("unused")
public class FileUploadByDragAndDrop extends CommandAction implements Command<Void> {


    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();
    private String fileName;

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

        String filePath;
        if (Finder.findFileIfExists(fileName).isPresent()) {
            filePath = Finder.findFilePath(fileName);
        } else {
            throw new NoSuchFileException(String.format("No file could be found with the given file name '%s'", fileName));
        }
        WebElement element = webElement.getElement(context);
        WebElement virtualElement = (WebElement) ((JavascriptExecutor) ((RemoteWebElement) (element)).getWrappedDriver()).executeScript(Scripts.dragAndDropFileUpload(), element);
        virtualElement.sendKeys(filePath);
        return null;
    };

    @Override
    public Function<CommandContext, Void> getFunction() {
        return function;
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public void fileUploadByDragAndDropAction() {
        function.apply(context);
    }

    public void fileUploadByDragAndDrop() {
        timeCounter.restart();
        super.execute(Commands.InputCommands.UPLOAD_FILE_DRAG_AND_DROP.toString(), ignoredExceptions, timeCounter);
    }
}
