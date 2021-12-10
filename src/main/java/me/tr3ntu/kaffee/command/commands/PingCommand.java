package me.tr3ntu.kaffee.command.commands;

import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;


public class PingCommand implements ICommand {

    private static long inputTime;

    public static void setInputTime(long inputTimeLong) {
        inputTime = inputTimeLong;
    }

    private Color getColorByPing(long ping) {
        if (ping < 100)
            return Color.cyan;
        if (ping < 400)
            return Color.green;
        if (ping < 700)
            return Color.yellow;
        if (ping < 1000)
            return Color.orange;
        return Color.red;
    }

    @Override
    public boolean handle(CommandContext ctx) {

        long ping = ctx.getJDA().getGatewayPing();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(getColorByPing(ping));
        builder.setTitle("\uD83C\uDFD3  Pong!");
        builder.setDescription("Ping : " + ctx.getJDA().getGatewayPing() + " ms");

        ctx.getChannel().sendMessage(builder.build()).queue();

        return false;
    }

    @Override
    public String getHelp() {
        return "Shows the current ping from the bot to the discord servers";
    }

    @Override
    public String getName() {
        return "ping";
    }
}