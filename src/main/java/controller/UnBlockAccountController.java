package controller;

import model.UnBlockAccount;

import static model.Constants.ACCOUNT_NOT_REGISTERED;
import static model.Constants.UNBLOCK_ACCOUNT_CANCELED;
import static model.Constants.UNBLOCK_ACCOUNT_ERROR_INPUT_AMOUNT;
import static model.Constants.UNBLOCK_ACCOUNT_ERROR_INPUT_DIGIT;
import static model.Constants.UNBLOCK_ACCOUNT_ERROR_INPUT_MENU;
import static model.Constants.UNBLOCK_SUCCESSFUL;
import static model.Constants.VALIDATION_UNBLOCK_SUCCESSFULL;

import model.Transaction;
import view.Keypad;
import view.Screen;

public class UnBlockAccountController extends TransactionController{
	private int statusBlock; //status transaction process
	private int accUnBlock; // account number which blocked

	public UnBlockAccountController(Transaction theTransaction, Keypad theKeypad, Screen theScreen) {
		super(theTransaction, theKeypad, theScreen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		UnBlockAccount transaction = (UnBlockAccount) getTransaction();
		Screen screen = getScreen(); // get screen reference
		int statusProcess;
		
		 // loop while no valid choice has been made on unblock account option menu
		do {
			displayMenuOfAccounts();
			 while (statusBlock <= 0) {
				 switch(statusBlock) {
				 	case UNBLOCK_ACCOUNT_ERROR_INPUT_DIGIT:
				 		screen.displayMessageLine("The number of digit account number must be equal 4 digits.");
				 		break;
				 	case UNBLOCK_ACCOUNT_ERROR_INPUT_AMOUNT:
				 		screen.displayMessageLine("value account number not valid, must be positif value.");
				 		break;
				 	case UNBLOCK_ACCOUNT_ERROR_INPUT_MENU :
						screen.displayMessageLine("Invalid selection. Try again.");
						break;
				 }
				 displayMenuOfAccounts();
			 }
			 
			 transaction.setAccNumUnBlock(accUnBlock);
			 transaction.setStatusBlock(statusBlock);
			 statusProcess = transaction.execute();
			 switch (statusProcess) {
					case ACCOUNT_NOT_REGISTERED:
						screen.displayMessageLine("Account number not found.");
						break;
					case UNBLOCK_ACCOUNT_CANCELED :
						screen.displayMessageLine("You canceled transaction.");
						break;
					case UNBLOCK_SUCCESSFUL :
						screen.displayMessageLine("Account number has been active.");
						break;	
				}
			}while(statusProcess!=UNBLOCK_ACCOUNT_CANCELED);		
		
	}

	private void displayMenuOfAccounts() {
		// TODO Auto-generated method stub
		 int inputSheet;
		 statusBlock = 0;
		 accUnBlock = 0;
		      
		 // display the add cash menu
		 screen.displayMessageLine("\nUnblock Account Menu:");
		 screen.displayMessageLine("1 - Input Transaction");
		 screen.displayMessageLine("2 - Cancel Transaction");
		 screen.displayMessage("\nChoose a block account menu: ");

		 int input = keypad.getInput(); // get user input through keypad
		 // determine how to proceed based on the input value
		 switch (input) {
		 	case 1: // do unblock account number process
		 		screen.displayMessage("Input account number:");
				inputSheet = keypad.getInput(); // get user input through keypad
				if (!isValidAccountNumberDigit(inputSheet)) {
					statusBlock = UNBLOCK_ACCOUNT_ERROR_INPUT_DIGIT;
	        	}else if (!isValidAccountNumberValue(inputSheet)) {
	        		statusBlock = UNBLOCK_ACCOUNT_ERROR_INPUT_AMOUNT;
				}else {
					statusBlock = VALIDATION_UNBLOCK_SUCCESSFULL;
					accUnBlock = inputSheet;
	        	}
		 		break;       
		 	case UNBLOCK_ACCOUNT_CANCELED: // the user chose to cancel
		 		statusBlock = UNBLOCK_ACCOUNT_CANCELED; // save user's choice
		 		break;
		 	default: // the user did not enter a value from 1-2
		 		statusBlock = UNBLOCK_ACCOUNT_ERROR_INPUT_MENU;
			} 
	 }
	
	 private boolean isValidAccountNumberValue(int AccountNumber) {
		 if(AccountNumber >= 0000) {
			 return  true;
		 }else {
			 return false;
		 }
	}
	 
	 private boolean isValidAccountNumberDigit(int AccountNumber) {
		 int digitValue = 0;
		 
		 while (AccountNumber>0) {
			 AccountNumber = AccountNumber / 10;
			 digitValue = digitValue + 1;
		 }
		 if(digitValue == 4) {
			 return  true;
		 }else {
			 return true;
		 }
	}

}
