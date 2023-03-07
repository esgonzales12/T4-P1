package drivers.impl;

import drivers.UsbDriver;
import log.StaticLogBase;
import safe.SafeController;
import safe.enums.State;

import java.io.*;
import java.util.List;

public class UsbDriverImpl extends StaticLogBase implements UsbDriver {
    private FileOutputStream outputStream;
    private SafeController safeController;

    public UsbDriverImpl(SafeController safeController) {
        this.safeController = safeController;
        outputStream = null;
    }

    @Override
    public void connect() {
        // Detect USB device connection
        if (safeController != null) {
            log.info("HANDLING CONNECT");
            safeController.handleStateChangeRequest(State.EXPORT);
            return;
        }
        log.info("SAFE CONTROLLER IS NULL");
    }

    @Override
    public void disconnect() {
        // Send signal to SafeController
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.info("ERROR CLOSING OUTPUT STREAM");
            }
        }
    }

    @Override
    public void createFile(String filename) {
        // handle case where file is open already
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.info("COULDN'T CLOSE OPEN STREAM");
            }
        }

        try {
            outputStream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            log.info("UNABLE TO OPEN OUTPUT STREAM FOR FILE: " + filename);
        }
    }

    @Override
    public boolean write(List<String> lines) {
        // write the lines to the file opened in createFile
        // handle the case where outputStream is null
        if (outputStream == null) {
            log.info("NO FILE OPEN");
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
            log.info("ERROR WRITING TO FILE");
            return false;
        }
        return true;
    }
}
