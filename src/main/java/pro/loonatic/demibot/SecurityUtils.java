package pro.loonatic.demibot;

import net.dv8tion.jda.api.entities.*;

import java.util.List;

public class SecurityUtils{

    public static boolean isOwner(User author) {
        return Config.getOwnerIds().contains(author.getId());
    }

    public static boolean isTrustedServer(Guild guild) {
        return Config.getServerIds().contains(guild.getId()) || Config.getServerIds().contains("any");
    }

    public static boolean isTrustedChannel(TextChannel channel) {
        return Config.getChannelIds().contains(channel.getId()) || Config.getServerIds().contains("any");
    }
    public static boolean isTrustedRole(List<Role> role) {
        for(Role list : role) {
            if(Config.getRoleIds().contains(list.toString())) {
                getTrustedRole(list);
                return true;
            }
            else if (Config.getRoleIds().contains("any")) {
                return true;
            }
        }
        return false;
    }
    public static String getTrustedRole(Role t) {
        return t.toString();
    }
    public static boolean isTrustedUser(User author) {
        return isOwner(author) || Config.getUserIds().contains(author.getId());
    }
    public static boolean isDM(PrivateChannel privateChannel) {
        return privateChannel.getType().toString().equals("PRIVATE");
    }
}
