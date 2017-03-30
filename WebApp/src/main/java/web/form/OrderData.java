package web.form;

public class OrderData {
    private String name;
    private String phone;
    private String city;
    private String street;
    private String houseNumber;
    private String email;
    private String password;
    private String confirmPassword;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return super.toString()
                + " {name=" + name
                + ", phone=" + phone
                + ", city=" + city
                + ", street=" + street
                + ", houseNumber=" + houseNumber
                + ", email=" + email
                + ", password=" + password
                + ", confirmPassword" + confirmPassword
                + "}";
    }
}
