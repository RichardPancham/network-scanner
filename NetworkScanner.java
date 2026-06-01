import java.net.InetAddress;
import java.io.FileWriter;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// This is our main class — in Java everything lives inside a class
public class NetworkScanner {

    // A Device is a blueprint for storing info about each device we find
    // Think of it like a row in a spreadsheet — IP, hostname, status
    static class Device {
        String ip;
        String hostname;
        boolean online;

        // Constructor — runs when we create a new Device
        Device(String ip, String hostname, boolean online) {
            this.ip = ip;
            this.hostname = hostname;
            this.online = online;
        }
    }

    // This method pings one IP address and returns true if it responds
    // InetAddress is built into Java — no libraries needed
    static boolean isOnline(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            // getByName looks up the IP, isReachable pings it
            // 1000 = timeout in milliseconds (1 second)
            return address.isReachable(1000);
        } catch (Exception e) {
            return false; // if anything goes wrong, assume offline
        }
    }

    // This method gets the device name (hostname) from its IP
    static String getHostname(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            String hostname = address.getCanonicalHostName();
            // If it just returns the IP back, we don't know the name
            if (hostname.equals(ip)) return "unknown";
            return hostname;
        } catch (Exception e) {
            return "unknown";
        }
    }

    // Main method — this is where Java always starts running
    public static void main(String[] args) throws Exception {

        // Your network base — the first 3 parts of your IP
        // To find yours: open PowerShell and type ipconfig
        // Look for IPv4 Address e.g. 192.168.1.5 — base is 192.168.1
        String networkBase = "10.0.0";

        // We'll store every device we find in this list
        ArrayList<Device> devices = new ArrayList<>();

        System.out.println("Scanning network: " + networkBase + ".0/24");
        System.out.println("This may take a minute...\n");

        // Loop through every possible IP from .1 to .254
        // i is the last number of the IP address
        for (int i = 1; i <= 254; i++) {
            String ip = networkBase + "." + i;

            // Print a dot for each IP checked so user sees progress
            System.out.print(".");

            boolean online = isOnline(ip);

            // Only look up hostname if device is online (saves time)
            String hostname = online ? getHostname(ip) : "offline";

            // Add this device to our list
            devices.add(new Device(ip, hostname, online));
        }

        System.out.println("\n");

        // Count how many are online
        long onlineCount = devices.stream().filter(d -> d.online).count();

        // Get current date and time for the report filename
        String timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        String filename = "scan_" + timestamp + ".txt";

        // Build the report — StringBuilder is efficient for building strings
        StringBuilder report = new StringBuilder();
        report.append("====== Network Scan Report ======\n");
        report.append(String.format("%-18s %-25s %s%n", "IP", "Hostname", "Status"));
        report.append("-".repeat(50) + "\n");

        // Loop through every device and add it to the report
        for (Device d : devices) {
            if (d.online) { // only show online devices
                report.append(String.format("%-18s %-25s %s%n",
                    d.ip, d.hostname, "Online"));
            }
        }

        report.append("-".repeat(50) + "\n");
        report.append("Devices online:  " + onlineCount + "\n");
        report.append("Scan completed:  " + timestamp + "\n");
        report.append("=".repeat(50) + "\n");

        // Print report to terminal
        System.out.println(report.toString());

        // Save report to a .txt file
        FileWriter writer = new FileWriter(filename);
        writer.write(report.toString());
        writer.close();

        System.out.println("Report saved to: " + filename);
    }
}