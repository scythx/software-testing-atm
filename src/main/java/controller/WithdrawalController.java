package controller;

import static model.Constants.*;

import model.Transaction;
import model.Withdrawal;
import view.Keypad;
import view.Screen;

public class WithdrawalController extends TransactionController{

	public WithdrawalController(Transaction theTransaction, Keypad theKeypad, Screen theScreen) {
		super(theTransaction, theKeypad, theScreen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO withdraw nominal from saldo account process 
		Withdrawal transaction = (Withdrawal) getTransaction();
		Screen screen = getScreen(); // get screen reference
		int idxamount;
		int statusProcess;   

		// loop while no valid choice has been made on withdrawal option menu
		do {
			idxamount = displayMenuOfAmounts();
			while (idxamount <= 0) {
				switch (idxamount) {
					case WITHDRAWAL_ERROR_INPUT_AMOUNT :
						screen.displayMessageLine("value amount not a multiple of 20.");
						break;
					case WITHDRAWAL_ERROR_RANGE_AMOUNT :
						screen.displayMessageLine("the nominal amount withdrawal does not match the valid range\n"
								+ "the transaction must be with minimum transaction is $" + MIN_WITHDRAW_AMOUNT + " and\n"
								+ "maximum transaction is $" + MAX_WITHDRAW_AMOUNT);
						break;
					case WITHDRAWAL_ERROR_INPUT_MENU :
						screen.displayMessageLine("Invalid selection. Try again.");
						break;
						
				}   
			   idxamount = displayMenuOfAmounts();
			}
			
			transaction.setAmount(idxamount);
			statusProcess = transaction.execute();
			switch (statusProcess) {
				case CASHDISPENSER_NOT_ENOUGH :
					screen.displayMessageLine("Cash dispenser doesn't have sufficient amount of cash.");
					break;
				case BALANCE_NOT_ENOUGH :
					screen.displayMessageLine("Your balance isn't enough for this withdrawal.");
					break;
				case WITHDRAWAL_CANCELED :
					screen.displayMessageLine("You canceled transaction");
					break;
				case WITHDRAW_SUCCESSFUL :
					screen.displayMessageLine("Your cash has been dispensed. Please take your cash now.");
					break;
					
			}
		}while(statusProcess!=WITHDRAWAL_CANCELED);
	}
	
	// display a menu of withdrawal amounts and the option to cancel;
	// return the chosen amount or 0 if the user chooses to cancel
	private int displayMenuOfAmounts() {
		int userChoice = 0; // local variable to store return value

		Screen screen = getScreen(); // get screen reference
	      
		// array of amounts to correspond to menu numbers
		int[] amounts = {0, 20, 40, 60, 100, 200};

		// loop while no valid choice has been made
		//while (userChoice == 0) {
		// display the withdrawal menu
		screen.displayMessageLine("\nWithdrawal Menu:");
		screen.displayMessageLine("1 - $20");
		screen.displayMessageLine("2 - $40");
		screen.displayMessageLine("3 - $60");
		screen.displayMessageLine("4 - $100");
		screen.displayMessageLine("5 - $200");
		screen.displayMessageLine("6 - Other amount");
		screen.displayMessageLine("7 - Cancel transaction");
		screen.displayMessage("\nChoose a withdrawal amount: ");

		int input = keypad.getInput(); // get user input through keypad
		// determine how to proceed based on the input value
		switch (input) {
			case 1: // if the user chose a withdrawal amount 
			case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
			case 3: // corresponding amount from amounts array
			case 4:
			case 5:
				userChoice = amounts[input]; // save user's choice
				break;       
	        case WITHDRAWAL_OTHER_AMOUNT: 
	        	screen.displayMessageLine("\ninput other amount in multiples of 20 :");
	        	input = keypad.getInput();
	        	if (!rangeValidWithdrawAmount(input)) {
	        		userChoice = WITHDRAWAL_ERROR_RANGE_AMOUNT;
	        	}else if (!validWithdrawAmount(input)) {
	        		userChoice = WITHDRAWAL_ERROR_INPUT_AMOUNT;
	        	}else {
	        		userChoice = input;
	        	}
	        	break;
	        case WITHDRAWAL_CANCELED: // the user chose to cancel
	        	userChoice = WITHDRAWAL_CANCELED; // save user's choice
	        	break;
	        default: // the user did not enter a value from 1-7
	        	userChoice = WITHDRAWAL_ERROR_INPUT_MENU;
		} 
	      //} 

		return userChoice; // return withdrawal amount or CANCELED
	}
	
	private boolean rangeValidWithdrawAmount(int amount) {
		if(amount> MIN_WITHDRAW_AMOUNT && amount< MAX_WITHDRAW_AMOUNT) {
			return  true;
		}else {
			return false;
		}
	}
	
	private boolean validWithdrawAmount(int amount) {
		if(amount % 20 == 0) {
			return  true;
		}else {
			return false;
		}
	}
}
