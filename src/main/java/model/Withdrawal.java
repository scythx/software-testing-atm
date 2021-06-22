package model;

//import view.Keypad;
//import view.Screen;
import static model.Constants.*;

// Withdrawal.java
// Represents a withdrawal ATM transaction

public class Withdrawal extends Transaction {
   private int amount; // amount to withdraw
   //private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // Withdrawal constructor
   public Withdrawal(int userAccountNumber, /*Screen atmScreen,*/ 
      BankDatabase atmBankDatabase, /*Keypad atmKeypad,*/ CashDispenser atmCashDispenser) {

      // initialize superclass variables
      super(userAccountNumber, /*atmScreen,*/ atmBankDatabase);
      //keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   }

   // perform transaction
   @Override
   public int execute() {
	   BankDatabase bankDatabase;
	   double availableBalance;
	   int status = 0;
	   int idxamount = getAmount();
	   
	   if (idxamount != WITHDRAWAL_CANCELED) { // cek apakah idxamount bukan canceled (6) 
		   // get bankdatabase akun
		   bankDatabase = getBankDatabase();
		   availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
	   		      
		   // lakukan proses pengambilan uang:
		   // cek dulu range value amount dengan batasan limit value
		   if(!cashDispenser.isSufficientCashAvailable(idxamount)) { 
			   // cek dulu uang yang ada di dispenser ATM apakah masih ada?
			   status = CASHDISPENSER_NOT_ENOUGH ;
			   bankDatabase.credit(getAccountNumber(), idxamount);
			   cashDispenser.dispenseCash(idxamount);
			   status = WITHDRAW_SUCCESSFUL;
		   }else if ((availableBalance-idxamount)<= MIN_AMOUNT_BALANCE) { // cek saldo apakah masih mencukupi
			   status = BALANCE_NOT_ENOUGH;
		   }else {
			   // a. Kurangi available balance dengan nominal uang yang diambil
			   // b. kurangi total balance dengan nominal uang yang diambil
			   // c. Kurangi cash dispenser amount
			   bankDatabase.credit(getAccountNumber(), idxamount);
			   //cashDispenser.dispenseCash(idxamount);
			   status = WITHDRAW_SUCCESSFUL;
		   }
	   }else {
		   status = WITHDRAWAL_CANCELED;
	   }
	   
	   return status;
   }
   
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	} 
} 