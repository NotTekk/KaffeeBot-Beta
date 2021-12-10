package me.tr3ntu.kaffee.command.commands.fun;

import me.tr3ntu.kaffee.Config;
import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import net.dv8tion.jda.api.entities.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YoMamaCommand implements ICommand {
    @Override
    public boolean handle(CommandContext ctx) {

        List<User> mentionedUsers = ctx.getMessage().getMentionedUsers();
        List<User> modifiable = new ArrayList<>(mentionedUsers);
        modifiable.remove(0);

        if (ctx.getMessage().getMentionedUsers().isEmpty())
        {
            ctx.getChannel().sendMessage("No user mentioned.");
        }
        else {
            String joke;
            OkHttpClient caller = new OkHttpClient();
            Request request = new Request.Builder().url("http://api.yomomma.info/").build();
            for(User u : modifiable) {
                try {
                    Response response = caller.newCall(request).execute();
                    JSONObject json = new JSONObject(response.body().string());
                    joke = (String) json.get("joke");
                    ctx.getChannel().sendMessage(u.getAsMention() + " " + joke).queue();
                } catch (IOException | NullPointerException e) {
                    ctx.getChannel().sendMessage("No joke found").queue();
                }
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return "ym";
    }

    @Override
    public String getHelp() {
        return "Yo Mamma Joke";
    }
}
