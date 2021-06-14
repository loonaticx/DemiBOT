package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

import static pro.loonatic.demibot.CommandUtils.wGet;

public class WGetCommand implements Command {
    public void process(MessageReceivedEvent event, List<String> args) {
        MessageChannel channel = event.getChannel();
        wGet(args);
        channel.sendMessage("Downloaded: " + args).queue();

    }
}
