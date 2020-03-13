package pro.loonatic.demibot;

import pro.loonatic.demibot.commands.Command;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static pro.loonatic.demibot.Config.*;
import static pro.loonatic.demibot.SecurityUtils.*;


public class Main {
    public static JDA jda;
    private static ThreadPoolExecutor threadPool = null;

    public static ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public static void setupThreadPool(int threadCount) {
        // If we already have a thread pool, we'll need to shut it down.
        if (threadPool != null) {
            // If we have a thread pool with this many threads already, we don't have to create a new one
            if (threadPool.getActiveCount() == threadCount) {
                return;
            }

            threadPool.shutdown();
        }

        threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
    }

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

    /* These keep track of how many commands a user has running currently */
    private Map<String, Integer> userToCommandCount = new ConcurrentHashMap<String, Integer>();

    public int getCommandCount(User user) {
        return userToCommandCount.getOrDefault(user.getId(), 0);
    }

    public void setCommandCount(User user, int count) {
        userToCommandCount.put(user.getId(), count);
    }

    public void addCommandCount(User user, int count) {
        setCommandCount(user,getCommandCount(user) + count);
    }

    public void removeCommandCount(User user, int count) {
        setCommandCount(user, Math.max(0, getCommandCount(user) - count));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Guild guild = event.getGuild();
        Channel channel = event.getTextChannel();
        List<Role> role = guild.getRoles();
        //System.out.println("roles : " + Arrays.asList(role));
        PrivateChannel privateChannel = event.getPrivateChannel();

        if(isDebugMode()) {
            if(!isOwner(author)) {
                System.out.println("TRUSTED? " + author + " " + isTrustedUser(author));
                return;
            }
            try {
                if (privateChannel.getType().toString().equals("PRIVATE")) {
                    System.out.println("Channel Type: " + privateChannel.getType());
                    return;
                }
            } catch(NullPointerException e) {

            }
/*
            System.out.println("Channel Type: " + channel.getType());
            System.out.println("CHANNEL TRUSTED?: " + isTrustedChannel(channel));
            System.out.println("AUTHOR? " + author + " " + isOwner(author));
            System.out.println("SERVER TRUSTED? " + guild + " " + isTrustedServer(guild));
*/            //System.out.println("ROLE TRUSTED? " + isTrustedRole(role) );
        }
        if (!isTrustedServer(guild)) {
                return;
        }
        if (!isTrustedChannel(channel)) {
                return;
        }
        if (!isTrustedUser(author) && !isOwner(author)) {
                return;
        }
        if (author.isBot() || !event.isFromType(ChannelType.TEXT)) {
                return;
        }

        String msg = event.getMessage().getContentRaw();

        if (!msg.startsWith(Config.getPrefix())) {
            return;
        }

        msg = msg.substring(Config.getPrefix().length());
        List<String> args = new ArrayList<String>(Arrays.asList(msg.split(" ")));
        Command command = CommandManager.getCommand(args.get(0));

        if (command == null) {
            return;
        }

        args.remove(0);

        // Let's see if this user can start a new command right now...
        if (getCommandCount(author) >= Config.getMaxConcurrentCommands()) {
            String plural = Config.getMaxConcurrentCommands() == 1 ? "" : "s";
            event.getChannel().sendMessage("You are already running a maximum of " + Config.getMaxConcurrentCommands() + " command" + plural + "! Please slow down a bit :(").queue();
            return;
        }

        // Add a new command currently running to the user
        addCommandCount(author, 1);

        Main.getThreadPool().execute(() -> {
            try {
                command.process(event, args);
            } catch (Exception e) {
                e.printStackTrace();
                event.getChannel().sendMessage(e.getMessage());
            }

            // This command has finished, we can stop keeping track of it
            removeCommandCount(author, 1);
        });
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
