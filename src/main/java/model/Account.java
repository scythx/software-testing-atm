package model;

public class Account {
   private int accountNumber; // account number
   private int pin; // PIN for authentication
   private double availableBalance; // funds available for withdrawal
   private double depositFund; // deposit fund
   private double totalBalance; // funds available & pending deposits
   private int roleUser; // 1 sebagai admin; 2 sebagai nasabah perorangan; 3 sebagai nasabah company
   private String desc; // deskripsi Nasabah
   private int statusActive; // status nasabah, 1: status nasabah aktif (tidak diblokir); 
   							 //0: status nasabah tidak aktif (di blokir)  
   
   // inisialisasi akun tanpa default value
   public Account() {
   }
   
   // Account constructor initializes attributes without deposit fund
   public Account(int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance, int role, int status) {
      accountNumber = theAccountNumber;
      pin = thePIN;
      availableBalance = theAvailableBalance;
      totalBalance = theTotalBalance;
      roleUser = role;
      statusActive = status;
      desc = "-";
   }
   
   // Account constructor initializes attributes with deposit fund
   public Account(int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theDepositFund, double theTotalBalance, int role, int status) {
      accountNumber = theAccountNumber;
      pin = thePIN;
      availableBalance = theAvailableBalance;
      depositFund = theDepositFund;
      totalBalance = theTotalBalance;
      roleUser = role;
      statusActive = status;
      desc = "-";
   }

   // determines whether a user-specified PIN matches PIN in Account
   public boolean validatePIN(int userPIN) {
      if (userPIN == pin) {
         return true;
      }
      else {
         return false;
      }
   } 

   // returns available balance
   public double getAvailableBalance() {
      return availableBalance;
   } 

   // returns the total balance
   public double getTotalBalance() {
      return totalBalance;
   } 

   public void credit(double amount) {
	   availableBalance = availableBalance - amount;
	   //totalBalance = totalBalance - amount;
   }

   public void debit(double amount) {
	   //availableBalance = availableBalance + amount;
	   totalBalance = totalBalance + amount;
   }

   public int getAccountNumber() {
      return accountNumber;  
   }

	public int getPin() {
		return pin;
	}
	
	public void setPin(int pin) {
		this.pin = pin;
	}
	
	public int getRoleUser() {
		return roleUser;
	}
	
	public String getRoleDescription() {
		String roleDes = "";
		
		if(roleUser == 1) {
			roleDes = "admin";
		}else if (roleUser == 2) {
			roleDes = "nasabah";
		}
		return roleDes;
		
	}
	
	public void setRoleUser(int roleUser) {
		this.roleUser = roleUser;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}
	
	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public int getStatusActive() {
		return statusActive;
	}

	public void setStatusActive() {
		this.statusActive = 1;
	}
	
	public void setStatusBlocked() {
		this.statusActive = 0;
	}
	
	public String getStatusDescription() {
		String statusDes = "";
		
		if(statusActive == 1) {
			statusDes = "account number active";
		}else if (statusActive == 0) {
			statusDes = "account number blocked";
		}
		return statusDes;
	}
	
	public boolean isAccActive() {
		if(statusActive == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isAccBlocked() {
		if(statusActive == 0) {
			return true;
		}else {
			return false;
		}
	}

	public double getDepositFund() {
		return depositFund;
	}

	public void setDepositFund(double depositFund) {
		this.depositFund = depositFund;
	}
} 