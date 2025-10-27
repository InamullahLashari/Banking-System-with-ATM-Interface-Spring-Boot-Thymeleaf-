package in.sp.main.dto;

public class UserRegisterDTO {

    private String username;
    private String password;
    private String phoneNum;
    private String cnic;
    private String email;

    // Default constructor
    public UserRegisterDTO() {
    }

    // Parameterized constructor
    public UserRegisterDTO(String username, String password, String phoneNum, String cnic, String email) {
        this.username = username;
        this.password = password;
        this.phoneNum = phoneNum;
        this.cnic = cnic;
        this.email = email;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
