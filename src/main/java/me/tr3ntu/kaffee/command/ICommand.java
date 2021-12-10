package me.tr3ntu.kaffee.command;

import java.util.List;

public interface ICommand {
    boolean handle(CommandContext ctx);

    String getName();

    String getHelp();

    default List<String> getAliases() {
        return List.of(); // use Arrays.asList if you are on java 8
    }
}
