package model;

import static model.Constants.ACCOUNT_NOT_REGISTERED;
import static model.Constants.UNBLOCK_ACCOUNT_CANCELED;
import static model.Constants.UNBLOCK_SUCCESSFUL;

public class UnBlockAccount extends Transaction{
	private int statusBlock; //status block process
	private int accNumUnBlock; // account number which blocked
	
	public UnBlockAccount(BankDatabase atmBankDatabase) {
		super(0, atmBankDatabase);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		BankDatabase bankDatabase;
		int status = getStatusBlock();
		int accUnBlock = getAccNumUnBlock();
		
		if (status != UNBLOCK_ACCOUNT_CANCELED) { // cek apakah idxamount bukan canceled (6) 
			// get bankdatabase akun
			bankDatabase = getBankDatabase();
		   		      
			   // some validation
			if (!bankDatabase.isAccountExist(accUnBlock)) { // cek destination account is not exist
				status = ACCOUNT_NOT_REGISTERED;
			}else {// lakukan proses unblock account number:
					// a. set status account to blocked
					//bankDatabase.blockingAcc(accBlock);	   
					status = UNBLOCK_SUCCESSFUL;
			}   

		}else {
			status = UNBLOCK_ACCOUNT_CANCELED;
		}	
		return status;		
		
	}

	public int getStatusBlock() {
		return statusBlock;
	}

	public void setStatusBlock(int statusBlock) {
		this.statusBlock = statusBlock;
	}

	public int getAccNumUnBlock() {
		return accNumUnBlock;
	}

	public void setAccNumUnBlock(int accNumBlock) {
		this.accNumUnBlock = accNumBlock;
	}

}
