
import java.util.Scanner;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class User {

	private Scanner scanner = new Scanner(System.in);
	private static final String USER_TXT = "user.txt";
    private String userID;
    private String name;
    private String email;
    private String password;
    private String phoneNo;
    private String userType;
    

    //getter 
    public String getUserID() {return userID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhoneNo() { return phoneNo; }
    public String getUserType() { return userType; }
    

    //setter
    public void setUserID(String userID) { this.userID = userID;  }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    public void setUserType(String userType) { this.userType = userType; }

    //constructor
    public User() {
        userID = "";
        name = "";
        email = "";
        password = "";
        phoneNo = "";

    }
    public User(String userID, String name, String email, String password, String phoneNo, String userType) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.userType = userType;
    }
    
    //create a user.txt
    public void saveToFile() {
        List<User> allUsers = getAllUsers();
        int userIndex = -1;
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).userID.equals(this.userID)) {
                userIndex = i;
                break;
            }
        }
        if (userIndex >= 0) {
            allUsers.set(userIndex, this); 
            // replace the old user info with the updated one
        } else {
            allUsers.add(this); 
            // add a new user if it doesn't exist in the list
        }
        saveAllUsers(allUsers);
    }

    //read user data from a file
    private static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(USER_TXT))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] userData = line.split(" , ");
                if (userData.length < 6) {
                    continue;
                }
                User user = new User(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5]);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    //save "User" object to the user.txt
    private static void saveAllUsers(List<User> users) {
        try (FileWriter fw = new FileWriter(USER_TXT, false)) {
            for (User user : users) {
                fw.write(user.userID + " , " + user.name + " , " + user.email + " , " + user.password + " , " + user.phoneNo + " , " + user.userType + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // showing the user information from the user.txt
    public void showProfile() {  
        System.out.println("User Profile:");
        System.out.println("userID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone Number: " + phoneNo);
        System.out.println("User Type: " + userType);
    }


    // update user information to the user.txt
    public void updateProfile() {
        System.out.println("Which one you want to change?");
        System.out.println("1. Email");
        System.out.println("2. Password");
        System.out.println("3. Phone Number");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String updateEmail, updatePhoneNo;
        
        switch (choice) {
            case 1:
                System.out.print("Enter your new email: ");
                changeEmail(updateEmail = scanner.nextLine());
                updateProfile();
                break;
            case 2:
                changePassword();
                updateProfile();
                break;
            case 3:
                System.out.print("Enter your new phone number: ");
                changePhoneNo(updatePhoneNo = scanner.nextLine());
                updateProfile();
                break;
            case 4:
                MainTest.login(); 
               break;
            default:
                System.out.println("Invalid choice.");
                updateProfile();
                break;
        }
    }
    
    public void changeEmail(String newEmail) {
        this.email = newEmail;
        saveToFile();
        System.out.println("Email updated successfully!");
    }
    public void changePhoneNo(String newPhoneNo) {
        this.phoneNo = newPhoneNo;
        saveToFile();
        System.out.println("Phone Number updated successfully!");
    }
    public void changePassword() {
        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();

        if (this.password.equals(currentPassword)) {
            while (true) { // Loop until the user correctly verifies their new password
                System.out.print("Enter your new password (4 digits and 4 letters): ");
                String newPassword = scanner.nextLine();
                if (newPassword.length() == 8 &&
                        newPassword.chars().filter(Character::isDigit).count() == 4 &&
                        newPassword.chars().filter(Character::isLetter).count() == 4) {
                        password = newPassword;
                        System.out.print("Re-enter your new password for verification: ");
                        String verifyPassword = scanner.nextLine();
                        
                        if (newPassword.equals(verifyPassword)) {
                            this.password = newPassword;
                            saveToFile();
                            System.out.println("Password updated successfully!");
                            break;
                        } else {
                            System.out.println("Passwords do not match. Please try again.");
                        }
                        //break; // IF the password consist of 4digit and 4letter, exit the loop
                    } else {
                        System.out.println("Password must consist of 8 digits, 4 digits, and 4 letters.");
                    }

                
                
                
            }
        } else {
            System.out.println("Incorrect current password.");
            changePassword();
        }
    }
    public double getDiscountRate() {
        return 0; // no discount normal user
    }
    public Booking bookTicket(User aUser) {
        return new Booking(aUser);
    }

}

