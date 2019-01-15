package pro.loonatic.demibot.commands.systemutil;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class diskUtil {

    private int mb = 1024*1024;
    private static int gb = 1024*1024*1024;

    public static void main(String[] args) {
        System.out.println(lpaths);
        System.out.println(DiskPartitions);
    }

    public static Map<String, File> lpaths = new HashMap<String, File>();
    public static ArrayList<String> DiskPartitions = new ArrayList<String>();

        static {
            try {
                NumberFormat formatter = new DecimalFormat("#0.000");
                File[] paths;
                FileSystemView fsv = FileSystemView.getFileSystemView();
                paths = File.listRoots();
                for (File path : paths) {
                    addDisk(path.toString(), path);
                    DiskPartitions.add(formatter.format((double)path.getFreeSpace() / gb));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public static void addDisk(String diskName, File file) {
        lpaths.put(diskName, file);
    }

}
