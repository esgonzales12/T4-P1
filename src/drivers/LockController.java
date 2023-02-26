package drivers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockController {
    /*
     *A lock interface which is surrounded by calls to lock and
     * unlock method
     */
    private static Lock lock = new ReentrantLock();

    public void disengage() {
        lock.lock();
        try {
            System.out.println("LOCKED"); // I will need to implement the logic
        } finally {
            lock.unlock();
        }
    }
}
