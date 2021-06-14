package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

import static pro.loonatic.demibot.Main.jda;

public class StatusCommand implements Command {
    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        String message = args.toString();
        message = message.substring(1, message.length()-1).replaceAll(",", " ");
        jda.getPresence().setActivity(Activity.streaming(message, "https://twitch.tv/loonatricks"));
    }
}