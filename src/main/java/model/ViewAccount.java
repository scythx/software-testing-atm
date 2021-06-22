package model;

public class ViewAccount extends Transaction{

	public ViewAccount(BankDatabase atmBankDatabase) {
		super(0, atmBankDatabase);
		// TODO Auto-generated constructor stub
	}

	// performs the transaction
	@Override
	public int execute() {
		return 0;
	}
	
	

}
