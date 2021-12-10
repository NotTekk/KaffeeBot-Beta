package me.tr3ntu.kaffee.command.commands.music;

import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

@SuppressWarnings("ConstantConditions")
public class JoinCommand implements ICommand {
    @Override
    public boolean handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (selfVoiceState.inVoiceChannel()) {

            EmbedBuilder m = new EmbedBuilder();
            m.setTitle(" \u274c  koffee is already in a voice channel!");
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

        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        final VoiceChannel memberChannel = memberVoiceState.getChannel();

        audioManager.openAudioConnection(memberChannel);

        EmbedBuilder m = new EmbedBuilder();
        m.setTitle("\uD83D\uDD0A Connecting to: " + memberChannel.getName());
        m.setColor(Color.CYAN);

        channel.sendMessage(m.build()).queue();
        m.clear();
        return false;
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Makes the bot join your voice channel";
    }
}
