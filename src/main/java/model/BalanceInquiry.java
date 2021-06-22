package model;

//import view.Screen;

public class BalanceInquiry extends Transaction {
   // BalanceInquiry constructor
   public BalanceInquiry(int userAccountNumber, /*Screen atmScreen,*/ 
      BankDatabase atmBankDatabase) {

      super(userAccountNumber, /*atmScreen,*/ atmBankDatabase);
   } 

   // performs the transaction
   @Override
   public int execute() {
      return 0;
   }
} 