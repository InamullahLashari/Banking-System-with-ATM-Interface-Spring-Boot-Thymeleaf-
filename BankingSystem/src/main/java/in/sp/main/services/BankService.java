package in.sp.main.services;

import java.util.Optional;

import in.sp.main.dto.UserBalanceDto;
import in.sp.main.dto.createDto;
import in.sp.main.useraccount.UserAccount;

public interface BankService {
	
	public boolean createUser(createDto dto) ;
	
	
 UserBalanceDto getDetailByCnic(String cnic);
 
 public boolean accountActivate(String accNo);
 
 // set ATM pin
public boolean setPin(String accountNumber, String pincode);

UserAccount findByAccountNumber(String accountNumber);

Optional<UserAccount>findByPincode(String pincode);


Optional<UserAccount> findByAccountNumberAndPincode(String accountNumber, String pincode);
public boolean withdrawAmount(String accountNumber, double amount);

 
 

}
