package pro.loonatic.demibot;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

public class SecurityUtils{

    public static boolean isOwner(User author) {
        return Config.getOwnerIds().contains(author.getId());
    }

    public static boolean isTrustedServer(Guild guild) {
        return Config.getServerIds().contains(guild.getId());
    }

    public static boolean isTrustedUser(User author) {
        return isOwner(author) || Config.getUserIds().contains(author.getId());
    }
}
