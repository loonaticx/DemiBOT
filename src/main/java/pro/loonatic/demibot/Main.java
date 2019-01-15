package pro.loonatic.demibot;

import pro.loonatic.demibot.commands.Command;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pro.loonatic.demibot.Config.getBotToken;


public class Main {
    public static JDA jda;
    public static void main(String...args) {
        Config.loadConfig();
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(getBotToken())
                    .addEventListener(new MessageListener())
                    .buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Connected");
    }
}

class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //User self = (User)event.getJDA();
        User author = event.getAuthor();

        //boolean isLoonatic = author.getId().equals("141314236998615040");

        if (author.isBot() || !event.isFromType(ChannelType.TEXT)) {
            return;
        }

        String msg = event.getMessage().getContentRaw();

        if (!msg.startsWith(";")) {
            return;
        }

        msg = msg.substring(1);
        List<String> args = new ArrayList<String>(Arrays.asList(msg.split(" ")));
        Command command = CommandManager.getCommand(args.get(0));

        if (command == null) {
            return;
        }

        args.remove(0);

        try {
            command.process(event, args);
        } catch (Exception e) {
            e.printStackTrace();
            event.getChannel().sendMessage(e.getMessage());
        }


            /*
            if (msg.startsWith("pfp")){
                try {
                    jda.changePresence(StatusType.IDLE, ActivityType.PLAYING, "playing text");
                }catch (AWTException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            */
/*
            else if (msg.contains("test")) {
            File f = new File("path/to/file.jpg");
            try {
                jda.getSelfUser().getManager().setAvatar(Icon.from(f));
            } catch (IOException e) {
                e.printStackTrace();
            }

            */
    }
}
