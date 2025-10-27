package in.sp.main.services;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.dto.UserBalanceDto;
import in.sp.main.dto.createDto;
import in.sp.main.repositories.BankAccountRepository;
import in.sp.main.repositories.UserRegrepository;
import in.sp.main.user.UserRegister;
import in.sp.main.useraccount.UserAccount;

@Service
public class BankServiceImp implements BankService {

    @Autowired
    private BankAccountRepository bankRepo;

    @Autowired
    private UserRegrepository userRepo;

    ///////////////////////////////////////
    // Create New User + Bank Account
    ///////////////////////////////////////
    @Override
    public boolean createUser(createDto dto) {
      
        // Step 2: Create UserAccount (bank details)
        UserAccount account = new UserAccount();
        account.setCnic(dto.getCnic());
        account.setEmail(dto.getEmail());
        account.setAddress(dto.getAddress());
        account.setName(dto.getName());
        account.setPhoneNum(dto.getPhoneNum());
        account.setBalance(0.0);

        // ✅ Generate unique account number using timestamp
        account.setAccountNumber(String.valueOf(System.currentTimeMillis()));

       

        // Save account in DB
        bankRepo.save(account);

        return true;
    }

    ///////////////////////////////////////
    // Get Account Detail By CNIC
    ///////////////////////////////////////
    @Override
    public UserBalanceDto getDetailByCnic(String cnic) {
        UserAccount account = bankRepo.findByCnic(cnic);

        if (account == null) {
            return null; // No record found
        }

        // Map entity -> DTO
        UserBalanceDto dto = new UserBalanceDto();
        dto.setName(account.getName());
        dto.setBalance(account.getBalance());
        dto.setAccountNumber(account.getAccountNumber());
       

        return dto;
    }

    ///////////////////////////////////////
    // ATM Side portion
    ///////////////////////////////////////

    // Check if account is eligible for ATM activation
    @Override
    public boolean accountActivate(String accNo) {
        try {
            UserAccount verifypin = bankRepo.findByAccountNumber(accNo);

            if (verifypin != null && verifypin.getPincode() == null) {
                // Account exists and has no PIN yet → eligible for activation
                return true;
            } else {
                // Account already has a PIN or doesn't exist
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    ///////////////////////////////////////
    // Set ATM PIN (only if not set before)
    ///////////////////////////////////////
    @Override
    public boolean setPin(String accountNumber, String pincode) {
        try {
            UserAccount account = bankRepo.findByAccountNumber(accountNumber);

            if (account != null && account.getPincode() == null) {
                account.setPincode(pincode);   // Set new PIN
                bankRepo.save(account);        // Save changes
                return true;                   // Success
            } else {
                // PIN already exists OR account not found
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    ///////////////////////////////////////
    // Find Account By Account Number
    ///////////////////////////////////////
    @Override
    public UserAccount findByAccountNumber(String accountNumber) {
        return bankRepo.findByAccountNumber(accountNumber);
    }
    
    
    /////////////////////////////verifypincode////////////////////////////
    @Override
    public Optional<UserAccount> findByAccountNumberAndPincode(String accNo, String pincode) {
        if (accNo == null || accNo.isBlank() || pincode == null || pincode.isBlank()) {
            return Optional.empty();
        }
        return bankRepo.findByAccountNumberAndPincode(accNo, pincode);
    }

	@Override
	public Optional<UserAccount> findByPincode(String pincode) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	
	
///////////////////////////////////////
// Withdraw and update balance
///////////////////////////////////////
@Override
public boolean withdrawAmount(String accountNumber, double amount) {
UserAccount account = bankRepo.findByAccountNumber(accountNumber);

if (account == null) {
return false; // account not found
}

if (amount <= 0 || account.getBalance() < amount) {
return false; // invalid or insufficient funds
}

// Deduct balance
account.setBalance(account.getBalance() - amount);
bankRepo.save(account);

return true;

   

    
}}
