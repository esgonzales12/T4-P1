package drivers;


public interface UsbDriver {

    void connect();
    void disconnect();
    void createFile(String filename);
    boolean write();
}
