package me.tr3ntu.kaffee.command.commands;

import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WhoIsCommand implements ICommand {

    @Override
    public boolean handle(CommandContext ctx) {

        if(ctx.getMessage().getMentionedUsers().isEmpty()) {
            ctx.getChannel().sendMessage("No user mentioned.").queue();
            return false;
        }
        User user = ctx.getMessage().getMentionedUsers().get(1);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Information about " + user.getName() + "#" + user.getDiscriminator() , null, "http://i.imgur.com/880AyL6.png");
        builder.setColor(ctx.getGuild().getMemberById(user.getId()).getColor());
        builder.setThumbnail(user.getAvatarUrl());
        builder.addField(":id: User ID", user.getId(), true);

        String nickname = "None";
        if(ctx.getGuild().getMemberById(user.getId()).getNickname() != null) nickname = ctx.getGuild().getMemberById(user.getId()).getNickname();
        builder.addField(":information_source: Nickname", nickname, true);
        builder.addField(":computer: Status", ctx.getMessage().getGuild().getMemberById(user.getId()).getOnlineStatus().name().toLowerCase(), true);

        String activity = "None";
        if(!ctx.getGuild().getMemberById(user.getId()).getActivities().isEmpty()) {
            activity = ctx.getGuild().getMemberById(user.getId()).getActivities().get(0).getName();
        }
        builder.addField(":video_game: Activity", activity, true);

        String isOwner = "No";
        if( ctx.getGuild().getMemberById(user.getId()).isOwner()) {
            isOwner = "Yes";
        }
        builder.addField(":white_check_mark: Owner",  isOwner, true);

        String role = "No role";
        if(!ctx.getGuild().getMemberById(user.getId()).getRoles().isEmpty()) {
            role = ctx.getGuild().getMemberById(user.getId()).getRoles().get(0).getAsMention();
        }
        builder.addField(":medal: Highest role", role, true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
        builder.addField(":clock2: Creation date", user.getTimeCreated().format(formatter), true);
        builder.addField(":inbox_tray:  Join date", ctx.getGuild().getMemberById(user.getId()).getTimeJoined().format(formatter), true);

        ctx.getChannel().sendMessage(builder.build()).queue();
        return false;
    }

    @Override
    public String getName() {
        return "whois";
    }

    @Override
    public String getHelp() {
        return "Provides information about the mentioned user.";
    }
}
