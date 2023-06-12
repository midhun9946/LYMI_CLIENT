import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Arrays;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.PowerSource;
import oshi.hardware.Sensors;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;


public class ServerInfoMining {
  public static void main(String[] args) {
  SystemInfo systemInfo = new SystemInfo();
  HardwareAbstractionLayer hardware = systemInfo.getHardware();
  Sensors sensors = hardware.getSensors();
  OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    // Get CPU utilization
    CentralProcessor processor = hardware.getProcessor();
    long[] prevTicks = processor.getSystemCpuLoadTicks();
    double cpuUtilization;
    while (true) {
      Util.sleep(5000); // Sleep for 1 second
      long[] ticks = processor.getSystemCpuLoadTicks();
      cpuUtilization = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100.0;
      prevTicks = ticks;
      System.out.println("CPU Utilization: " + String.format("%.2f", cpuUtilization) + "%");
      // Get CPU temperature
      double cpuTemperature = sensors.getCpuTemperature();
      System.out.println("CPU Temperature: " + cpuTemperature + "°C");

      // Get Memory usage
      long totalMemory = hardware.getMemory().getTotal();
      long availableMemory = hardware.getMemory().getAvailable();
      long usedMemory = totalMemory - availableMemory;
      double memoryUtilization = (double) usedMemory / totalMemory * 100.0;
      System.out.println("Memory Usage: " + FormatUtil.formatBytes(usedMemory) + " / " +
          FormatUtil.formatBytes(totalMemory) + " (" + String.format("%.2f", memoryUtilization) + "%)");

      // Get CPU Load Average
      double cpuLoadAverage = osBean.getSystemLoadAverage();
      System.out.println("CPU Load Average: " + cpuLoadAverage);

      // Get Partition-wise Memory Usage
      OSFileStore[] fileStores =
          systemInfo.getOperatingSystem().getFileSystem().getFileStores().toArray(new OSFileStore[0]);

      for (OSFileStore fileStore : fileStores) {
        long totalSpace = fileStore.getTotalSpace();
        long usableSpace = fileStore.getUsableSpace();
        long usedSpace = totalSpace - usableSpace;
        double partitionUtilization = (double) usedSpace / totalSpace * 100.0;
        if (partitionUtilization > 80) {
          System.out.println("Partition: " + fileStore.getName());
          System.out.println(
              "Usage: " + FormatUtil.formatBytes(usedSpace) + " / " + FormatUtil.formatBytes(totalSpace) + " (" + String.format("%.2f", partitionUtilization) + "%)");
        }
      }
      System.out.println();

      Util.sleep(3000); // Sleep for 3 seconds before the next iteration
    }
}
}

