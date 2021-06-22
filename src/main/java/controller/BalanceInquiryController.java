package controller;

import model.BalanceInquiry;
import model.BankDatabase;
import model.Transaction;
import view.Keypad;
import view.Screen;

public class BalanceInquiryController extends TransactionController{
	
	public BalanceInquiryController(Transaction theTransaction, Keypad theKeypad, Screen theScreen) {
		super(theTransaction, theKeypad, theScreen);
	}

	@Override
	public void run() {
		// TODO View Saldo Proccess
		BalanceInquiry transaction = (BalanceInquiry) getTransaction();
		
		// get references to bank database and screen
	    BankDatabase bankDatabase = transaction.getBankDatabase();
	    Screen screen = getScreen();
	    int accNo = transaction.getAccountNumber();

	    // get the available balance for the account involved
	    double availableBalance = bankDatabase.getAvailableBalance(accNo);

	    // get the total balance for the account involved
	    double totalBalance = bankDatabase.getTotalBalance(accNo);
	      
	    // display the balance information on the screen
	    screen.displayMessageLine("\nBalance Information:");
	    screen.displayMessage(" - Available balance: "); 
	    screen.displayDollarAmount(availableBalance);
	    screen.displayMessage("\n - Total balance:     ");
	    screen.displayDollarAmount(totalBalance);
	    screen.displayMessageLine("");
	}

}
