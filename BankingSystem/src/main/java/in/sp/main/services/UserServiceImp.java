package in.sp.main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.sp.main.repositories.BankAccountRepository;
import in.sp.main.repositories.UserRegrepository;
import in.sp.main.user.UserRegister;
import in.sp.main.useraccount.UserAccount;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    
    @Autowired
    private UserRegrepository userregisteration;
    

    // Method to generate a unique account number
    private String generateUniqueAccountNumber() {
        return String.valueOf((int) (Math.random() * 90000) + 10000); // Random number between 10000 and 99999
    }

    // Account creation and saving method
    @Override
    public boolean createAccount(UserAccount user) {
        try {
            // Generate a unique account number and assign it to the user
            user.setAccountNumber(generateUniqueAccountNumber());

            // Set initial balance to 0 (if not already set in the UserAccount constructor)
            user.setBalance(0.0);

            // Save the user account to the database
            bankAccountRepository.save(user);

            return true;
        } catch (Exception e) {
            // Log the error using a proper logging framework
            e.printStackTrace();
            return false;
        }
    }

    
    
   
///////////update////////////////////
//    @Override
//    public String updateAccount(String accountNum, Double balance ) {
//    	
//        UserAccount updateAccountUser = accountRepository.findByAccountNum(accountNum);
//
//        if (updateAccountUser != null) {
//            // Update the account details
//          
//             
//       double NewBalance = updateAccountUser.getBalance() + balance;
//        updateAccountUser.setBalance(NewBalance);
//            		accountRepository.save(updateAccountUser);
//            		
//            		 return "Succesfully Deposite:" + balance;
//        } else {
//            // Throw a custom exception
//            throw new RuntimeException("User not found with account number: " + accountNum);
//        }
//    }

    
  
	  
		
//////////////////////////registeration part///////////////////////////
	@Override
	public boolean registerAcoount(UserRegister Reguser) {
		
		 try {
	            
	            userregisteration.save(Reguser);

	            return true;
	        } catch (Exception e) {
	            
	            e.printStackTrace();
	            return false;
	        }
	    }
	
	
	
	 ///////////////////////


	//////////////////////////////ATM////////////////////////////////////////////////////
//	@Override
//	public UserAccount accountActivate(String accountUser) {
//		UserAccount sending = accountRepository.findByAccountNum(accountUser);
//	    
//	    
//	    return sending;
//	}
//


////////////////////set pin//////////////////////
//	
//
//	@Override
//	public boolean setPincode(String account, String pincode) {
//		UserAccount userVerify = accountRepository.findByAccountNum(account);
//
//	    if (userVerify != null) {
//	        // Update the account details
//	        userVerify.setPincode(pincode);
//	        accountRepository.save(userVerify);
//	        return true;
//	    } else {
//	        // Optionally log or throw an exception here to handle failure more explicitly
//	        return false;
//	    }
//	}
/////////////////////////verify Atm pin////////////
	
	

@Override
public UserAccount getEmailCheck(String email) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String updateAccount(String accountNum, Double balance) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public UserAccount accountActivate(String accountUser) {
	// TODO Auto-generated method stub
	return null;
}



@Override
public UserAccount getDetailEmail(String email) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public UserAccount pinCheck(String atmpin) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public UserRegister getEmailRegister(String email, String pass) {
	// TODO Auto-generated method stub
	return null;
}




	
	
	
}

    
