package model;

import view.Keypad;
import view.Screen;

public class Deposit extends Transaction {
	  private double amount; // amount to deposit
	  private Keypad keypad; // reference to keypad
	  private DepositSlot depositSlot; // reference to deposit slot
	  private final static int CANCELED = 0; // constant for cancel option
	
	//  Deposit constructor	  
   public Deposit(int userAccountNumber, /*Screen atmScreen,*/ 
      BankDatabase atmBankDatabase, /*Keypad atmKeypad,*/ 
      DepositSlot atmDepositSlot) {

      // initialize superclass variables
      super(userAccountNumber, atmBankDatabase);
      //keypad = atmKeypad;
      depositSlot = atmDepositSlot;

   } 

   // perform transaction
   @Override
   public int execute() {
	   return 0;
   }
//
//   // prompt user to enter a deposit amount in cents 
//   private double promptForDepositAmount() {
//      Screen screen = getScreen(); // get reference to screen
//      int input = -1;
//      
//      while (input<0) {
//	      // display the prompt
//	      screen.displayMessage("\nPlease enter a deposit amount in " + 
//	         "CENTS (or 0 to cancel): ");
//	      input = keypad.getInput(); // receive input of deposit amount
//      }
//      // check whether the user canceled or entered a valid amount
//      if (input == CANCELED) {
//         return CANCELED;
//      }
//      else {
//         return (double) input / 100; // return dollar amount
//      }
//   }
} 
