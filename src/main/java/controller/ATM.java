package controller;

import static model.Constants.*;

import model.AddAccount;
import model.BalanceInquiry;
import model.BankDatabase;
import model.BlockAccount;
import model.UnBlockAccount;
import model.CashDispenser;
import model.Deposit;
import model.DepositSlot;
import model.Transaction;
import model.Transfer;
import model.ViewAccount;
import model.Withdrawal;
import view.Keypad;
import view.Screen;

public class ATM {
   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser

   private BankDatabase bankDatabase; // account information database
   private DepositSlot depositSlot; // deposit atm slot


   // no-argument ATM constructor initializes instance variables
   public ATM() {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad 
      cashDispenser = new CashDispenser(); // create cash dispenser
      bankDatabase = new BankDatabase(); // create acct info database
   }

   // start ATM 
   public void run() {
      // welcome and authenticate user; perform transactions
      while (true) {
         // loop while user is not yet authenticated
         while (!userAuthenticated) {
            screen.displayMessageLine("\nWelcome!");       
            authenticateUser(); // authenticate user
         }
         switch (bankDatabase.getRoleAccount(currentAccountNumber)) {
         	case ROLE_ADMIN : // menu admin
         		performTransactionsAdmin();
         		break;
         	case ROLE_INDIVIDUAL_CUSTOMER : // menu nasabah perorangan
         		performTransactionsCustomer(); // user is now authenticated
         		break;
         	case ROLE_COMPANY_CUSTOMER : // menu nasabah perusahaan
         		performTransactionsCustomer(); // user is now authenticated
         		break;
         }
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         screen.displayMessageLine("\nThank you! Goodbye!");
      }
   }

   // attempts to authenticate user against database
   private void authenticateUser() {
      screen.displayMessage("\nPlease enter your account number: ");
      int accountNumber = keypad.getInput(); // input account number
      screen.displayMessage("\nEnter your PIN: "); // prompt for PIN
      int pin = keypad.getInput(); // input PIN
      
      // set userAuthenticated to boolean value returned by database
      userAuthenticated = 
         bankDatabase.authenticateUser(accountNumber, pin);
      
      // check whether authentication succeeded
      if (userAuthenticated) {
         currentAccountNumber = accountNumber; // save user's account #
      } 
      else {
         screen.displayMessageLine(
            "Invalid account number or PIN. Please try again.");
      } 
   } 

   // display the main menu and perform transactions for admin
   private void performTransactionsAdmin() {
	   // local variable to store transaction currently being processed
	   Transaction currentTransaction = null;
	   TransactionController currentTransactionController = null;
	   
	   boolean userExited = false; // user has not chosen to exit
	   // cashdispenser
	   CashDispenserController currentDispenserController = new CashDispenserController(cashDispenser, keypad, screen);

	   // loop while user has not chosen option to exit system
	   while (!userExited) {
		   // show main menu and get user selection
		   int mainMenuSelection = displayMainMenuAdmin();
		   
		   switch(mainMenuSelection) {
		   		case UNBLOCK_ACCOUNT_MENU:
		   			currentTransaction = createTransactionAdmin(mainMenuSelection);
		   			currentTransactionController = new UnBlockAccountController(currentTransaction, keypad, screen);
	                currentTransactionController.run(); // execute transaction
		   			break;
		   		case BLOCK_ACCOUNT_MENU:
		   			currentTransaction = createTransactionAdmin(mainMenuSelection);
		   			currentTransactionController = new BlockAccountController(currentTransaction, keypad, screen);
	                currentTransactionController.run(); // execute transaction
		   			break;
		   		case VIEW_CASHDISPENSER_MENU :
		   			currentDispenserController.displayCashDispenser();
		   			break;
		   		case ADD_CASHDISPENSER_MENU :
		   			currentDispenserController.addCashDispenser();
		   			break;
		   		case ADD_ACCOUNT_MENU :
		   			currentTransaction = createTransactionAdmin(mainMenuSelection);
		   			currentTransactionController = new AddAccountController(currentTransaction, keypad, screen);
	                currentTransactionController.run(); // execute transaction
		   			break;
		   		case VIEW_ACCOUNT_MENU:
		   			currentTransaction = createTransactionAdmin(mainMenuSelection);
		   			currentTransactionController = new ViewAccountController(currentTransaction, keypad, screen);
	                currentTransactionController.run(); // execute transaction
		   			break;
		   		case EXIT_ADMIN_MENU: // user chose to terminate session
		   			screen.displayMessageLine("\nExiting the system...");
		   			userExited = true; // this ATM session should end
		   			break;
		   		default: // invalid input menu option 
		   			screen.displayMessageLine("\nYou did not enter a valid selection. Try again.");
		   			break;
		   }
		   
	   }

   }
   // display the main menu and perform transactions for individual customer
   private void performTransactionsCustomer() {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      TransactionController currentTransactionController = null;
      
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while (!userExited) {
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenuCustomer();

         // decide how to proceed based on user's menu selection
         switch (mainMenuSelection) {
            // user chose to perform one of three transaction types
         
            case BALANCE_INQUIRY: // user chose to view balance        
               // initialize as new object of chosen type
               currentTransaction = createTransactionCustomer(mainMenuSelection);
               currentTransactionController = new BalanceInquiryController(currentTransaction, keypad, screen);
               currentTransactionController.run(); // execute transaction
               break;
               
            case WITHDRAWAL: // user chose to balance taking
            	  // initialize as new object of chosen type
                currentTransaction = createTransactionCustomer(mainMenuSelection);
                currentTransactionController = new WithdrawalController(currentTransaction, keypad, screen);
                currentTransactionController.run(); // execute transaction
                break; 
                
            case TRANSFER: // user chose to add balance
                currentTransaction = createTransactionCustomer(mainMenuSelection);
                currentTransactionController = new TransferController(currentTransaction, keypad, screen);
                currentTransactionController.run(); // execute transaction
                break; 
                
            
            case EXIT_CUSTOMER_MENU: // user chose to terminate session
               screen.displayMessageLine("\nExiting the system...");
               userExited = true; // this ATM session should end
               break;
               
            default: // 
               screen.displayMessageLine("\nYou did not enter a valid selection. Try again.");
               break;
         }
      } 
   } 

   // display the main menu customer and return an input selection
   private int displayMainMenuCustomer() {
      screen.displayMessageLine("\nMain menu nasabah:");
      screen.displayMessageLine("1 - View My Balance");
      screen.displayMessageLine("2 - Withdraw Cash");
      screen.displayMessageLine("3 - Transfer Amount");
      screen.displayMessageLine("4 - Exit\n");
      screen.displayMessage("Enter a choice: ");
      return keypad.getInput(); // return user's selection
   }
   
   // display the main menu admin and return an input selection
   private int displayMainMenuAdmin() {
      screen.displayMessageLine("\nMain menu admin:");
      screen.displayMessageLine("1 - Unblock Account Number");
      screen.displayMessageLine("2 - Block Account Number");
      screen.displayMessageLine("3 - View Cash Dispenser Information");
      screen.displayMessageLine("4 - Add Cash Dispenser Information");
      screen.displayMessageLine("5 - Add Account Number");
      screen.displayMessageLine("6 - View Account Number");
      screen.displayMessageLine("7 - Exit\n");
      screen.displayMessage("Enter a choice: ");
      return keypad.getInput(); // return user's selection
   }
         
   private Transaction createTransactionCustomer(int type) {
      Transaction temp = null; 
          
      switch (type) {
         case BALANCE_INQUIRY: 
            temp = new BalanceInquiry(currentAccountNumber, bankDatabase);
            break;
         case WITHDRAWAL: 
             temp = new Withdrawal(currentAccountNumber, bankDatabase, cashDispenser);
             break;
         case TRANSFER: 
             temp = new Transfer(currentAccountNumber, bankDatabase); 
             break;
      }

      return temp;
   }
   
   private Transaction createTransactionAdmin(int type) {
	      Transaction temp = null; 
	          
	      switch (type) {
	      case UNBLOCK_ACCOUNT_MENU: 
	             temp = new UnBlockAccount(bankDatabase); 
	             break;   
	      case BLOCK_ACCOUNT_MENU: 
	             temp = new BlockAccount(bankDatabase); 
	             break;
	      case VIEW_ACCOUNT_MENU: 
	             temp = new ViewAccount(bankDatabase); 
	             break;
	      case ADD_ACCOUNT_MENU: 
	             temp = new AddAccount(bankDatabase); 
	             break;
	      }

	      return temp;
	   } 
}