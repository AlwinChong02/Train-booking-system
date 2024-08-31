import java.util.*; // For using Classes: Date, Scanner, ArrayList.
import java.sql.Time; // For Time(it is also inherited from Date class)
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.io.File;  // For File Handling and IOExecption
import java.io.FileWriter;
import java.io.IOException;


/* Code References for Java Library if need help (java.util.* or java.sql.Time)
 *
 * Date: https://docs.oracle.com/javase/8/docs/api/java/util/Date.html
 * Time: https://docs.oracle.com/javase/8/docs/api/java/sql/Time.html
 * Scanner: https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html
 * ArrayList: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html, https://www.w3schools.com/java/java_arraylist.asp
 * 
 */


public class MainTest {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        User user = null;
  
        while (true) {
            System.out.println("=====================================");
            System.out.println("ETS Booking System(ETS) Main Menu:");
            System.out.println("=====================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
            case 1:
                user = register();
                break;
            case 2:
                user = login();
                if (user != null) {
                    
                    System.out.println("Login successful!");
                    while (true) {
                        System.out.println("=====================================");
                        System.out.println("Welcome to ETS Online Ticketing System!");
                        System.out.println("=====================================");
                        System.out.println("1. Book Ticket");
                        System.out.println("2. Show Profile");
                        System.out.println("3. Update Your Profile Information");
                        System.out.println("4. View Ticket");
                        System.out.println("5. FAQ");
                        System.out.println("6. Make a Review");
                        System.out.println("7. Logout");
                        System.out.print("Enter your choice: ");

                        int choice1 = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        switch (choice1) {
                            case 1:
                                user.bookTicket(user);  
                                break;
                            case 2:
                                user.showProfile();  
                                break;
                            case 3:
                                user.updateProfile(); 
                                break;
                            case 4:
                                Booking booking = new Booking(user,"bookingList.txt"); 
                                break;
                            case 5:
                                FAQ();
                                break;
                            case 6:
                                makeReview(user); 
                                break;
                            case 7:
                                System.out.println("Logging out...");
                                user = null;
                                return;
                            default:
                                System.out.println("Invalid choice. Please enter a valid option.");
                        }
                    }
                }  else {
                    System.out.println("Login failed!");
                }
                break;
            case 3:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    //static methods
    public static User register(){

        String userID, name, email, password, phoneNo, userType;

        Scanner scanner = new Scanner(System.in);
        while (true) {
        	System.out.print("Create your userID: ");
            userID = scanner.nextLine();
        	System.out.print("Enter your name: ");
            name = scanner.nextLine();
            System.out.print("Enter your email: ");
            String enteredEmail = scanner.nextLine();

            // Check if the entered email contains both "@" and "."
            if (enteredEmail.contains("@") && enteredEmail.contains(".")) {
                email = enteredEmail;
                break; // if the email match the requirement, exit the loop
            } else {
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        }

        while (true) {
            System.out.print("Enter your password(4 digits and 4 letters): ");
            String enteredPassword = scanner.nextLine();

            // Check if the password contains 8 characters with 4 digit and 4 letter characters
            if (enteredPassword.length() == 8 &&
                enteredPassword.chars().filter(Character::isDigit).count() == 4 &&
                enteredPassword.chars().filter(Character::isLetter).count() == 4) {
                password = enteredPassword;
                break; // IF the password consist of 4digit and 4letter, exit the loop
            } else {
                System.out.println("Password must consist of 8 digits, 4 digits, and 4 letters.");
            }
        }

        System.out.print("Enter your phone number: ");
       phoneNo = scanner.nextLine();

        //user select category between OKU, Student, Senior Citizen) //put in 
        System.out.print("Select your user category:\n" +
                        "===============================\n" +
                         "1. OKU\n" +
                         "2. Student\n" +
                         "3. Senior Citizen(55 y.o. or above)\n" +
                         "4.None of above\n" +
                         "Enter your choice: ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        User user = null;

        if (categoryChoice == 1) {
            user =  new User(userID, name, email, password, phoneNo, "OKU");
        } else if (categoryChoice == 2) {
            user = new User(userID, name, email, password, phoneNo, "Student");
        } else if (categoryChoice == 3) {
            user =  new User(userID, name, email, password, phoneNo, "Senior Citizen");
        } else if (categoryChoice == 4) {
            user =  new User(userID, name, email, password, phoneNo, "User");
        } else {
            System.out.println("Invalid choice. Please enter a valid option.");
            register();
        }
        
        saveToFile(user); // Save the user data to the file after registration
        System.out.println("User registered successfully!");
    
        return user;
    }
    public static User login() {

        User verifiedUser = null;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your userID: ");
        String inputUserID = scanner.nextLine();

        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine();

        List<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            if (user.getUserID().equals(inputUserID) && user.getPassword().equals(inputPassword)) {

                verifiedUser = user; // Successful login
                break;
            }
        }
            if (verifiedUser == null) {
                System.out.println("Invalid userID or password.");   
            }
        return verifiedUser;
    }
    
    //create a user.txt
    public static void saveToFile(User user) {
        List<User> allUsers = getAllUsers();
        int userIndex = -1;
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUserID().equals(user.getUserID())) {
                userIndex = i;
                break;
            }
        }
        if (userIndex >= 0) {
            allUsers.set(userIndex,user); 
            // replace the old user info with the updated one
        } else {
            allUsers.add(user); // add a new user if it doesn't exist in the list    
        }
        saveAllUsers(allUsers);
    }

    //read user data from a file
    private static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("user.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] userData = line.split(" , ");
                if (userData.length < 6) {
                    continue;
                }
                User user = new User(userData[0], userData[1], userData[2], userData[3], userData[4],userData[5]);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    //save "User" object to the user.txt
    private static void saveAllUsers(List<User> users) {
        try (FileWriter fw = new FileWriter("user.txt", false)) {
            for (User user : users) {
                fw.write(user.getUserID() + " , " + user.getName() + " , " + user.getEmail() + " , " + user.getPassword() + " , " + user.getPhoneNo() + " , " + user.getUserType() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void FAQ() {
		Scanner scanner = new Scanner(System.in);

		// FAQ questions
		String[] question = { 
				/* Q1 */"What is the Electric Train Service (ETS) Ticketing System?",
				/* Q2 */"With the launch of ETS Ticketing System, what are the ticket purchase mediums available?",
				/* Q3 */"What are the methods of purchasing tickets through ETS Ticketing System?",
				/* Q4 */"What information is required during registration?",
				/* Q5 */"What are the benefits of registering with ETS Ticketing System?",
				/* Q6 */"Who is eligible to register for concession fares?",
				/* Q7 */"How can customers register for concession tickets?",
				/* Q8 */"What supporting documents are required?", 
				/* Q9 */"What are the ticket payment methods?",
				/* Q10 */"Can ticket purchases be made if I am not registered with ETS Ticketing System?",
				/* Q11 */"What is the maximum number of tickets allowed per transaction?",
				/* Q12 */"Can purchased tickets be canceled?", 
				/* Q13 */"Can tickets be amended or transferred?",
				/* Q14 */"How can ticket cancellation be done?",
				/* Q15 */ "Are refunds for travel fare cancellations given in cash?",
				/* Q16 */"How long will the fare refunds take to be credited to the customer's bank or credit card account?" };

		// FAQ answers
		String[] answers = {
				/* A1 */ "The Electric Train Service (ETS) Ticketing System is a new ticketing system introduced for purchasing tickets for ETS (Electric Train Service).",
				/* A2 */ "Tickets can be purchased through the ETS Mobile app and the ETS website.",
				/* A3 */"Before making a purchase, customers need to register through ETS Ticketing System. Customers without email accounts must create an email account first.",
				/* A4 */"The required information includes:\n" + "i- Email\n" + "ii- Password\n" + "iii- Name as on MyKad (Malaysian identity card)\n" + "iv- IC\n" + "v- Mobile phone number",
				/* A5 */"Registered customers enjoy benefits such as:\n" + "i- Concession fares\n" + "ii- Online ticket cancellation\n" + "iii- View ticket history",
				/* A6 */"i- Students\n" + "ii- OKU\n" + "iii- Senior Citizens aged 60 and above",
				/* A7 */"Customers need to log in to the ETS Ticketing System through the ETS website www.ets.com.my.",
				/* A8 */"Please refer to the supporting documents as listed below:\n" + "Pelajar - MyKad & Surat Pengesahan Pelajar\n" + "Orang Kurang Upaya (OKU) - Kad OKU JKM\n" + "Warga Emas 60 tahun dan ke atas - Mykad",
				/* A9 */"Ticket payments can be made in credit cards, debit cards, and online booking.",
				/* A10 */ "No. Customers need to register themselves before making a purchase.",
				/* A11 */"Maximum of 5 tickets per account", 
                /* A12 */"Yes.",
				/* A13 */"No, ticket amendments are not allowed, including changes to travel dates, departure times, train numbers, or passenger details.",
				/* A14 */"Ticket cancellation can be done through:\n" + "i. ETS website\n" + "ii. ETS Mobile app",
				/* A15 */"No, travel fare refunds will be credited to the customer's bank or credit card account.",
				/* A16 */"Fare refunds are credited to the customer's bank or credit card account within 3 working days after the claim process is completed." };

		System.out.println("Frequently Asked Question (FAQ):");
		// Display Questions
		for (int i = 0; i < question.length; i++) {
			System.out.println((i + 1) + ". " + question[i]);
		}

		int chosenQuestion;

		// Get Questions Number from User
		try {
			while (true) {
				do {
					System.out.print("Enter the FAQ number to view the answer: ");
					chosenQuestion = scanner.nextInt();

					// Display Answers
					if (chosenQuestion > 0 && chosenQuestion <= question.length) {
						System.out.println("\nAnswer:\n" + answers[chosenQuestion - 1]);
					} else {
						System.out.println("Invalid FAQ number. Please enter a valid FAQ number.");
					}
				} while (chosenQuestion <= 0 || chosenQuestion > question.length);

				Scanner input = new Scanner(System.in);
				System.out.println("\nWould you like to view other FAQ answer? (Yes/No)");
				String anotherQuestion = scanner.next();
				if (!anotherQuestion.equalsIgnoreCase("yes")) {
					break; // Exit loop if the answer is not "yes"
				}
			}
			System.out.println("Thank you. Wait for a second, we will lead you to the main page..........");
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}

	public static void makeReview(User aUser) {
		try {
			Scanner scanner = new Scanner(System.in);

			System.out.println("ETS Ticketing System Review");
			System.out.println(
					"**Please rate the following statements on a scale of 1 (Strongly Disagree) to 5 (Strongly Agree):**");

			// Define review questions
			String[] questions = { "Overall, I am satisfied with my recent experience using ETS services.",
					"The ETS ticketing system was easy to use and convenient.",
					"The cleanliness and maintenance of ETS train compartments are satisfactory.",
					"The ETS staff members were helpful and courteous during my journey.",
					"The onboard amenities and facilities, including seating comfort, restrooms, and Wi-Fi (if available), are up to the mark.",
					"I encountered issues with the ETS Mobile app or website when booking tickets or checking schedules.",
					"I have used ETS concession fares and found the process satisfactory.",
					"I am likely to recommend ETS services to others based on my recent experiences." };

			List<Review> reviews = new ArrayList<>();

			while (true) {
				System.out.println("New Customer Review");
				System.out.print("Your Name: " + aUser.getName() + "\n");
	
				int[] newRatings = new int[questions.length];
				for (int i = 0; i < questions.length; i++) {
					System.out.println(questions[i]);
					System.out.print("Rating (1-5): ");
					int rating = scanner.nextInt();

					// Validate rating input
					if (rating < 1 || rating > 5) {
						System.out.println("Invalid rating. Please enter a rating between 1 and 5.");
						i--; // Repeat the same question
					} else {
						newRatings[i] = rating;
					}
				}

				scanner.nextLine(); // Consume the newline character
				System.out.print("Enter your review comment (optional): ");
				String comment = scanner.nextLine();

				Review newReview = new Review(aUser, questions); // Pass the user's name
				for (int i = 0; i < newRatings.length; i++) {
					newReview.setRating(i, newRatings[i]);
				}
				newReview.setComment(comment);
				newReview.addReview();

				reviews.add(newReview);

				System.out.println("Average Rating: " + Review.computeAverageRating(reviews));

				System.out.print("Do you want to record another customer review? (yes/no): ");
				String anotherReview = scanner.next();
				if (!anotherReview.equalsIgnoreCase("yes")) {
					break; // Exit loop if the answer is not "yes"
				}
			}
			System.out.println("Thank you for collecting customer reviews!");

		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}
}
