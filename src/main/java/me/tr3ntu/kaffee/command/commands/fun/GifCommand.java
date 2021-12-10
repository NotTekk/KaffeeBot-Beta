package me.tr3ntu.kaffee.command.commands.fun;

import me.tr3ntu.kaffee.command.CommandContext;
import me.tr3ntu.kaffee.command.ICommand;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

public class GifCommand implements ICommand {
    @Override
    public boolean handle(CommandContext ctx) {
        String url;
        JSONArray array;
        String query = "";
        for(String arg : ctx.getArgs()) {
            query += arg.toLowerCase() + "+";
            query = query.substring(0, query.length()-1);
        }
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=dc6zaTOxFJmzC").build();
        try {
            Random rand = new Random();
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            array = json.getJSONArray("data");
            //Random GIF returned by the API
            int gifIndex = rand.nextInt(array.length());
            url = (String) array.getJSONObject(gifIndex).get("url");
            ctx.getChannel().sendMessage(url).queue();
        } catch (IOException | NullPointerException e) {
            ctx.getChannel().sendMessage("No GIF found :cry:").queue();
        }
        return false;
    }

    @Override
    public String getName() {
        return "gif";
    }

    @Override
    public String getHelp() {
        return null;
    }
}
