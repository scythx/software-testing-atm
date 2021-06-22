package controller;

import model.Transaction;
import view.Keypad;
import view.Screen;

public abstract class TransactionController {	
    Keypad keypad;
    Screen screen;
    private Transaction transaction;

    public TransactionController(Transaction theTransaction, Keypad theKeypad, Screen theScreen) {
        transaction = theTransaction;
        keypad = theKeypad;
        screen = theScreen;
    }

    public abstract void run(); // run transaction process

    /**
     * @return the keypad
     */
    public Keypad getKeypad() {
        return keypad;
    }

    /**
     * @param keypad the keypad to set
     */
    public void setKeypad(Keypad keypad) {
        this.keypad = keypad;
    }

    /**
     * @return the screen
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * @param screen the screen to set
     */
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    /**
     * @return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
