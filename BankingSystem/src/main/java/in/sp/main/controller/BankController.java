package in.sp.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import in.sp.main.services.UserService;
import in.sp.main.user.UserRegister;
import in.sp.main.useraccount.UserAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


public class BankController {

    @Autowired
    private UserService userservice;

///////////////// ✅ Show Registration Page///////////////////////////////////////
   

    
    /********/////////////////////////////////end/////////////////////////////////////////////////////////////

    
    //////////////////this is the process of registration account ///////////////////////////////////////////
  
    
//    @PostMapping("/userside")  // Fix URL mapping to /register
//    public String AccountRegister(@ModelAttribute("user") UserRegister user, Model model) {  
//        // Assuming getemailCheck() method is correctly implemented to fetch data by email
//        UserAccount verify = userservice.getEmailCheck(user.getEmail());  // Use getEmail() instead of setEmail()
//        
//        // Verify the email, name, and account number
//        if (verify != null && 
//            verify.getEmail().equals(user.getEmail()) &&
//            verify.getName().equals(user.getName()) 
////            &&
////            verify.getAccountNum().equals(user.getAccountNum()
//            		) {
//
//            // If data matches, proceed to register the account
//            boolean status = userservice.registerAcoount(user);
//            if (status) {
//                model.addAttribute("message", "Account registered successfully.");
//            } else {
//                model.addAttribute("Errormessage", "Account creation failed. Please try again.");
//            }
//
//        } else {
//            // If data doesn't match, return error message
//           model.addAttribute("Errormessage", "Account verification failed. Please check your information as you insert.");
//           return "Register";
//        }
//        
//        return "Registered";  // Return the view name
//    }

    
    //*****///////////////////////////////////////end/////////////////////////////////////****//
    
    
    
    
   
    
    /////////////home////////////
    
  
    
    
    //////////////////this is the process of loginn account ///////////////////////////////////////////
    
   

//    // ✅ Process Login Form
//    @PostMapping("/logform")
//    public String loginCheck(@ModelAttribute("user") UserRegister user, Model model) {
//    	UserRegister valid = userservice.getEmailRegister(user.getUsername(), user.getPassword());
//    	UserRegister verify = userservice.getEmailCheck(user.getUsername()); 
//        if (valid != null) {
//            model.addAttribute("message", "Login Successfully");
//            model.addAttribute("name", valid.getName());
//            //model.addAttribute("account", valid.getAccountNum());
//            model.addAttribute("balance", verify.getBalance());
//            return "logined";
//        } else {
//            model.addAttribute("errorMessage", "Invalid email or password.");
//            return "login";
//        }
//    }
      
//////////////////this is the process of ATMaccount wher activa or not ///////////////////////////////////////////
    
   
    ////////////////////////////////////////////
    

   
   
   
   
   //////////////////////////atm pin code/////////////////
   @PostMapping("/pincode")
   public String checkPin(@ModelAttribute("user") UserAccount userpin, Model model) {
       // Check if the pin exists in the database
	   UserAccount verifypin = userservice.pinCheck(userpin.getPincode());

       // Corrected if condition: Removed the semicolon after the condition
       if (verifypin != null && verifypin.getPincode().equals(userpin.getPincode())) {
           model.addAttribute("message", "Your ATM card has been activated successfully.");
           return "Atminterface"; // Return success page
       } else {
           model.addAttribute("Errormessage", "System out of service.");
           return "Atminterface"; // Return error page
       }
   }

  
   
  
   
   
///////////////////acountcreated page display by clcik create account////////////////////
   
    
 ///////////////////user page  goes to display by login only////////////////////
   
   
	   
   }
   
   



    
    

    
    
    
    
    

