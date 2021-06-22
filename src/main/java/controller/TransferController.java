package controller;

import static model.Constants.*;

import model.Transaction;
import model.Transfer;
import model.Withdrawal;
import view.Keypad;
import view.Screen;

public class TransferController extends TransactionController{
	private int statusTransfer; //status transaction process
	private int destAccountNumber; // destination account number to transfer amount
	private int amount; // amount to transfer
	
	public TransferController(Transaction theTransaction, Keypad theKeypad, Screen theScreen) {
		super(theTransaction, theKeypad, theScreen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Transfer transaction = (Transfer) getTransaction();
		Screen screen = getScreen(); // get screen reference
		int statusProcess;
		
		// loop while no valid choice has been made on transfer option menu
		do {
			displayMenuOfAmounts();
			while (statusTransfer <= 0) {
				switch (statusTransfer) {
					case TRANSFER_ERROR_INPUT_AMOUNT: // cek amount bernilai negatif
						screen.displayMessageLine("value amount not valid, must be positif value");
						break;
					case TRANSFER_ERROR_RANGE_AMOUNT: // cek range value amount dengan batasan limit value
						screen.displayMessageLine("the nominal amount transfer does not match the valid range\n"
								+ "the transaction must be with minimum transaction is $" + MIN_TRANSFER_AMOUNT + " and\n"
								+ "maximum transaction is $" + MAX_TRANSFER_AMOUNT);
						break;
					case TRANSFER_ERROR_INPUT_MENU: // cek input menu tidak valid
						screen.displayMessageLine("Invalid selection. Try again.");
						break;
				}   
				displayMenuOfAmounts();
			}
			
			transaction.setAmount(amount);
			transaction.setDestAccountNumber(destAccountNumber);
			transaction.setStatusTransfer(statusTransfer);
			statusProcess = transaction.execute();
			switch (statusProcess) {
				case BALANCE_NOT_ENOUGH  :
					screen.displayMessageLine("Your balance isn't enough for this transfer.");
					break;
				//case SAME_ACCOUNT_NUMBER :
					//screen.displayMessageLine("Destination account same with sending account, "
						//	+ "must be difference account number");
					//break;
				case ACCOUNT_NOT_REGISTERED:
					screen.displayMessageLine("Destinatination account tidak ditemukan");
					break;
				case TRANSFER_ACCOUNT_BLOCKED:
					screen.displayMessageLine("Destination account has been blocked");
					break;	
				case TRANSFER_CANCELED :
					screen.displayMessageLine("You canceled transaction");
					break;
				case TRANSFER_SUCCESSFUL :
					screen.displayMessageLine("Your cash has been transfered.");
					break;	
			}
		}while(statusProcess!=TRANSFER_CANCELED);
	}
	
	// display a menu of transfer amounts and the option to cancel;
	private void displayMenuOfAmounts() {
		int inputMenu, inputAmount, destAccount;
		
		this.statusTransfer    = 0;
		this.destAccountNumber = 0;
		this.amount            = 0;
		
		Screen screen = getScreen(); // get screen reference
		
		// display the transfer menu
		screen.displayMessageLine("\nTransfer Menu:");
		screen.displayMessageLine("1 - input transaction");
		screen.displayMessageLine("2 - Cancel transaction");
		
		screen.displayMessage("\nChoose a transfer amount menu: ");
		inputMenu = keypad.getInput(); // get user input through keypad
		
		// determine how to proceed based on the input value
		switch (inputMenu) {
			case 1: // do transfer amount
				screen.displayMessage("Input transfer amount: ");
				inputAmount = keypad.getInput(); // get user input through keypad
				
				screen.displayMessage("Input a destination account number: ");
				destAccount = keypad.getInput(); // get user input through keypad
				
	        	if (inputAmount <= 0) {
	        		statusTransfer = TRANSFER_ERROR_INPUT_AMOUNT;
	        		
	        	}else if (!rangeValidTransferAmount(inputAmount)) {
	        		statusTransfer = TRANSFER_ERROR_RANGE_AMOUNT;
	        	}else {
	        		destAccountNumber = destAccount;
	        		amount 	       = inputAmount;
	        		statusTransfer    = VALIDATION_TRANSFER_SUCCESSFULL;
	        	}
	        	break;      
			case TRANSFER_CANCELED: // the user chose to cancel
				statusTransfer = TRANSFER_CANCELED; // save user's choice
				break;
			default: // the user did not enter a value from 1-2
				statusTransfer = TRANSFER_ERROR_INPUT_MENU;
		}  
	}
	
	private boolean rangeValidTransferAmount(int amount) {
		if(amount>= MIN_TRANSFER_AMOUNT && amount<= MAX_TRANSFER_AMOUNT) {
			return  true;
		}else {
			return false;
		}
	}
}
