/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryptor;

/**
 *
 * @author Graham
 */
public class Profile {
    private String account;
    private String username;
    private String password;
    private String notes;
    
    public Profile(){
        
    }
    
    public Profile(String acc, String user, String pass, String note){
        account = acc;
        username = user;
        password = pass;
        notes = note;
    }
    
    public Profile (Profile orig){
        account = orig.account;
        username = orig.username;
        password = orig.password;
        notes = orig.notes;
    }
    
    public String getAccount(){
        return account;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getNotes(){
        return notes;
    }
    
    public void setAccount(String acc){
        account = acc;
    }
    
    public void setUsername(String user){
        username = user;
    }
    
    public void setPassword(String pass){
        password = pass;
    }
    
    public void setNotes(String note){
        notes = note;
    }
    
    public String toString(){
        return "Account: " + account + "\nUsername: " + username + "\nPassword: " + password + "\nNotes: " + notes;
    }
    
}
