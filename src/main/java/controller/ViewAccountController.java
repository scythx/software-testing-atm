package controller;

import model.Account;
import model.BalanceInquiry;
import model.BankDatabase;
import model.Transaction;
import model.ViewAccount;
import view.Keypad;
import view.Screen;

public class ViewAccountController extends TransactionController{

	public ViewAccountController(Transaction theTransaction, Keypad theKeypad, Screen theScreen) {
		super(theTransaction, theKeypad, theScreen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO View Account Number
		ViewAccount transaction = (ViewAccount) getTransaction();
		
		// get references to bank database and screen
	    BankDatabase bankDatabase = transaction.getBankDatabase();
	    Screen screen = getScreen();
	    Account[] dataAccount = bankDatabase.getDataAllAccount();

	      
	    // display the data account information on the screen
	    screen.displayMessageLine("\nAccount Number Information: " + dataAccount.length + " account");
	    screen.displayMessageLine("\n----------------------------------------:");
	    for (int idx = 0; idx<dataAccount.length; idx++) {
	    	screen.displayMessage("\n"+ (idx+1) + "- Account Number: " + dataAccount[idx].getAccountNumber());
	    	screen.displayMessage("\n - Role Account: " + dataAccount[idx].getRoleDescription());
	    	screen.displayMessage("\n - Description Account Number: " + dataAccount[idx].getDesc());
	    	screen.displayMessage("\n - Status Account Number: " + dataAccount[idx].getStatusDescription() + "\n");	
	    }
	    screen.displayMessageLine("\n----------------------------------------:");
	}

}
