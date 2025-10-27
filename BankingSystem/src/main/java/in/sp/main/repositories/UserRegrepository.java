package in.sp.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sp.main.dto.UserRegisterDTO;
import in.sp.main.user.UserRegister;
import java.util.Optional;

public interface UserRegrepository extends JpaRepository<UserRegister, Integer> {

    // Find a user by username
    UserRegister findByUsername(String username);


    
    UserRegister findByUsernameAndPassword(String username, String password);

}
