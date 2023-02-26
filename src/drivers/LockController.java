package drivers;

public class LockController {
    /*
     *A lock interface which is surrounded by calls to lock and
     * unlock method
     */
    private boolean isLocked;

    public LockController() {
        this.isLocked = true;
    }

    public void engageLock() {
        if (this.isLocked) {
            System.out.println("The lock is already engaged.");
        } else {
            this.isLocked = true;
            System.out.println("The lock has been engaged.");
        }
    }

    public void disengageLock() {
        if (!this.isLocked) {
            System.out.println("The lock is already disengaged.");
        } else {
            this.isLocked = false;
            System.out.println("The lock has been disengaged.");
        }
    }

    public boolean isLocked() {
        return this.isLocked;
    }
}

