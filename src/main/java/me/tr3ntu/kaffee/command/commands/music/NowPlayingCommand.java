package me.tr3ntu.kaffee.command.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import me.tr3ntu.kaffee.lavaplayer.GuildMusicManager;
import me.tr3ntu.kaffee.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class NowPlayingCommand implements ICommand {
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
            m.setTitle(" \u26d4  You need to be in a voice channel for this command to work!");
            m.setColor(Color.red);

            channel.sendMessage(m.build()).queue();
            m.clear();
            return false;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            EmbedBuilder m = new EmbedBuilder();
            m.setTitle(" \u26a0  There is no track playing currently");
            m.setColor(Color.yellow);

            channel.sendMessage(m.build()).queue();
            m.clear();
            return false;
        }

        final AudioTrackInfo info = track.getInfo();

        EmbedBuilder m = new EmbedBuilder();
        m.setTitle("\uD83D\uDCC0  Now playing: ");
        m.setDescription(" *" + info.title + "* **by** *" + info.author + "* **(Link:** *" + info.uri + "* **)!**");
        m.setColor(Color.green);

        channel.sendMessage(m.build()).queue();
        m.clear();

        return false;
    }

    @Override
    public String getName() {
        return "nowpl";
    }

    @Override
    public String getHelp() {
        return "Shows the currently playing song";
    }
}
