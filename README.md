# Network Scanner

A Java command line tool that scans your local network and detects every connected device — showing their IP address, hostname, and online status. Outputs a clean report and saves it automatically to a text file.

---

## The Problem

When managing or troubleshooting a network you need to know what devices are connected. Doing this manually — checking each device one by one — is slow and inefficient. IT teams need a fast way to get a full picture of everything on the network.

## The Solution

Run one command and the tool does the rest. It loops through every possible IP address on your network, pings each one, resolves the hostname, and prints a full report. Everything gets saved to a timestamped file so you have a record of every scan.

---

## Demo

```
Scanning network: 10.0.0.0/24
This may take a minute...
..............................................................................................................................................................................................................................................................

====== Network Scan Report ======
IP                 Hostname                  Status
--------------------------------------------------
10.0.0.1           router.home               Online
10.0.0.170         DESKTOP-RICHARD           Online
10.0.0.45          LAPTOP-SARAH              Online
10.0.0.88          unknown                   Online
--------------------------------------------------
Devices online:  4
Scan completed:  2026-06-01_00-36
==================================================

Report saved to: scan_2026-06-01_00-36.txt
```

---

## How It Works

```
Start
  ↓
Loop through every IP in range (x.x.x.1 to x.x.x.254)
  ↓
Ping each IP using Java's InetAddress
  ↓
If online → resolve hostname
  ↓
Store as Device object in ArrayList
  ↓
Print report to terminal + save to .txt file
```

### Key concepts used
- Classes and objects — `Device` class stores IP, hostname, and status for each found device
- Constructors — each Device is created with its own data when discovered
- ArrayLists — dynamically stores all devices found during the scan
- For loops — iterates through all 254 possible IP addresses
- Methods — `isOnline()` pings each IP, `getHostname()` resolves the device name
- Exception handling — `try/catch` prevents crashes if a device doesn't respond
- File I/O — saves the scan report to a timestamped `.txt` file

---

## Getting Started

### Requirements
- Java 11 or higher
- Windows, Mac, or Linux

### Run it

```bash
# Step 1 — clone the repo
git clone https://github.com/RichardPancham/network-scanner.git
cd network-scanner

# Step 2 — find your network base IP
# Windows: open PowerShell and type ipconfig
# Look for IPv4 Address e.g. 10.0.0.170 — your base is 10.0.0

# Step 3 — update the network base in NetworkScanner.java
# Find this line and change it to match your network:
String networkBase = "10.0.0";

# Step 4 — compile
javac NetworkScanner.java

# Step 5 — run
java NetworkScanner
```

---

## Output

The tool prints the report to your terminal and saves it as a `.txt` file in the same folder:

```
scan_2026-06-01_00-36.txt
```

Each scan creates a new file with a timestamp so you can track network changes over time.

---

## Files

| File | Purpose |
|------|---------|
| `NetworkScanner.java` | Main program — scans network and generates report |
| `scan_[timestamp].txt` | Auto-generated scan report (created on each run) |

---

## Built With

- Java 17
- `java.net.InetAddress` — built-in Java networking library
- `java.io.FileWriter` — built-in Java file writing
- No external dependencies

---

## Author

**Richard Pancham** — [@RichardPancham](https://github.com/RichardPancham)

Built to practice Java fundamentals and demonstrate real-world IT networking concepts — device discovery, IP scanning, and automated reporting.

---

## License

MIT — free to use and modify.
