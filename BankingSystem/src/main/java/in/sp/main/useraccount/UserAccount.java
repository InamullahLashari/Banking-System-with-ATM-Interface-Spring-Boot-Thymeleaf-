package in.sp.main.useraccount;

import jakarta.persistence.*;
import in.sp.main.user.UserRegister;

@Entity
@Table(name = "bank")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String cnic;
    private String accountNumber;
    private Double balance;
    private String pincode;
    private String email;
    private String address;
    private String phoneNum;

    // Just reference back (inverse side)
    @OneToOne(mappedBy = "account")
    private UserRegister userRegister;

    public UserAccount() {}

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCnic() { return cnic; }
    public void setCnic(String cnic) { this.cnic = cnic; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }

    public UserRegister getUserRegister() { return userRegister; }
    public void setUserRegister(UserRegister userRegister) { this.userRegister = userRegister; }
}
