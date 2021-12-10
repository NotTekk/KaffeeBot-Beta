package me.tr3ntu.kaffee.command.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import me.tr3ntu.kaffee.lavaplayer.GuildMusicManager;
import me.tr3ntu.kaffee.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class SkipCommand implements ICommand {
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
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if (audioPlayer.getPlayingTrack() == null) {
            EmbedBuilder m = new EmbedBuilder();
            m.setTitle(" \u26a0  There is no track playing currently");
            m.setColor(Color.yellow);

            channel.sendMessage(m.build()).queue();
            m.clear();
            return false;
        }

        musicManager.scheduler.nextTrack();
        EmbedBuilder m = new EmbedBuilder();
        m.setTitle(" \u26a0  Skipped!");
        m.setColor(Color.green);

        channel.sendMessage(m.build()).queue();
        m.clear();
        return false;
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "skips the current track";
    }
}
