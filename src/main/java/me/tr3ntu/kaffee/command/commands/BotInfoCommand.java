package me.tr3ntu.kaffee.command.commands;

import me.tr3ntu.kaffee.Config;
import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class BotInfoCommand implements ICommand {


    @Override
    public boolean handle(CommandContext ctx) {

        List<Guild> guilds = ctx.getJDA().getGuilds();

        EmbedBuilder m = new EmbedBuilder();
        m.setColor(Color.BLUE);
        m.setTitle("\uD83E\uDD16   Bot Info\n");
        m.setThumbnail(ctx.getSelfUser().getAvatarUrl());
        m.addField(":id: Bot ID", ctx.getSelfUser().getId(), true);
        m.addField(":id: Version", Config.get("VERSION"), true);
        m.addField("\uD83C\uDF10 Server Count", "In " + String.valueOf(guilds.size()) + " servers",true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
        m.addField(":clock2: Creation date", ctx.getMember().getTimeCreated().format(formatter), true);
        m.setDescription("Kaffee's Official Community Server: " + "https://discord.gg/C6jx6u6KzD\n");
        m.setFooter("\n\nCreated by Tr3ntu#0001", "https://images-ext-1.discordapp.net/external/MmPH3E0_AUA6cE0-QkFa2kM5nvhonrqU6bOj3C3YgCg/https/cdn.discordapp.com/avatars/571414793454485511/4e135ac814cbc7ae8103e5518e6c6462.png");

        ctx.getChannel().sendMessage(m.build()).queue();
        m.clear();
        return false;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getHelp() {
        return null;
    }
}
