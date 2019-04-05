package pro.loonatic.demibot;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import static pro.loonatic.demibot.Config.*;

public class SecurityUtils{
    //private static User author;
    private static MessageReceivedEvent event;
/*
    public SecurityUtils(MessageReceivedEvent event) {
        this.event = event;
        onMessageReceived(event);
    }
    public void onMessageReceived(MessageReceivedEvent event) {
        author = event.getAuthor();
    }*/
    public static boolean isOwner(User author) {
        return author.getId().equals(getOwnerID());
    }
    public static boolean isServerID() {
        return event.getGuild().getId().equals(getServerID());
    }
    public static boolean isServerIDs() {
        for(String servers : getServerIDs()) {
            if(event.getGuild().getId().equals(servers)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isTrustedUser(User author) {
        for(String person : getUserIDs()) {
            System.out.println(person);
            if (event.getAuthor().getId().equals(person)) {
                return true;
            }
        }
        return false;
    }
}
