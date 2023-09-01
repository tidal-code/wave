package com.tidal.wave.data;

import com.tidal.wave.command.Command;

import java.util.LinkedList;
import java.util.List;


@SuppressWarnings("rawtypes")
public class CommandStore<T> {

    private static final ThreadLocal<List<Command>> commands = ThreadLocal.withInitial(LinkedList::new);

    public static void storeCommand(Command command){
        commands.get().add(command);
    }

    public static List<Command> getCommands(){
        return commands.get();
    }

    public static void clearCommands(){
        commands.remove();
    }

}
