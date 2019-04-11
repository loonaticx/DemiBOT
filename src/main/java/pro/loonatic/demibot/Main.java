package pro.loonatic.demibot;

import pro.loonatic.demibot.commands.Command;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pro.loonatic.demibot.Config.*;
import static pro.loonatic.demibot.SecurityUtils.*;


public class Main {
    public static JDA jda;
    public static void main(String...args) throws FileNotFoundException {
        Config.loadConfig();
        final boolean debug = isDebugMode();
        new CommandUtils();
        new CommandManager(debug);

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
        jda.getPresence().setGame(Game.streaming("on " + System.getProperty("os.name"), "https://twitch.tv/loonatricks"));
        //jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
    }
}

class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //User self = (User)event.getJDA();
        User author = event.getAuthor();
        Guild guild = event.getGuild();

        if(isDebugMode()) {
            if(!isOwner(author)) {
                System.out.println("TRUSTED? " + author + " " + isTrustedUser(author));
                return;
            }
            System.out.println("AUTHOR? " + author+ " " + isOwner(author));
            System.out.println("SERVER TRUSTED? " + guild + " " + isTrustedServer(guild));
        }
        if (!isTrustedServer(guild)) {
            return;
        }
        if (!isTrustedUser(author) && !isOwner(author)) {
            return;
        }

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
