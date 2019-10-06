package pro.loonatic.demibot.commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class PingCommand implements Command {
    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        MessageChannel channel = event.getChannel();
        long milli = System.currentTimeMillis() - event.getMessage().getCreationTime().toInstant().toEpochMilli();

        channel.sendMessage("``Pong`` " + milli + " ms").queue();
    }
}
