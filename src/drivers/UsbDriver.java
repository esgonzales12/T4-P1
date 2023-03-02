package drivers;


import java.util.List;

public interface UsbDriver {

    void connect();
    void disconnect();
    void createFile(String filename);
    boolean write(List<String> lines);
}
