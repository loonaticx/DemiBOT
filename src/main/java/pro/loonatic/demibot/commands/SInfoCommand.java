package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;
import pro.loonatic.demibot.commands.systemutil.*;

import java.awt.*;
import java.io.File;
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
                " (*~" + Math.round(ramInfo.getTotalPRAMGB()) + " GB*)" +
                "\n Total Free Physical Memory: " +  ramInfo.getFreePRAM() + " MB" +
                " (*~" + Math.round(ramInfo.getFreePRAMGB()) + " GB*)\n";

    }

/* Get a list of all filesystem roots on this system */
    public String[] getDiskInfo() {
        File[] roots = File.listRoots();
        String[] list = new String[roots.length];

    /* For each filesystem root, print some info

    I could see this code getting revised, if product(gigabytes) more than 1024, amp it to a terabyte.
    If product(gigabytes) <0 (including like .256 for example)... do some math to make it show (kilobytes) instead.
    Probably would be done using a String.format expression, ternary, or heck idk lambada ??? >_<
    Either way, this'll do for now.
    ex: Usable space (%string%)...
     */
    for(int i = 0; i < list.length; i++) {
        list[i] = "File system root: " + roots[i].getAbsolutePath() +"\n";
        list[i] += "Total space (gigabytes): " + (String.format("%.3f",(double)roots[i].getTotalSpace()/(long)1073741824))+"\n";
        list[i] += "Free space (gigabytes): " + (String.format("%.3f",(double)roots[i].getFreeSpace()/(long)1073741824))+"\n";
        list[i] += "Usable space (gigabytes): " + (String.format("%.3f",(double)roots[i].getUsableSpace()/(long)1073741824))+"\n";
    }
    /*
    for (File root : roots) {
        list += "File system root: " + root.getAbsolutePath() +"\n";
        list += "Total space (gigabytes): " + (String.format("%.3f",(double)root.getTotalSpace()/(long)1073741824))+"\n";
        list += "Free space (gigabytes): " + (String.format("%.3f",(double)root.getFreeSpace()/(long)1073741824))+"\n";
        list += "Usable space (gigabytes): " + (String.format("%.3f",(double)root.getUsableSpace()/(long)1073741824))+"\n";
    }
    */

        return list;
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

    /*
    args.get(0) --> argument following the command,
    ;sysinfo storage
             ^^^^^^^
     */
    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(author.toString(), null, author.getAvatarUrl());

        /*
        Temporary fix, will probably end up using a map in the end.
        probs like builder.setTitle("DemiBOT -- "+map.get<string value>, null")
        so the map needs to hold a few things? arg command, function returning an array of pieces to be
        pasted on stuff like builder? :thonk:
         */
        boolean check = false;
        if(!(args.size() < 1)) {
            if (args.get(0).toLowerCase().equals("storage")) {
                builder.setTitle("DemiBOT -- Storage Information", null);
                String[] Driveinfo = getDiskInfo();

                for(int i = 0; i < Driveinfo.length; i++) {
                    builder.addField("Disk #"+i, Driveinfo[i], true);
                }

                //builder.addField("Disk Information", getDiskInfo(), true);
                check = true;
            }
        }
        if(!(check)) {
            builder.setTitle("DemiBOT -- General System Information", null);
            builder.addField("Hardware Information", getHWInfo(), true);
            builder.addField("OS Information", getOSInfo(), true);
            builder.addField("Network Information", getNetworkInfo(), true);
        }

        //builder.setImage(CommandUtils.getScreenshot());
        builder.setColor(Color.red);

        event.getChannel().sendMessage(builder.build()).queue();

    }
}
