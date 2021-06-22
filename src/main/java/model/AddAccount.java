package model;

import static model.Constants.ACCOUNT_REGISTERED;
import static model.Constants.ADD_ACCOUNT_CANCELED;
import static model.Constants.ADD_ACCOUNT_SUCCESSFUL;

public class AddAccount extends Transaction {
	private Account newAccount;
	private int statusAdd;
	
	public AddAccount(BankDatabase atmBankDatabase) {
		super(0, atmBankDatabase);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		BankDatabase bankDatabase;
		int status = getStatusAdd();
		Account acc = getNewAccount();
		double totalAvailableBalance = 0;
		
		if (status != ADD_ACCOUNT_CANCELED) { // cek apakah idxamount bukan canceled (6) 
			// get bankdatabase akun
			bankDatabase = getBankDatabase();
		   		      
			   // some validation
			if (bankDatabase.isAccountExist(acc.getAccountNumber())) { // cek destination account is exist
				status = ACCOUNT_REGISTERED;
			}else {// lakukan proses penambahan account
					// calculate total available balance for nasabah
					if(acc.getRoleUser() == 2) {
						totalAvailableBalance = acc.getTotalBalance();
					}
					acc.setTotalBalance(totalAvailableBalance);
					//bankDatabase.addAccount(acc);  
					status = ADD_ACCOUNT_SUCCESSFUL;
			}   

		}else {
			status = ADD_ACCOUNT_CANCELED;
		}	
		return status;
	}

	public Account getNewAccount() {
		return newAccount;
	}

	public void setNewAccount(Account newAccount) {
		this.newAccount = newAccount;
	}

	public int getStatusAdd() {
		return statusAdd;
	}

	public void setStatusAdd(int statusAdd) {
		this.statusAdd = statusAdd;
	}

}
