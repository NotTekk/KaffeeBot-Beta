package me.tr3ntu.kaffee.command.commands.music;

import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import me.tr3ntu.kaffee.lavaplayer.GuildMusicManager;
import me.tr3ntu.kaffee.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class RepeatCommand implements ICommand {
    @Override
    public boolean handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            EmbedBuilder m = new EmbedBuilder();
            m.setTitle(" \u274c  Koffee needs to be in a voice channel for this to work!");
            m.setColor(Color.red);

            channel.sendMessage(m.build()).queue();
            m.clear();
            return false;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            EmbedBuilder m = new EmbedBuilder();
            m.setTitle(" \u26d4  You need to be in a voice channel for this command to work!");
            m.setColor(Color.red);

            channel.sendMessage(m.build()).queue();
            m.clear();
            return false;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            EmbedBuilder m = new EmbedBuilder();
            m.setTitle(" \u26d4  You need to be in the same voice channel as Koffee for this command to work!");
            m.setColor(Color.red);

            channel.sendMessage(m.build()).queue();
            m.clear();
            return false;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final boolean newRepeating = !musicManager.scheduler.repeating;

        musicManager.scheduler.repeating = newRepeating;
        String r = newRepeating ? "repeating" : "not repeating";

        EmbedBuilder m = new EmbedBuilder();
        m.setTitle(" \u2B55 The player has been set to: " + r);
        m.setColor(Color.red);

        channel.sendMessage(m.build()).queue();
        m.clear();
        return newRepeating;
    }

    @Override
    public String getName() {
        return "repeat";
    }

    @Override
    public String getHelp() {
        return "Loops the current song";
    }
}
