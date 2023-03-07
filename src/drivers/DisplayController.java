package drivers;

import javafx.scene.paint.Color;
import safe.SafeController;


public class DisplayController {
    // Constants for the different signal types
    public static final int BRIEF_FLASH = 1;
    public static final int EXTENDED_PERIOD = 2;
    public static final int REPEATED_FLASHING = 3;

    // Instance variables
    private Color currentColor;
    private boolean isBacklightOn;
    private char lowerChar;
    private String upperPrompt;
    private SafeController safeController;

    // Constructor
    public DisplayController(SafeController safeController) {
        this.currentColor = Color.WHITE;
        this.isBacklightOn = true;
        this.lowerChar = ' ';
        this.upperPrompt = "";
        this.safeController = safeController;
    }

    // Signal function
    public void signal(Color color, int signalType) {
        this.currentColor = color;

        switch (signalType) {
            case BRIEF_FLASH:
                // code to turn on the light bar for a brief flash
                break;
            case EXTENDED_PERIOD:
                // code to turn on the light bar for an extended period
                break;
            case REPEATED_FLASHING:
                // code to turn on the light bar for repeated flashing
                break;
            default:
                System.out.println("Invalid signal type.");
        }
    }

    // Clear function
    public void clear() {
        this.lowerChar = ' ';
        this.upperPrompt = "";
    }

    // Sleep function
    public void sleep() {
        this.isBacklightOn = false;
    }

    // Display input function
    public void displayInput(char letter) {
        this.lowerChar = letter;
    }

    // Display prompt function
    public void displayPrompt(String prompt) {
        this.upperPrompt = prompt;
    }


}