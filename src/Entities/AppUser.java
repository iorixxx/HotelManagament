package Entities;

public class AppUser {
    private int ID;
    private String Username;
    private String Password;
    private String Name;
    private String Surname;
    
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public String getSurname() {
        return Surname;
    }
    public void setSurname(String surname) {
        Surname = surname;
    }
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {
        Username = username;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
}
