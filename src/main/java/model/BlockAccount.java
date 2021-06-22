package model;

import static model.Constants.*;

public class BlockAccount extends Transaction{
	private int statusBlock; //status block process
	private int accNumBlock; // account number which blocked
	
	public BlockAccount(BankDatabase atmBankDatabase) {
		super(0, atmBankDatabase);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int execute() {
		BankDatabase bankDatabase;
		int status = getStatusBlock();
		int accBlock = getAccNumBlock();
		   
		if (status != BLOCK_ACCOUNT_CANCELED) { // cek apakah idxamount bukan canceled (6) 
			// get bankdatabase akun
			bankDatabase = getBankDatabase();
		   		      
			   // some validation
			if (!bankDatabase.isAccountExist(accBlock)) { // cek destination account is not exist
				status = ACCOUNT_NOT_REGISTERED;
			}else {// lakukan proses block account number:
					// a. set status account to blocked
					//bankDatabase.blockingAcc(accBlock);	   
					status = BLOCK_SUCCESSFUL;
			}   

		}else {
			status = TRANSFER_CANCELED;
		}	
		return status;
	}

	public int getStatusBlock() {
		return statusBlock;
	}

	public void setStatusBlock(int statusBlock) {
		this.statusBlock = statusBlock;
	}

	public int getAccNumBlock() {
		return accNumBlock;
	}

	public void setAccNumBlock(int accNumBlock) {
		this.accNumBlock = accNumBlock;
	}

	
}
