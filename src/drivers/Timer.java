package drivers;

import log.StaticLogBase;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public class Timer extends StaticLogBase implements Runnable{

    private static final int TIMEOUT_DURATION = 10;
    private final AtomicBoolean running;
    private final AtomicBoolean countingDown;
    private TimeKeeper timeKeeper;

    public Timer() {
        running = new AtomicBoolean(false);
        countingDown = new AtomicBoolean(false);
        timeKeeper = null;
    }

    public void setTimeKeeper(TimeKeeper timeKeeper) {
        this.timeKeeper = timeKeeper;
    }

    @Override
    public void run() {
        running.set(true);
        while(running.get()) {
            while (!countingDown.get() && running.get()) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        log.info("INTERRUPT ON NON COUNTDOWN");
                    }
                }
            }
            while(countingDown.get() && running.get()) {
                synchronized (this) {
                    try {
                        log.info("TIMER STARTED");
                        long start = System.currentTimeMillis();
                        wait(Duration.ofSeconds(TIMEOUT_DURATION).toMillis());
                        long end = System.currentTimeMillis();
                        if (Duration.ofMillis(end - start).toSeconds() >= TIMEOUT_DURATION) {
                            if (timeKeeper != null) {
                                timeKeeper.notifyTimeout();
                            }
                        }
                    } catch (InterruptedException e) {
                        log.info("INTERRUPT ON COUNTDOWN");
                    }
                }
            }
        }
    }


    public synchronized void startTimer() {
        countingDown.set(false);
        notifyAll();
        countingDown.set(true);
        notifyAll();
    }

    public synchronized void end() {
        running.set(false);
        notifyAll();
    }
}
