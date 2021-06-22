package controller;

import static model.Constants.*;

import model.CashDispenser;
import view.Keypad;
import view.Screen;

public class CashDispenserController {
	 private CashDispenser cashDispenser; // ATM's cash dispenser
	 Keypad keypad;
	 Screen screen;
	    
	 public CashDispenserController(CashDispenser atmCashDispenser, Keypad theKeypad, Screen theScreen) {
		 cashDispenser = atmCashDispenser;
		 keypad = theKeypad;
		 screen = theScreen;
	 }
	 
	 public void displayCashDispenser() {
		 screen.displayMessageLine("\nTotal number of sheet is "+ cashDispenser.getCount());
		 screen.displayMessageLine("Total cash in dispenser is "+ 
		 cashDispenser.getTotalAmountCashDispenser() + " with nominal amount $" + NOMINAL_AMOUNT);
	 }
	 
	 public void addCashDispenser() {
		 int numberSheet = displayMenuOfAmounts();
		 
		 // loop while no valid choice has been made on add cash dispenser option menu
		 while (numberSheet <= 0) {
			 switch(numberSheet) {
			 	case ADD_CASHDISPENSER_ERROR_INPUT_AMOUNT:
			 		screen.displayMessageLine("value number of sheet not valid, must be positif value");
			 		break;
			 	case ADD_CASHDISPENSER_ERROR_COUNT_MAX:
			 		screen.displayMessageLine("the number of sheet cash dispenser has exceeded the maximum "
			 				+ "number limit total sheet\n total maximal number sheet is " + MAX_COUNT_CASHDISPINSER
							+ "with the current number of sheet cash dispenser is " + cashDispenser.getCount());
			 		break;
			 	case WITHDRAWAL_ERROR_INPUT_MENU :
					screen.displayMessageLine("Invalid selection. Try again.");
					break;
			 }
			 numberSheet = displayMenuOfAmounts();
		 }
		 
		 if(numberSheet != ADD_CASHDISPENSER_CANCELED) {
			 cashDispenser.addCount(numberSheet);
			 screen.displayMessageLine("Cash dispenser has been added");
		 }else {
			 screen.displayMessageLine("You canceled transaction");
		 }
		 
			 
	 }
	 
	 private int displayMenuOfAmounts() {
		 int userChoice = 0; // local variable to store return value
		 int inputSheet;
		      
		 // display the add cash menu
		 screen.displayMessageLine("\nAdd Cash Dispenser Menu:");
		 screen.displayMessageLine("1 - add amount");
		 screen.displayMessageLine("2 - Cancel transaction");
		 screen.displayMessage("\nChoose a add cash menu: ");

		 int input = keypad.getInput(); // get user input through keypad
		 // determine how to proceed based on the input value
		 switch (input) {
		 	case ADD_CASHDISPENSER_MENU: // do add cash amount in dispenser
		 		screen.displayMessage("Input number of sheet with nominal amount $" + NOMINAL_AMOUNT + ":");
				inputSheet = keypad.getInput(); // get user input through keypad
				if (!validAddCashAmount(inputSheet)) {
	        		userChoice = ADD_CASHDISPENSER_ERROR_INPUT_AMOUNT;
	        	}else if (!isMaxNumberSheetValid(inputSheet)) {
	        		userChoice = ADD_CASHDISPENSER_ERROR_COUNT_MAX;
				}else {
	        		userChoice = inputSheet;
	        	}
		 		break;       
		 	case ADD_CASHDISPENSER_CANCELED: // the user chose to cancel
		 		userChoice = ADD_CASHDISPENSER_CANCELED; // save user's choice
		 		break;
		 	default: // the user did not enter a value from 1-2
		 		userChoice = ADD_CASHDISPENSER_ERROR_INPUT_MENU;
			} 
		      //} 

			return userChoice; // return withdrawal amount or CANCELED
	 }
	 
	 private boolean validAddCashAmount(int sheetAmount) {
		 if(sheetAmount >= 0) {
			 return  true;
		 }else {
			 return false;
		 }
	}
	 
	private boolean isMaxNumberSheetValid(int sheetAmount){
		if((cashDispenser.getCount()+sheetAmount) < MAX_COUNT_CASHDISPINSER) {
			return true;
		}else {
			return false;
		}
			
	}
}
