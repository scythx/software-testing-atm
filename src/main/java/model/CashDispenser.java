package model;

import static model.Constants.*;

public class CashDispenser {
   // the default initial number of bills in the cash dispenser

   private int count; // number of $20 bills remaining
   
   // no-argument CashDispenser constructor initializes count to default
   public CashDispenser() {
      count = INITIAL_COUNT_CASHDISPINSER; // set count attribute to default
   }

   // simulates dispensing of specified amount of cash
   public void dispenseCash(int amount) {
      // pecahan nominal $20
	  int billsRequired = amount / NOMINAL_AMOUNT; // number of $20 bills required
      count -= billsRequired; // update the count of bills
   }

   // indicates whether cash dispenser can dispense desired amount
   public boolean isSufficientCashAvailable(int amount) {
      int billsRequired = amount / NOMINAL_AMOUNT; // number of $20 bills required

      if (count >= billsRequired) {
         return true; // enough bills available
      }
      else {
         return false; // not enough bills available
      }
   }

	public int getCount() {
		return count;
	}
	
	public void addCount(int numberOfSheet) {
		count = count + numberOfSheet;
	}
	
	public int getTotalAmountCashDispenser() {
		return (count * NOMINAL_AMOUNT);
	}
} 