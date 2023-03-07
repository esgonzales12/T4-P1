package drivers;

import safe.SafeController;
import safe.SafeControllerImpl;
import safe.enums.State;

import java.io.*;
import java.util.List;

public class USB {

    private UsbDriver driver;
    private static SafeController controller;

    public USB(UsbDriver driver, SafeController controller) {
        this.driver = driver;
        this.controller = controller;
    }

    public void connect() {
        driver.connect();
    }

    public void disconnect() {
        driver.disconnect();
    }

    public void exportLogs(String filename) {
        if (driver instanceof SafeUsbDriver && ((SafeUsbDriver) driver).isConnected()) {
            // Allow logs to be exported via the USB connection while device is connected
            ((SafeUsbDriver) driver).exportLogs(filename);
        }
    }

    // Inner class implementing UsbDriver interface
    public static class SafeUsbDriver implements UsbDriver {

        private boolean connected;
        private String connectedDeviceId;
        private FileOutputStream outputStream;

        public SafeUsbDriver() {
            this.connected = false;
            this.connectedDeviceId = null;
        }

        @Override
        public void connect() {
            // Detect USB device connection
            if (isUsbDeviceConnected()) {
                // Get device ID
                String deviceId = getConnectedDeviceId();

                // Send signal to SafeController
                controller.handleStateChangeRequest(State.EXPORT);

                // Set connected flag and device ID
                this.connected = true;
                this.connectedDeviceId = deviceId;
            }
        }

        @Override
        public void disconnect() {
            // Send signal to SafeController
            if (this.connected) {
             //   controller.usbDeviceDisconnected(this.connectedDeviceId);
                this.connected = false;
                this.connectedDeviceId = null;
            }
        }

        @Override
        public void createFile(String filename) {
            // handle case where file is open already
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println("Error: closing existing file");
                }
            }

            try {
                outputStream = new FileOutputStream(filename);
            } catch (FileNotFoundException e) {
                // handle this without throwing error
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean write(List<String> lines) {
            // write the lines to the file opened in createFile
            // handle the case where outputStream is null
            if (outputStream == null) {
                System.out.println("ERROR: outputStream is null");
                return false;
            }

            try {
                Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                for (String str: lines) {
                    writer.write(str + "\n");
                }
                writer.close();
                outputStream.close();
                outputStream = null;  // make sure outputStream is null after writing is complete
            } catch (Exception e) {
                System.out.println("ERROR WRITING FILE");
                return false;
            }
            return true;
        }

        public boolean isConnected() {
            return this.connected;
        }

        public void exportLogs(String filename) {
            // Allow logs to be exported via the USB connection while device is connected
            createFile(filename);
//            write();
        }

        private boolean isUsbDeviceConnected() {
            // Check if USB device is connected to safe's USB port
            // Return true if connected, false otherwise
            return false;
        }

        private String getConnectedDeviceId() {
            // Get ID of connected USB device
            // Return device ID as String
            return "";
        }
    }
}
