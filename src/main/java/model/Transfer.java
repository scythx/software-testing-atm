package model;

import static model.Constants.*;

public class Transfer extends Transaction{
	private int statusTransfer; //status transaction process
	private int destAccountNumber; // destination account number to transfer amount
	private int amount; // amount to transfer
	
	// Transfer constructor
	public Transfer(int userAccountNumber, BankDatabase atmBankDatabase) {
		super(userAccountNumber, atmBankDatabase);
	} 
	
	// performs the transaction
	@Override
	public int execute() {
		BankDatabase bankDatabase;
		double availableBalance;
		int status = getStatusTransfer();
		int idxamount = getAmount();
		int destAcc = getDestAccountNumber();
		int currAcc = getAccountNumber();
		   
		if (status != TRANSFER_CANCELED) { // cek apakah idxamount bukan canceled (6) 
			// get bankdatabase akun
			bankDatabase = getBankDatabase();
			availableBalance = bankDatabase.getAvailableBalance(currAcc);
		   		      
			   // some validation
			if ((availableBalance-idxamount)<= MIN_AMOUNT_BALANCE) { // cek saldo apakah masih mencukupi
				status = BALANCE_NOT_ENOUGH;
			}else {
				//if(currAcc == destAcc) { // cek account number same with destination account number
					//status = SAME_ACCOUNT_NUMBER;
				//}else 
				if (!bankDatabase.isAccountExist(destAcc)) { // cek destination account is not exist
					status = ACCOUNT_NOT_REGISTERED;
				}else if(!bankDatabase.isAccountActive(destAcc)) { // cek destination account is blocked
					status = TRANSFER_ACCOUNT_BLOCKED;
				}else {// lakukan proses transfer uang:
					// a. Kurangi available balance dengan nominal uang yang ditransfer
					// b. kurangi total balance dengan nominal uang yang ditransfer
					bankDatabase.credit(currAcc, idxamount);
					   
					// c. tambahkan available balance (akun tujuan transfer) dengan nominal uang yang ditransfer
					// d. tambahkan total balance (akun tujuan transfer) dengan nominal uang yang ditransfer
					bankDatabase.debit(destAcc, idxamount);
					status = TRANSFER_SUCCESSFUL;
			   }
			}
		}else {
			status = TRANSFER_CANCELED;
		}	
		return status; 
	}

	public int getDestAccountNumber() {
		return destAccountNumber;
	}

	public void setDestAccountNumber(int destAccountNumber) {
		this.destAccountNumber = destAccountNumber;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getStatusTransfer() {
		return statusTransfer;
	}

	public void setStatusTransfer(int statusTransfer) {
		this.statusTransfer = statusTransfer;
	}
	
	
}
