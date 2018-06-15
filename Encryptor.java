/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*Package*/
package encryptor;

/*Library Imports*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/*Encryptor Class*/
public class Encryptor extends JFrame implements ActionListener, ItemListener{

    /*Size of default window*/
    public static final int WIDTH = 500;
    public static final int HEIGHT = 550;
    
    /*GUI variables*/
    private final JMenuBar mainMenuBar;
    private final JMenu fileMenu;
    private final JMenuItem newFile;
    private final JMenuItem openFile;
    private final JMenuItem saveAsFile;
    private final JPanel statusPanel;
    private JLabel statusLabel;
    private TitledBorder title;
    private final JPanel titlePanel;
    private final JPanel programPanel;
    private final JPanel usernamePanel;
    private final JPanel passwordPanel;
    private final JPanel notePanel;
    private final JLabel programLabel;
    private final JLabel usernameLabel;
    private final JLabel passwordLabel;
    private final JLabel noteLabel;
    private JComboBox programField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextArea noteField;
    private final JPanel buttonPanel;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    /*Add GUI Popup*/
    private final JPanel addPanel;
    private final JPanel addOne;
    private final JPanel addTwo;
    private final JPanel addThree;
    private final JPanel addFour;
    private final JLabel addOneLabel;
    private final JLabel addTwoLabel;
    private final JLabel addThreeLabel;
    private final JLabel addFourLabel;
    private JTextField inProgram;
    private JTextField inUsername;
    private JTextField inPassword;
    private JTextField inNotes;
    private final Object[] addOptions = {"Add", "Cancel"};
    
    /*File IO variables*/
    private JFileChooser saveChooser;
    private JFileChooser openChooser;
    private final FileNameExtensionFilter filter = new FileNameExtensionFilter("Encoded", "enc");
    private BufferedWriter bw = null;
    private FileWriter fw = null;
    private BufferedReader br = null;
    private FileReader fr = null;
    private String input;
    private String fileName;
    
    /*Encryption variables*/
    private char[] passcode = new char[16];
    
    /*Variables to store the activities and key words*/
    private ArrayList<Profile> profileList = new ArrayList<Profile>();
    
    /*Main function to create the GUI object*/
    public static void main(String[] args) {
        Encryptor w = new Encryptor();
        w.setVisible(true);
    }
    
    public Encryptor(){
        /*Creates initial JFrame*/
        super("Encryptor");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        /*Create Menu Bar*/
        mainMenuBar = new JMenuBar();
        setJMenuBar(mainMenuBar);
        
        /*Create File Menu Items*/
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        newFile = new JMenuItem("New");
        newFile.setMnemonic(KeyEvent.VK_N);
        newFile.addActionListener(this);
        fileMenu.add(newFile);
        mainMenuBar.add(fileMenu);
        
        openFile = new JMenuItem("Open");
        openFile.setMnemonic(KeyEvent.VK_O);
        openFile.addActionListener(this);
        fileMenu.add(openFile);
        mainMenuBar.add(fileMenu);
        
        saveAsFile = new JMenuItem("Save As...");
        saveAsFile.setMnemonic(KeyEvent.VK_A);
        saveAsFile.addActionListener(this);
        saveAsFile.setEnabled(false);
        fileMenu.add(saveAsFile);
        mainMenuBar.add(fileMenu);
        
        /*Create Status Bar at bottom of screen*/
        statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel = new JLabel("Create a new file or open an existing file");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
        
        /*Create Titled Border which contains the file name*/
        title = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "new.enc");
        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.PAGE_AXIS));
        titlePanel.setBorder(title);
        add(titlePanel, BorderLayout.CENTER);
        
        /*Create the Account Panel and components*/
        programPanel = new JPanel();
        programLabel = new JLabel("Account: ");
        programField = new JComboBox();
        programField.addItemListener(this);
        programField.setPreferredSize(new Dimension(400, 20));
        programPanel.add(programLabel);
        programPanel.add(programField);
        titlePanel.add(programPanel);
        /*Create Username Panel and components*/
        usernamePanel = new JPanel();
        usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField(35);
        usernameField.setEditable(false);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        titlePanel.add(usernamePanel);
        /*Create Password Panel and components*/
        passwordPanel = new JPanel();
        passwordLabel = new JLabel("Password: ");
        passwordField = new JTextField(35);
        passwordField.setEditable(false);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        titlePanel.add(passwordPanel);
        /*Create Edit Panel and components*/
        notePanel = new JPanel();
        notePanel.setLayout(new BorderLayout());
        noteLabel = new JLabel("Note: ");
        noteField = new JTextArea();
        noteField.setEditable(false);
        notePanel.add(noteLabel, BorderLayout.NORTH);
        notePanel.add(noteField, BorderLayout.CENTER);
        titlePanel.add(notePanel);
        
        /*Create add, edit and delete buttons*/
        buttonPanel = new JPanel();
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        editButton = new JButton("Edit");
        editButton.addActionListener(this);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        titlePanel.add(buttonPanel);
        
        /*Create GUI for 'Add' JOptionPane*/
        addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.PAGE_AXIS));
        addOne = new JPanel();
        addOneLabel = new JLabel("Account:     ");
        inProgram = new JTextField(35);
        addTwo = new JPanel();
        addTwoLabel = new JLabel("Username: ");
        inUsername = new JTextField(35);
        addThree = new JPanel();
        addThreeLabel = new JLabel("Password: ");
        inPassword = new JTextField(35);
        addFour = new JPanel();
        addFourLabel = new JLabel("Notes:         ");
        inNotes = new JTextField(35);
        addOne.add(addOneLabel);
        addOne.add(inProgram);
        addTwo.add(addTwoLabel);
        addTwo.add(inUsername);
        addThree.add(addThreeLabel);
        addThree.add(inPassword);
        addFour.add(addFourLabel);
        addFour.add(inNotes);
        addPanel.add(addOne);
        addPanel.add(addTwo);
        addPanel.add(addThree);
        addPanel.add(addFour);
        
        titlePanel.setVisible(false);
    }
    
    /*Overrides the actionPerformed method*/
    @Override
    public void actionPerformed(ActionEvent e){
        int result;
        Profile prof;
        String buttonPress = e.getActionCommand();
        
        /*If the 'Add' button is pressed, grab input from the user for a new Profile*/
        if (buttonPress.equals("Add")){
            result = JOptionPane.showOptionDialog(this, addPanel, "AddProfile", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, addOptions, addOptions[0]);
            String prog = inProgram.getText();
            String user = inUsername.getText();
            String pass = inPassword.getText();
            String note = inNotes.getText();
            /*Checks that the input was confirmed before storing it*/
            if (result == JOptionPane.YES_OPTION && prog.length() > 0 && user.length() > 0 && pass.length() > 0){
                profileList.add(new Profile(prog, user, pass, note));
                addProfile(profileList.get(profileList.size()-1));
            }
            /*Add a notifier that the file has been changed*/
            title.setTitle(fileName + "*");
            repaint();
            /*Reset the add menu values*/
            inProgram.setText("");
            inUsername.setText("");
            inPassword.setText("");
            inNotes.setText("");
        }
        /*If the 'Edit' button is pressed, pop up a menu that allows the user to change an existing Profiles data*/
        else if (buttonPress.equals("Edit")){
            result = JOptionPane.showOptionDialog(this, addPanel, "AddProfile", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, addOptions, addOptions[0]);
            String prog = inProgram.getText();
            String user = inUsername.getText();
            String pass = inPassword.getText();
            String note = inNotes.getText();
            /*Checks the the input was confirmed before applying changes*/
            if (result == JOptionPane.YES_OPTION && prog.length() > 0 && user.length() > 0 && pass.length() > 0){
                prof = profileList.get(programField.getSelectedIndex());
                prof.setAccount(prog);
                prof.setUsername(user);
                prof.setPassword(pass);
                prof.setNotes(note);
                editProfile(profileList);
            }
            /*Add a notifier that the file has been changed*/
            title.setTitle(fileName + "*");
            repaint();
            /*Reset the edit menu values*/
            inProgram.setText("");
            inUsername.setText("");
            inPassword.setText("");
            inNotes.setText("");
        }
        /*If the 'Delete' button is pressed, remove the current Profile from the list*/
        else if (buttonPress.equals("Delete")){
            System.out.println(profileList.get(0).toString());
            
        }
        /*If the 'New' button is pressed, create a new empty file*/
        else if (buttonPress.equals("New")){
            input = (String)JOptionPane.showInputDialog(this, "Create file name: ", "New File", JOptionPane.PLAIN_MESSAGE, null, null, "");
            /*Checks that proper file name is given by user*/
            if (input != null && (input.length() > 0)){
                /*Ensures that the file has the '.enc' extension*/
                fileName =  input + ".enc";
                /*Let user know the file is not saved*/
                title.setTitle(fileName + "*");
                statusLabel.setText(fileName + " has not been saved");
                repaint();
                
                titlePanel.setVisible(true);
                saveAsFile.setEnabled(true);
                input = null;
                createCode();
            }
        }
        /*If the 'Open' button is pressed, open an existing .enc file*/
        else if (buttonPress.equals("Open")){
            openFile();
        }
        /*If the 'Save As...' button is pressed, save the current file*/
        else if (buttonPress.equals("Save As...")){
            saveFile();
        }
    }
    /*Overrides the itemStateChanged function*/
    @Override
    public void itemStateChanged(ItemEvent event){
        Profile p;
        if (event.getStateChange() == ItemEvent.SELECTED){
            p = profileList.get(programField.getSelectedIndex());
            usernameField.setText(p.getUsername());
            passwordField.setText(p.getPassword());
            noteField.setText(p.getNotes());
        }
    }
    
    /*Takes a Profile object 'P' and adds it to Profile drop down list*/
    public void addProfile(Profile p){
        programField.addItem(p.getAccount());
        programField.setSelectedIndex(profileList.size() - 1);
        usernameField.setText(p.getUsername());
        passwordField.setText(p.getPassword());
        noteField.setText(p.getNotes());
    }
    
    /*Takes an arraylist of Profiles 'pl' and gets/updates a specific profile selected by the user*/
    public void editProfile(ArrayList<Profile> pl){
        Profile p;
        int selected;
        int i;
        
        selected = programField.getSelectedIndex();
        p = pl.get(selected);
        programField.removeAllItems();
        
        i = 0;
        while (i < pl.size()){
            programField.addItem(pl.get(i).getAccount());
            i++;
        }
        
        programField.setSelectedIndex(selected);
        usernameField.setText(p.getUsername());
        passwordField.setText(p.getPassword());
        noteField.setText(p.getNotes());
    }
    
    /*Saves the currently opened file*/
    public void saveFile(){
        int i;
        int result;
        Profile p;
        String filePath;
        String name;
        
        /*Initialize the file chooser and get input from user*/
        saveChooser = new JFileChooser();
        saveChooser.setFileFilter(filter);
        saveChooser.setSelectedFile(new File ("file.enc"));
        result = saveChooser.showSaveDialog(this);
        
        /*If the user confirms their save location*/
        if (result == JFileChooser.APPROVE_OPTION){
            try{
                filePath = saveChooser.getSelectedFile().toString();
                /*If the filename does not proper extension, if not gives it one*/
                if (filePath.length() < 4 || filePath.endsWith(".enc") == false){
                    filePath = filePath.concat(".enc");
                    name = saveChooser.getSelectedFile().getName() + ".enc";
                }
                else{
                    name = saveChooser.getSelectedFile().getName();
                }
                
                /*Update UI to represent new saved filename*/
                fileName = name;
                title.setTitle(name);
                repaint();
                
                /*Ready the file IO*/
                fw = new FileWriter(filePath);
                bw = new BufferedWriter(fw);
                i = 0;
                bw.write(Integer.toString(profileList.size()) + "\r\n\r\n");
                
                /*Loops through each Profile in the array, encrypting and outputting it to the file*/
                while (profileList.size() > i){
                    p = profileList.get(i);
                    //bw.write(p.getAccount() + "\r\n" + p.getUsername() + "\r\n" + p.getPassword() + "\r\n" + p.getNotes() + "\r\n\r\n");
                    bw.write(encrypt(p.getAccount(), "test") + "\r\n" + encrypt(p.getUsername(), "test") + "\r\n" + encrypt(p.getPassword(), "test") + "\r\n" + encrypt(p.getNotes(), "test") + "\r\n\r\n");
                    //System.out.print(encrypt(p.getAccount(), "test") + "\r\n" + encrypt(p.getUsername(), "test") + "\r\n" + encrypt(p.getPassword(), "test") + "\r\n" + encrypt(p.getNotes(), "test") + "\r\n\r\n" );
                    i++;
                }
                bw.close();
            }
            catch(IOException e){
                
            }
        }
    }
    
    /*Opens an existing .enc file*/
    public void openFile(){
        int i;
        int result;
        int profileCount;
        String filePath;
        String name;
        String acc;
        String user;
        String pass;
        String note;
        Profile p;
        
        /*Initialize file chooser and get input from user*/
        openChooser = new JFileChooser();
        openChooser.setFileFilter(filter);
        result = openChooser.showOpenDialog(this);
        
        /*If a file is selected*/
        if (result == JFileChooser.APPROVE_OPTION){
            try{
                /*Set UI varaibles to properly display the opened files name and reset global varaibles to empty states*/
                filePath = openChooser.getSelectedFile().toString();
                name = openChooser.getSelectedFile().getName();
                title.setTitle(name);
                fileName = openChooser.getSelectedFile().getName();
                repaint();
                profileList = new ArrayList<>();
                programField.removeAllItems();
                titlePanel.setVisible(true);
                saveAsFile.setEnabled(true);
                
                /*Ready file IO*/
                fr = new FileReader(filePath);
                br = new BufferedReader(fr);
                
                /*Read in how many profiles are stored in the file*/
                profileCount = Integer.parseInt(br.readLine());
                br.readLine();
                
                /*Loop through each stored profile, storing data in the array*/
                i = 0;
                while(profileCount > i){
                    /*Decrypt the data and store it*/
                    acc = encrypt(br.readLine(), "test");
                    user = encrypt(br.readLine(), "test");
                    pass = encrypt(br.readLine(), "test");
                    note = encrypt(br.readLine(), "test");
                    br.readLine();
                    p = new Profile(acc, user, pass, note);
                    profileList.add(p);
                    addProfile(p);
                    i++;
                }
                /*Set the currently views profile as the first profile element if theres more than one*/
                if (profileCount > 0){
                    p = profileList.get(0);
                    programField.setSelectedIndex(0);
                    usernameField.setText(p.getUsername());
                    passwordField.setText(p.getPassword());
                    noteField.setText(p.getNotes());
                }
            }
            catch(IOException e){
                
            }
        }
    }
    
    /**/
    public char[] createCode(){
        char[] code = new char[15];
        int res;
        Random r = new Random();
        
        for (int i = 0 ; i < 15 ; i++){
            res = r.nextInt(90) + 32;
            passcode[i] = (char)res;
        }
        passcode[15] = '\0';
        displayCode(code);
        return code;
    }
    
    /**/
    public void displayCode(char [] code){
        String str = new String(code);
        System.out.println(str + "\r\n");
        for (int i = 0 ; i < 15 ; i++){
            System.out.print(passcode[i]);
        }
        JOptionPane.showMessageDialog(this, "Here is your access code. Make sure to copy it:\r\n\r\n " + String.valueOf(code));
    }
    
    /*Encrypt or decrypt the string 'text' using the string 'key' and return it*/
    public String encrypt(String text, String key){
        int lenText = text.length();
        int lenKey = key.length();
        char[] textArr = text.toCharArray();
        char[] keyArr = key.toCharArray();
        int a, b, res;
        
        /*Loop through each char in the 'text' and use XOR cipher with the next 'key' char*/
        for (int i = 0, j = 0 ; i < lenText ; i++){
            /*If the end of the 'key' string is reached loop around to start*/
            if (j >= lenKey)
                j = 0;
            a = textArr[i];
            b = keyArr[j];
            /*XOR bit shift*/
            res = a ^ b;
            textArr[i] = (char)(res);
            j++;
        }
        /*Create the new string and return it*/
        text = new String(textArr);
        return text;
    }
}
