package pro.loonatic.demibot.commands.systemutil;

public class ramUtil {
    private com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean)
            java.lang.management.ManagementFactory.getOperatingSystemMXBean();

    private int mb = 1024*1024;
    private int gb = 1024*1024*1024;

    public long getTotalPRAM() {
        return os.getTotalPhysicalMemorySize() / mb;
    }
    public double getTotalPRAMGB() {
        return os.getTotalPhysicalMemorySize() / gb;
    }

    public long getFreePRAM() {
        return os.getFreePhysicalMemorySize() / mb;
    }

    public double getFreePRAMGB() {
        return os.getFreePhysicalMemorySize() / gb;
    }
}
