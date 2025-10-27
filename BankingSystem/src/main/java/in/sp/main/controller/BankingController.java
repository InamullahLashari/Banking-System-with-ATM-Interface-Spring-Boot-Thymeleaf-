package in.sp.main.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // ✅ important
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import in.sp.main.dto.DepositRequest;
import in.sp.main.dto.UserBalanceDto;
import in.sp.main.dto.createDto;
import in.sp.main.repositories.BankAccountRepository;
import in.sp.main.services.BankService;
import in.sp.main.user.UserRegister;
import in.sp.main.useraccount.UserAccount;

@Controller  // ✅ tells Spring this class returns views
public class BankingController {
@Autowired
   private BankService bankservice;
@Autowired
private  BankAccountRepository bankAccountRepository;
    
    ////////////////////////////////////////home///////////////////
    
  @GetMapping("/home")
    
    
    public String inammPage() {
        return "redirect:/inamm.html";	
    }

    ///////////////////// this page load of baking option ///////////////////////
    @GetMapping("/bank")
    public String registerAccount(Model model) {
        model.addAttribute("user", new UserAccount());
        return "bankingside/bankingsideoperation";  // ✅ looks inside templates/side/bankingsideoperation.html
    }
    
    
    ///////////////////// this page load of baking option ///////////////////////
   
    
    
    ///////////////////////////////one is deposite///////////////////
     @GetMapping("/Deposite")
   public String depositeside(Model model) {
       model.addAttribute("user", new UserRegister());
       return "bankingside/deposite";
   }
     
     ///////////////////////////////two is create account///////////////////
     @GetMapping("/createAcc")
     public String createAccount(Model model) {
         model.addAttribute("user", new createDto());  // ✅ with parentheses
         return "bankingside/createAccount";          // ✅ template path
     }
     
   

     @PostMapping("/createaccount")
     public String createAccount(@ModelAttribute("user") createDto dto, Model model) {
         boolean created = bankservice.createUser(dto);

         if (created) {
             // After creation, immediately fetch details by CNIC
             UserBalanceDto accountInfo = bankservice.getDetailByCnic(dto.getCnic());

             // Add attributes for Thymeleaf
             model.addAttribute("successMessage", "Account created successfully!");
             model.addAttribute("name", accountInfo.getName());
             model.addAttribute("account", accountInfo.getAccountNumber());
             model.addAttribute("balance", accountInfo.getBalance());
             
             

             return "bankingside/Created";  // ✅ Thymeleaf success page
         } else {
             // If creation failed
             model.addAttribute("Errormessage", "Account creation failed. Please try again!");
             return "bankingside/createAccount"; // back to form
         }
     }
     
///////////////////// Deposit money ////////////////////////////
     @PostMapping("/deposit")
     public String deposit(@ModelAttribute("user") DepositRequest user, Model model) {
         // 1. Find account
         UserAccount account = bankAccountRepository.findByAccountNumber(user.getAccountNumber());
         System.out.println(user.getAmount());
         if (account == null) {
             // If account not found → show error page
             model.addAttribute("errorMessage", "❌ Account not found!");
             return "bankingside/depositError";  // create depositError.html
         }

         // 2. Update balance
         double newBalance = account.getBalance() + user.getAmount();
         account.setBalance(newBalance);

         // 3. Save updated account
         bankAccountRepository.save(account);

         // 4. Pass data to success page
         model.addAttribute("amount", user.getAmount());
         model.addAttribute("accountNumber", user.getAccountNumber());
         model.addAttribute("newBalance", newBalance);

         return "bankingside/depositSuccess";  // ✅ this is the Thymeleaf template
     }


   
	 
}
