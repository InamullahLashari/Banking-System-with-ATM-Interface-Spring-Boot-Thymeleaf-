package in.sp.main.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import in.sp.main.useraccount.UserAccount;




public interface BankAccountRepository extends JpaRepository<UserAccount, Integer> {
	
	
//	UserAccount  findByAccountNum(String accountNum);
	
	//here we use to get user detail if we use all getbyall method it give all detail
	
//	UserAccount findByusername(String username);
//	
	//UserAccount  findByPincode(String pincode);
	

	//public UserAccount findByPincode(String atmpin);
	public  UserAccount findByCnic(String cnic);

	UserAccount findByAccountNumber(String accountNumber);
	
	
	Optional<UserAccount> findByPincode(String pincode);
	Optional<UserAccount> findByAccountNumberAndPincode(String accountNumber, String pincode);
	boolean existsByCnic(String cnic);
	

	
	 
	
}



