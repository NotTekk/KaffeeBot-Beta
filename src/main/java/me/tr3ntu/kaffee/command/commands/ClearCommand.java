package me.tr3ntu.kaffee.command.commands;

import me.tr3ntu.kaffee.Config;
import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class ClearCommand implements ICommand {

    @Override
    public boolean handle(CommandContext ctx) {
        String[] args = ctx.getMessage().getContentDisplay().split(" ");
        int i=Integer.parseInt(String.valueOf(args));

        System.out.println(i);

        if(ctx.getMessage().getMember().hasPermission(ctx.getChannel(), Permission.MESSAGE_MANAGE)) {
            if(ctx.getMessage().getContentDisplay().equalsIgnoreCase("k!clear")) {
                if(args.length <= 2) {

                    EmbedBuilder m = new EmbedBuilder();
                    m.setTitle("Please enter a number between 2 and 100");
                    m.setColor(Color.red);
                    ctx.getChannel().sendMessage(m.build()).queue();

                } else {
                    TextChannel target = ctx.getMessage().getMentionedChannels().get(0);
                    purgeMessages(target, Integer.parseInt(args[2]));

                }
            }
        }

        return false;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getHelp() {
        return null;
    }

    private void purgeMessages(TextChannel channel, int num) {
        MessageHistory history = new MessageHistory(channel);
        List<Message> msgs;

        msgs = history.retrievePast(num).complete();
        channel.deleteMessages(msgs).queue();
    }
}

