package pro.loonatic.demibot.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;
import pro.loonatic.demibot.commands.systemutil.*;

import java.awt.*;
import java.util.List;

public class SInfoCommand implements Command {

    private static CommandUtils CInfo = new CommandUtils();
    private static ramUtil ramInfo = new ramUtil();
    private static diskUtil diskInfo = new diskUtil();
    private static cpuUtil cpuInfo = new cpuUtil();

    public String getHWInfo() {
        return getRamInfo() + getCPUInfo();
    }

    public String getRamInfo() {
        return
                "Total Physical Memory: " + ramInfo.getTotalPRAM() + " MB" +
                " (*~" + ramInfo.getTotalPRAMGB() + " GB*)" +
                "\n Total Free Physical Memory: " +  ramInfo.getFreePRAM() + " MB" +
                " (*~" + ramInfo.getFreePRAMGB() + " GB*)\n";

    }

    public String getDiskInfo() {
        int i;
        for (i = 0; i < diskInfo.lpaths.size(); i++) {
            diskInfo.lpaths.get(i);
        }
        return diskInfo.lpaths.get(i).toString();
    }

    public String getCPUInfo() {
        return
                "Processor Identity: " + cpuInfo.getProcID() +
                "\n Processor Architecture: " + cpuInfo.getProcArch() +
                "\n Processor Architecture (64/32): " + cpuInfo.getProcArch6432() +
                "\n Available CPU Logical Processors: " + cpuInfo.getLogicalProcessors() +
                "\n CPU Usage: " + cpuInfo.getCpuUsage();

    }

    public String getDisplayInfo() {
        return "Display Resolution: " + Toolkit.getDefaultToolkit().getScreenSize().toString();
    }


    public String getOSInfo() {
        return
                "Operating System Name: " + System.getProperty("os.name") +
                "\n Operating System Architecture: " + System.getProperty("os.arch") +
                "\n Operating System Version: " + System.getProperty("os.version") +
                "\n User Home Directory: " + System.getProperty("user.home") +
                "\n Logged In User: " + System.getProperty("user.name");
    }
// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public String getNetworkInfo() {
        try {
            return
                "Loopback Address: " + CInfo.getLoopbackAddress() +
                "\n Localhost Address: " + CInfo.getLocalHost() +
                "\n Public IP Address: " + CInfo.getPublicIP() +
                "\n Local IP Address: " + CInfo.getLocalIP() +
                "\n Hostname: " + CInfo.getCanonicalHostName() +
                "\n Hash Code: " + CInfo.gethashCode();

        } catch (Exception e) {
            return " Error: " + e.getStackTrace().toString();
        }
    }

    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(author.toString(), null, author.getAvatarUrl());
        //builder.setImage(CommandUtils.getScreenshot());
        builder.setTitle("DemiBOT -- System Information", null);
        builder.addField("Hardware Information", getHWInfo(), true);
        builder.addField("OS Information", getOSInfo(), true);
        builder.addField("Network Information", getNetworkInfo(), true);
        builder.setColor(Color.red);

        event.getChannel().sendMessage(builder.build()).queue();

    }
}
