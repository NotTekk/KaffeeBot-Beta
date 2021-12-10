package me.tr3ntu.kaffee.command.commands;

import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;

public class GitHubCommand implements ICommand {

    @Override
    public boolean handle(CommandContext ctx) {
        String msg = String.join(" ", ctx.getArgs());
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/users/" + msg).build();
        EmbedBuilder builder = new EmbedBuilder();

        try {
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());

            String pseudonym = json.getString("name");
            String bio = "None";
            String location = "Unknown";
            String website = "None";
            try {
                bio = json.getString("bio");
            } catch (JSONException e) {
                //no bio
            }
            try {
                location = json.getString("location");
            } catch (JSONException e) {
                //no known location
            }

            if(!json.getString("blog").equals("")) website = json.getString("blog");

            builder.setColor(Color.blue);
            builder.setAuthor("Information about " + pseudonym + " (" + msg + ")", "https://github.com/" + msg + "", "http://i.imgur.com/pH59eAC.png");
            builder.setThumbnail(json.getString("avatar_url"));

            builder.addField("User bio", bio, false);
            builder.addField("Location", location, true);
            builder.addField("Website", website, true);
            builder.addField("Public repositories", String.valueOf(json.getInt("public_repos")), true);
            builder.addField("Public gists", "" + String.valueOf(json.getInt("public_gists")), true);

            builder.addField("Followers", "" + String.valueOf(json.getInt("followers")), true);
            builder.addField("Following", "" + String.valueOf(json.getInt("following")), true);

            ctx.getChannel().sendMessage(builder.build()).queue();
        } catch (IOException | NullPointerException e) {
            ctx.getChannel().sendMessage("The GitHub API might be down at the moment").queue();
        } catch (JSONException e) {
            ctx.getChannel().sendMessage("User not found.").queue();
        }
        return false;
    }

    @Override
    public String getName() {
        return "gituser";
    }

    @Override
    public String getHelp() {
        return "Shows github user info";
    }
}
