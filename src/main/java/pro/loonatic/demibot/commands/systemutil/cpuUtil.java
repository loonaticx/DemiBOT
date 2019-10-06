package pro.loonatic.demibot.commands.systemutil;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ThreadMXBean;

import static java.lang.management.ManagementFactory.getOperatingSystemMXBean;

public class cpuUtil {

    private int  availableProcessors = getOperatingSystemMXBean().getAvailableProcessors();
    private long lastSystemTime      = 0;
    private long lastProcessCpuTime  = 0;

    public int getLogicalProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public String getProcID() {
        return System.getenv("PROCESSOR_IDENTIFIER");
    }

    public String getProcArch() {
        return System.getenv("PROCESSOR_ARCHITECTURE");
    }

    public String getProcArch6432() {
        return System.getenv("PROCESSOR_ARCHITEW6432");
    }

    public synchronized double getCpuUsage() {
        if ( lastSystemTime == 0 ) {
            baselineCounters();
            return getCpuUsage();
        }

        long systemTime     = System.nanoTime();
        long processCpuTime = 0;

        if ( getOperatingSystemMXBean() instanceof OperatingSystemMXBean) {
            processCpuTime = ( (OperatingSystemMXBean) getOperatingSystemMXBean() ).getProcessCpuTime();
        }

        double cpuUsage = ((double)( processCpuTime - lastProcessCpuTime )) / ((double)( systemTime - lastSystemTime ));

        lastSystemTime     = systemTime;
        lastProcessCpuTime = processCpuTime;

        return cpuUsage / availableProcessors;
    }

    private void baselineCounters() {
        lastSystemTime = System.nanoTime();

        if ( getOperatingSystemMXBean() instanceof OperatingSystemMXBean ) {
            lastProcessCpuTime = ( (OperatingSystemMXBean) getOperatingSystemMXBean() ).getProcessCpuTime();
        }
    }
}
