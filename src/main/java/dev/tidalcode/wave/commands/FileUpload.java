package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.exceptions.NoSuchFileException;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.webelement.Element;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;


@SuppressWarnings("unused")
public class FileUpload extends CommandAction implements Command<Void> {

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
        element.sendKeys(filePath);

        return null;
    };

    @Override
    public Function<CommandContext, Void> getFunction() {
        return function;
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.sendKeys();
    }

    public void fileUploadAction() {
        function.apply(context);
    }

    public void fileUpload() {
        timeCounter.restart();
        super.execute(Commands.InputCommands.UPLOAD_FILE.toString(), ignoredExceptions, timeCounter);
    }
}
