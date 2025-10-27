package in.sp.main.user;

import jakarta.persistence.*;
import in.sp.main.useraccount.UserAccount;

@Entity
@Table(name = "register")
public class UserRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    // Foreign key to UserAccount
    @OneToOne
    @JoinColumn(name = "account_id")
    private UserAccount account;

    public UserRegister() {}

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserAccount getAccount() { return account; }
    public void setAccount(UserAccount account) { this.account = account; }
}
