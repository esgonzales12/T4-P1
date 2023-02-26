package dao;

import dao.domain.LogRecord;
import log.StaticLogBase;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SecurityDao extends StaticLogBase {
    private static final String LOG_STORAGE_LOCATION = "src/dao/data/logRecords.txt";
    private static final String ENCRYPTION_KEY_STORAGE_LOCATION = "src/dao/data/encryptionKey.txt";
    public void save(LogRecord logRecord) {
        try (Writer writer =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     new FileOutputStream(LOG_STORAGE_LOCATION),
                                     StandardCharsets.UTF_8))) {
            writer.write(logRecord.toString());
        } catch (IOException e) {
            log.severe("ERROR SAVING LOG RECORD");
            log.severe(e.getMessage());
        }
    }

    public List<LogRecord> getRecords() {
        List<LogRecord> res = new ArrayList<>();
        try (BufferedReader reader
                     = new BufferedReader(
                             new FileReader(LOG_STORAGE_LOCATION))){
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String [] temp = currentLine.split(",");
                res.add(new LogRecord.Builder()
                        .setUsername(temp[0])
                        .setOperation(temp[1])
                        .setDateTime(temp[2])
                        .build());
            }
        } catch (IOException e) {
            log.severe("ERROR READING LOG RECORDS");
            log.severe(e.getMessage());
        }
        return res;
    }

    public String getEncryptionKey() {
        String res = null;
        try (BufferedReader reader
                     = new BufferedReader(
                            new FileReader(ENCRYPTION_KEY_STORAGE_LOCATION))){
            res = reader.readLine();
        } catch (IOException e) {
            log.severe("ERROR READING ENCRYPTION KEY");
            log.severe(e.getMessage());
        }
        return res;
    }
}
