package me.tr3ntu.kaffee.command.commands;

import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class HelpCommand implements ICommand {

    @Override
    public boolean handle(CommandContext ctx) {

        EmbedBuilder m = new EmbedBuilder();
        m.setColor(Color.BLUE);
        m.setTitle("\uD83D\uDD37   Help");
        m.setDescription("**Music:**\n\n" +
                            "join - Makes Kaffee join your voice channel\n" +
                            "jeave - Makes Kaffee disconect from the voice channel\n" +
                            "playn - Shows what is being played right now\n" +
                            "queue - Show the songs queue\n" +
                            "repeat - Loops the current song that is playing\n" +
                            "skip - Skips to the next song\n" +
                            "stop - Stops the music and clean the queue\n\n" +
                        "**Utilities:**\n\n" +
                            "gituser - Shows github user info (use github profile username)\n" +
                            "ping - Checks the bot connection\n" +
                            "whois - Provides information about the mentioned user\n\n" +
                        "**Fun:**\n\n" +
                            "ym - Makes YO MAMMA jokes with the mentioned user\n" +
                            "gif - search and displays gifs based on users input\n\n" +
                        "**Bot:**\n\n" +
                            "info - Provides information about the bot and invite to its official server\n\n");
        m.setFooter("\n\nCreated by Tr3ntu#0001", "https://images-ext-1.discordapp.net/external/MmPH3E0_AUA6cE0-QkFa2kM5nvhonrqU6bOj3C3YgCg/https/cdn.discordapp.com/avatars/571414793454485511/4e135ac814cbc7ae8103e5518e6c6462.png");

        ctx.getChannel().sendMessage(m.build()).queue();
        m.clear();

        return false;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return null;
    }
}