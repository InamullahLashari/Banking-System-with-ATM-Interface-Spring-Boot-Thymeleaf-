package in.sp.main.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.sp.main.user.UserRegister;
import in.sp.main.useraccount.UserAccount;
import in.sp.main.dto.UserRegisterDTO;
import in.sp.main.repositories.BankAccountRepository;
import in.sp.main.repositories.UserRegrepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller 
public class UserController {

    @Autowired
    private UserRegrepository userRepository;  // Make sure this is correctly injected
    @Autowired
    private BankAccountRepository bankAccountRepository;

    //////////////////////////////////// User side page //////////////////////
    @GetMapping("/userside")
    public String userside(Model model) {
        model.addAttribute("user", new UserAccount());
        return "UserSide/userside";
    } 

    ////////////////// Load login page /////////////////////
    @GetMapping("/loginpage")
    public String loginPage(Model model) {
        model.addAttribute("user", new UserRegisterDTO());
        return "UserSide/login";  // Make sure this matches your Thymeleaf file
    }

    /////////////////////////////////////// Register ///////////////////////////////
    @GetMapping("/register")
    public String Userrisgter(Model model) {
        model.addAttribute("user", new UserRegisterDTO());
        return "UserSide/Register"; // Your registration page
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegisterDTO user, Model model) {

        // 1. Find bank account by CNIC
        UserAccount bankAccount = bankAccountRepository.findByCnic(user.getCnic());

        if (bankAccount != null && 
            bankAccount.getEmail().equals(user.getEmail()) && 
            bankAccount.getPhoneNum().equals(user.getPhoneNum())) {

            // 2. Create new user and link with bank account
            UserRegister newUser = new UserRegister();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            newUser.setAccount(bankAccount);   // ✅ link here

            userRepository.save(newUser);

            model.addAttribute("successMessage", "✅ Registration successful! Now login.");
            model.addAttribute("user", new UserRegisterDTO());
            return "UserSide/login";
        }

        // Error case
        model.addAttribute("errorMessage", "❌ CNIC, Email or Phone not found in our records!");
        return "UserSide/Register";
    }

    ///////////////////////////////// Logout ///////////////////////////////////////
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Invalidate session if it exists
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Redirect to the login page
        return "redirect:/loginpage"; // Correct endpoint for login page
    }

    ////////////////////////// Login form submission //////////////////////////////
//////////////////////////Login form submission //////////////////////////////
@PostMapping("/logform")
public String loginUser(@ModelAttribute("user") UserRegisterDTO userDto, Model model) {
// Step 1: Validate username & password from UserRegister
UserRegister user = userRepository.findByUsernameAndPassword(
userDto.getUsername(), userDto.getPassword());

if (user != null) {
// ✅ Step 2: Fetch linked account directly via relation
UserAccount account = user.getAccount();

if (account != null) {
// ✅ Pass account details to dashboard
model.addAttribute("name", account.getName());
model.addAttribute("account", account.getAccountNumber());
model.addAttribute("balance", account.getBalance());

return "UserSide/logined"; // show user dashboard
} else {
model.addAttribute("errorMessage", "⚠ No account linked with this user!");
return "UserSide/login";
}
}

// ❌ Invalid username/password
model.addAttribute("errorMessage", "❌ Invalid username or password!");
return "UserSide/login";
}


}
