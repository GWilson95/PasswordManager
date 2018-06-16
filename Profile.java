/*
Profile.java
Author: Graham Wilson
Date: June 16th, 2018
*/

/*Package*/
package encryptor;

/*Profile Class*/
public class Profile {
    private String account;
    private String username;
    private String password;
    private String notes;
    
    /*Default Constructor*/
    public Profile(){
        
    }
    
    /*4-Argument Constructor*/
    public Profile(String acc, String user, String pass, String note){
        account = acc;
        username = user;
        password = pass;
        notes = note;
    }
    
    /*Copy Constructor*/
    public Profile (Profile orig){
        account = orig.account;
        username = orig.username;
        password = orig.password;
        notes = orig.notes;
    }
    
    /*Accessor Functions*/
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
    
    /*Mutator Functions*/
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
    
    /*Converts the data in the profile to one string*/
    @Override
    public String toString(){
        return "Account: " + account + "\nUsername: " + username + "\nPassword: " + password + "\nNotes: " + notes;
    }
    
}
