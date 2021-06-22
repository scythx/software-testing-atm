package controller;

import static model.Constants.ACCOUNT_NOT_REGISTERED;
import static model.Constants.ACCOUNT_REGISTERED;
import static model.Constants.ADD_ACCOUNT_CANCELED;
import static model.Constants.UNBLOCK_ACCOUNT_CANCELED;
import static model.Constants.ADD_ACCOUNT_ERROR_INPUT_AMOUNT_ACC;
import static model.Constants.ADD_ACCOUNT_ERROR_INPUT_DIGIT_ACC;
import static model.Constants.ADD_ACCOUNT_ERROR_INPUT_AMOUNT_PIN;
import static model.Constants.ADD_ACCOUNT_ERROR_INPUT_DIGIT_PIN;
import static model.Constants.ADD_ACCOUNT_ERROR_INPUT_AMOUNT_AVBALANCE;
import static model.Constants.ADD_ACCOUNT_ERROR_INPUT_AMOUNT_DEPOSIT;
import static model.Constants.ADD_ACCOUNT_ERROR_INPUT_RANGE_AVBALANCE;
import static model.Constants.ADD_ACCOUNT_ERROR_INPUT_RANGE_DEPOSIT;
import static model.Constants.ADD_ACCOUNT_ERROR_INPUT_MENU;
import static model.Constants.ADD_ACCOUNT_SUCCESSFUL;
import static model.Constants.BLOCK_SUCCESSFUL;
import static model.Constants.VALIDATION_ADD_SUCCESSFULL;


import model.Account;
import model.AddAccount;
import model.Transaction;
import model.UnBlockAccount;
import view.Keypad;
import view.Screen;

public class AddAccountController extends TransactionController{
	private Account accAdded = new Account();
	private int statusAdd; //status transaction process
	
	public AddAccountController(Transaction theTransaction, Keypad theKeypad, Screen theScreen) {
		super(theTransaction, theKeypad, theScreen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		AddAccount transaction = (AddAccount) getTransaction();
		Screen screen = getScreen(); // get screen reference
		int statusProcess;
		 // loop while no valid choice has been made on unblock account option menu
		do {
			displayMenuOfAccounts();
			 while (statusAdd <= 0) {
				 switch(statusAdd) {
				 	case ADD_ACCOUNT_ERROR_INPUT_DIGIT_ACC:
				 		screen.displayMessageLine("The number of digit account number must be equal 4 digits.");
				 		break;
				 	case ADD_ACCOUNT_ERROR_INPUT_AMOUNT_ACC:
				 		screen.displayMessageLine("value account number not valid, must be positif value.");
				 		break;
				 	case ADD_ACCOUNT_ERROR_INPUT_MENU :
						screen.displayMessageLine("Invalid selection. Try again.");
						break;
				 }
				 displayMenuOfAccounts();
			 }
			 
			 transaction.setNewAccount(accAdded);
			 transaction.setStatusAdd(statusAdd);
			 statusProcess = transaction.execute();
			 switch (statusProcess) {
				case ACCOUNT_REGISTERED:
					screen.displayMessageLine("Account number not found.");
					break;
				case ADD_ACCOUNT_CANCELED :
					screen.displayMessageLine("You canceled transaction.");
					break;
				case ADD_ACCOUNT_SUCCESSFUL :
					screen.displayMessageLine("Account number has been been add.");
					break;	
			}
			}while(statusProcess!=ADD_ACCOUNT_CANCELED);				
	}

	private void displayMenuOfAccounts() {
		// TODO Auto-generated method stub
		Account inputSheet = new Account();
		
		 // display the add cash menu
		screen.displayMessageLine("\nAdd Account Menu:");
		screen.displayMessageLine("1 - Add Account Nasabah");
		screen.displayMessageLine("2 - Add Account Admin");
		screen.displayMessageLine("3 - Cancel Transaction");
		screen.displayMessage("\nChoose a Add account menu: ");
		
		 int input = keypad.getInput(); // get user input through keypad
		 // determine how to proceed based on the input value
		 switch (input) {
		 	case 1: // do add account number nasabah process
		 	case 2: // do add account number admin process	
		 		screen.displayMessage("Input account number:");		 		
				inputSheet.setAccountNumber(keypad.getInput()); // get user input through keypad
				
				// validation input account number
				statusAdd = inputAccountNumberValidation(inputSheet.getAccountNumber());
				if (statusAdd == VALIDATION_ADD_SUCCESSFULL) {
					screen.displayMessage("Input pin number:");		 		
					inputSheet.setPin(keypad.getInput()); // get user input through keypad
					
					// validation pin number
					statusAdd = pinNumberValidation(inputSheet.getPin());
					if (statusAdd == VALIDATION_ADD_SUCCESSFULL) {
						if(input==1) { // for add account nasabah process
							screen.displayMessage("Input available balance:");		 		
							inputSheet.setAvailableBalance(keypad.getInput()); // get user input through keypad
							
							// validation available balance
							statusAdd = availableBalanceValidation(inputSheet.getAvailableBalance());
							if (statusAdd == VALIDATION_ADD_SUCCESSFULL) {
								screen.displayMessage("Input deposit fund:");		 		
								inputSheet.setDepositFund(keypad.getInput()); // get user input through keypad
								
								// validation deposit fund
								statusAdd = depositFundValidation(inputSheet.getDepositFund());
								if (statusAdd == VALIDATION_ADD_SUCCESSFULL) {
									screen.displayMessage("Input description Account:");		 		
									inputSheet.setDesc(keypad.getInputString()); // get user input through keypad
									inputSheet.setRoleUser(2);
									accAdded = inputSheet;
								}
							}
						}else { // for add account admin
							screen.displayMessage("Input descirption Account:");		 		
							inputSheet.setDesc(keypad.getInputString()); // get user input through keypad
							inputSheet.setRoleUser(1);
							inputSheet.setDepositFund(0);
							inputSheet.setAvailableBalance(0);
							accAdded = inputSheet;
						}						
					}
				}
				
		 		break;       
		 	case ADD_ACCOUNT_CANCELED: // the user chose to cancel
		 		statusAdd = ADD_ACCOUNT_CANCELED; // save user's choice
		 		break;
		 	default: // the user did not enter a value from 1-2
		 		statusAdd = ADD_ACCOUNT_ERROR_INPUT_MENU;
			} 
	 }

	private int inputAccountNumberValidation(int inputAccNum) {
		int statusInput;
		
		if (!isValidAccountNumberDigit(inputAccNum)) {
			statusInput = ADD_ACCOUNT_ERROR_INPUT_DIGIT_ACC;
		}else if (!isValidAccountNumberValue(inputAccNum)) {
			statusInput = ADD_ACCOUNT_ERROR_INPUT_AMOUNT_ACC;
		}else {
			statusInput = VALIDATION_ADD_SUCCESSFULL;
		}
		
		return statusInput;
	}
	
	private int pinNumberValidation(int inputPin) {
		// TODO Auto-generated method stub
		int statusInput;
		
		if (!isValidAccountNumberDigit(inputPin)) {
			statusInput = ADD_ACCOUNT_ERROR_INPUT_DIGIT_PIN;
		}else if (!isValidAccountNumberValue(inputPin)) {
			statusInput = ADD_ACCOUNT_ERROR_INPUT_AMOUNT_PIN;
		}else {
			statusInput = VALIDATION_ADD_SUCCESSFULL;
		}
		return statusInput;
	}
	 
	private int availableBalanceValidation(double inputAvailableBalance) {
		// TODO Auto-generated method stub
		int statusInput;
		if (!isValidInputAmmount(inputAvailableBalance)) {
			statusInput = ADD_ACCOUNT_ERROR_INPUT_AMOUNT_AVBALANCE;
		}else if(!isValidInputRangeBalance(inputAvailableBalance)){
			statusInput = ADD_ACCOUNT_ERROR_INPUT_RANGE_AVBALANCE;
		}else {
			statusInput = VALIDATION_ADD_SUCCESSFULL;
		}		
		
		return statusInput;
	}

	private int depositFundValidation(double inputDepositFund) {
		// TODO Auto-generated method stub
		int statusInput;
		if (!isValidInputAmmount(inputDepositFund)) {
			statusInput = ADD_ACCOUNT_ERROR_INPUT_AMOUNT_DEPOSIT;
		}else if(!isValidInputRangeDeposit(inputDepositFund)){
			statusInput = ADD_ACCOUNT_ERROR_INPUT_RANGE_DEPOSIT;
		}else {
			statusInput = VALIDATION_ADD_SUCCESSFULL;
		}		
		return statusInput;
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
	
	private boolean isValidInputAmmount(double amount) {
			// TODO Auto-generated method stub
		if(amount>0) {
			return  true;
		}else {
			return false;
		}
	}
	
	private boolean isValidInputRangeBalance(double amount) {
		// TODO Auto-generated method stub
		if(amount>=100) {
			return  true;
		}else {
			return false;
		}
	}
	
	private boolean isValidInputRangeDeposit(double amount) {
		// TODO Auto-generated method stub
		if(amount>=500) {
			return  true;
		}else {
			return false;
		}
	}
}
