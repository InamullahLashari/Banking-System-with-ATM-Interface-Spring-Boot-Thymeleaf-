
package in.sp.main.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.sp.main.dto.AtmFormDto;
import in.sp.main.services.BankService;
import in.sp.main.useraccount.UserAccount;
import jakarta.servlet.http.HttpSession;



@Controller
public class AtmController {
	
	@Autowired
 private BankService bankservice;
	
	   @GetMapping("/atm")
	    public String amtside(Model model) {
		   model.addAttribute("user", new UserAccount());
	        return "Atmside/atmside";  // resolves to templates/Atmside/atmside.html
	    }
	////////////////////////////////////Atm activationN function/////////////////////////
	   

	    /////////////////////////////////////
	    // Check account eligibility
	    /////////////////////////////////////
	   @PostMapping("/pinentry")
	   public String atmActivate(
	           @ModelAttribute("user") UserAccount user,
	           Model model,
	           HttpSession session) {

	       boolean eligible = bankservice.accountActivate(user.getAccountNumber());
	       System.out.println("this is boolean " + eligible);

	       if (eligible) {
	           // PIN not set → go to activate PIN page
	           model.addAttribute("accountNum", user.getAccountNumber());
	           return "Atmside/activateAtminterface";
	       } else {
	           // PIN already set or account not found → show message
	           model.addAttribute("message", "ATM card already activated or account not found.");

	           // ✅ Store account number in session only in this else case
	           session.setAttribute("accountNumber", user.getAccountNumber());

	           return "Atmside/pincode";
	       }
	   }



	    /////////////////////////////////////
	    // Save entered PIN
	    /////////////////////////////////////
	    @PostMapping("/savePin")
	    public String savePin(@ModelAttribute("user") UserAccount user, Model model) {

	        // Debug: print received values
	        System.out.println("Received Account Number: " + user.getAccountNumber());
	        System.out.println("Received PIN: " + user.getPincode());

	        String accountNum = user.getAccountNumber();
	        String pin = user.getPincode();

	        boolean saved = bankservice.setPin(accountNum, pin);

	        if (saved) {
	            model.addAttribute("message", "✅ Your ATM card PIN has been set successfully.");
	            return "Atmside/atmside";
	        } else {
	            model.addAttribute("message", "❌ Failed to set PIN. Account not found or already activated.");
	            return "null";
	        }
	    }


	
////////////////////////////////////////////open atm interfcae////////////////////////////
	   

//	    @PostMapping("/verifyPin")
//	    public String verifyPin(@RequestParam String pincode, Model model) {
//	        Optional<UserAccount> accountOpt = bankservice.findByPincode(pincode);
//
//	        if (accountOpt.isPresent()) {
//	            model.addAttribute("message", "✅ PIN verified successfully. Welcome!");
//	            return "Atmside/atminterface";
//	        } else {
//	            model.addAttribute("Errormessage", "❌ Invalid PIN. Please try again.");
//	            return "Atmside/pincode";
//	        }
//	    }
	    
	    @PostMapping("/verifyPin")
	    public String verifyPin(
	            @RequestParam String pincode,
	            Model model,
	            HttpSession session) {

	        // Retrieve the account number stored in session
	        String accNo = (String) session.getAttribute("accountNumber");

	        if (accNo == null) {
	            // Session expired or card not inserted
	            model.addAttribute("Errormessage", "⚠️ Please insert your card again.");
	            return "Atmside/pincode";
	        }

	        // Verify account number + PIN
	        Optional<UserAccount> accountOpt = bankservice.findByAccountNumberAndPincode(accNo, pincode);

	        if (accountOpt.isPresent()) {
	            // ✅ Keep account number in session for next steps (withdraw etc.)
	            session.setAttribute("accountNumber", accNo);

	            model.addAttribute("message", "✅ PIN verified successfully. Welcome!");
	            return "Atmside/atminterface";
	        } else {
	            model.addAttribute("Errormessage", "❌ Invalid PIN. Please try again.");
	            return "Atmside/pincode";
	        }
	    }


////////////////////////////////////////////////withdraw side//////////
	    
	    @GetMapping("/withdraw")
	    public String createAccount(Model model) {
	        model.addAttribute("atmForm", new AtmFormDto());  // ✅ match with POST
	        return "Atmside/cashwithdraw";
	    }

	    @PostMapping("/atm/withdraw")
	    public String withdraw(@ModelAttribute("atmForm") AtmFormDto atmDto, Model model, HttpSession session) {
	        Integer amount = atmDto.getAmount();
	        String accountNumber = (String) session.getAttribute("accountNumber"); // get from session

	        if (accountNumber == null) {
	            model.addAttribute("error", "No active account found. Please login first.");
	            return "Atmside/cashwithdraw";
	        }

	        if (amount == null || amount <= 0) {
	            model.addAttribute("error", "Please enter a valid amount");
	            return "Atmside/cashwithdraw";
	        }

	        boolean success = bankservice.withdrawAmount(accountNumber, amount);

	        if (success) {
	            model.addAttribute("success", "Cash withdrawn successfully: ₹" + amount);
	        } else {
	            model.addAttribute("error", "Insufficient balance or invalid amount");
	        }

	        model.addAttribute("atmForm", new AtmFormDto()); // reset form
	        model.addAttribute("accountNumber", accountNumber); // keep showing account number

	        return "Atmside/cashwithdraw";
	    }

	    @GetMapping("/fastcash")
	    public String showFastCashPage() {
	        return "Atmside/fastcash"; // HTML page where you show preset amounts
	    }
	    @GetMapping("/atminterface")
	    public String showatminterface() {
	        return "Atmside/atminterface"; // HTML page where you show preset amounts
	    }

	    
//    @PostMapping("/pinentry")
//    public String loginChecknm(@ModelAttribute("user") UserAccount user, Model model) {
//        UserAccount atmActive = userservice.accountActivate(user.getAccountNum());
//
//        if (atmActive == null) {
//            model.addAttribute("errorMessage", "Account not found: " + user.getAccountNum());
//            return "test";  // make sure test.html is in templates
//        }
//
//        if (atmActive.getPincode() == null) {
//            return "activate";  // resolves to templates/activate.html
//        } else {
//            model.addAttribute("errorMessage", "server error");
//            return "pincode";  // resolves to templates/pincode.html
//        }
   }
