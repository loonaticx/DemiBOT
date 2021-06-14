package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.Main;

import java.io.File;
import java.util.List;

public class AvatarCommand implements Command {
    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        User author = event.getAuthor();
        //File f = new File("path/to/" + args);
        if(args.size() < 1) {
            return;
        }
        try {
            Main.jda.getSelfUser().getManager().setAvatar(Icon.from(new File(args.get(0))));
        } catch(IllegalArgumentException e) {

        }
    }
}
