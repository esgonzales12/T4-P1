package drivers;

import safe.SafeControllerImpl;

import java.util.List;

public class USB {

    private UsbDriver driver;
    private SafeControllerImpl controller;

    public USB(UsbDriver driver, SafeControllerImpl controller) {
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
    private class SafeUsbDriver implements UsbDriver {

        private boolean connected;
        private String connectedDeviceId;

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
               // controller.usbDeviceConnected(deviceId);

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
            // Not implemented
        }

        @Override
        public boolean write(List<String> lines) {
            // Not implemented
            // write the lines to the file opened in createFile
            return false;
        }

        public boolean isConnected() {
            return this.connected;
        }

        public void exportLogs(String filename) {
            // Allow logs to be exported via the USB connection while device is connected
            createFile(filename);
            write();
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
