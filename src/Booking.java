
import java.util.*; // For Date
import java.io.*; //File and PrintWriter
import java.sql.Time; // For Time(it is also inherited from Date class)
import java.text.*; //DateFormat and SimpleDateFormat
import java.lang.Math; //Math.abs
import java.time.*;


public class Booking {
    //instance variables
    private String bookingID;
    protected User bookingUser;
    private Train bookingTrain;
    private TrainStation departureStation;
    private TrainStation arrivalStation;
    private String bookingDate;
    private String bookingTime; 
    private String bookingStatus; //PENDING, BOOKED or CANCELLED
    private double paymentAmount;
    public ArrayList<String> Max5Seats;
    public static ArrayList<Booking> bookings = new ArrayList<Booking>();

    //getter
    public String getbookingID() { return bookingID; }
    public User getBookingUser() { return bookingUser; }
    public Train getBookingTrain() { return bookingTrain; }
    public TrainStation getDepartStation() { return departureStation; }
    public TrainStation getArrivalStation() { return arrivalStation; }
    public String getBookingDate() { return bookingDate; }
    public String getBookingTime() { return bookingTime; }
    public String getBookingStatus() { return bookingStatus; }
    public double getPaymentAmount() { return paymentAmount; }

    //setter
    public void setbookingID(String bookingID) { this.bookingID = bookingID; }
    public void setDepartStation(TrainStation departureStation) { this.departureStation = departureStation; }
    public void setArrivalStation(TrainStation arrivalStation) { this.arrivalStation = arrivalStation; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
    public void setBookingTime(String bookingTime) { this.bookingTime = bookingTime; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
    public void setPaymentAmount(double paymentAmount) { this.paymentAmount = paymentAmount; }

    //constuctor(s)
    public Booking(User aBookingUser, String filename){ //for view ticket from MainTest
        bookingID = "";
        bookingUser = aBookingUser;
        bookingTrain = new Train();
        departureStation = new TrainStation();
        arrivalStation = new TrainStation();
        bookingDate = "";
        bookingTime = "";
        bookingStatus = "";
        paymentAmount = 0.0;
        Max5Seats = new ArrayList<String>();

        //read bookingList.txt, verify userID from aBookingUser and store the data into data instance variables
        try{
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] lineArray = line.split(",");
                
                if(lineArray[3].equals(aBookingUser.getUserID())){
                    bookingID = lineArray[0];
                    bookingDate = lineArray[1];
                    bookingTime = lineArray[2];
                    bookingUser.setUserID(lineArray[3]);
                    bookingUser.setName(lineArray[4]);
                    bookingUser.setEmail(lineArray[5]);
                    bookingUser.setPhoneNo(lineArray[6]);
                    bookingTrain.setTrainID(lineArray[7]);
                    departureStation.setStationLocation(lineArray[8]);
                    departureStation.setStationDepartTime1(lineArray[9]);
                    arrivalStation.setStationLocation(lineArray[10]);
                    arrivalStation.setStationDepartTime1(lineArray[11]);
                    paymentAmount = Double.parseDouble(lineArray[12]);

                    for(int i = 13; i < lineArray.length ; i++){
                        // if line contain [], remove it
                        if(lineArray[i].contains("[")){
                            lineArray[i] = lineArray[i].replace("[", "");
                            
                        } 
                        if(lineArray[i].contains("]")){
                            lineArray[i] = lineArray[i].replace("]", "");
                           
                        }
                        Max5Seats.add(lineArray[i]);
                    }
                    
                    viewTicket();
                    Max5Seats.clear();
                }
            }
        } catch(Exception e){
                System.out.println("Error: " + e.getMessage());
    }
        for (Booking booking : bookings) {
            booking.viewTicket();
            System.out.println("====================================");
        }
    }
            
    public Booking(User aBookingUser) {
        //bookings.add(this);
        this.bookingUser = aBookingUser;
        this.bookingID = UUID.randomUUID().toString().substring(0,8);   //generate randomised 8 characters for booking ID
        this.bookingTrain = new Train();
        //this.bookingTrain = aTrain;                   //set user object to bookingUser
        this.Max5Seats = new ArrayList<String>();
        this.departureStation = new TrainStation();
        this.arrivalStation = new TrainStation();

        ArrayList<User> userList = new ArrayList<User>();    //create userList to store all the user objects
        try {     // Read the file and store the data into the userList
            File fileUser = new File("user.txt");
            Scanner fileScanner = new Scanner(fileUser);
            if (fileUser.exists() && fileUser.canRead() && fileUser.canWrite()){
                
                while (fileScanner.hasNextLine()) {

                    String line = fileScanner.nextLine();
                    User user = new User();
                    String[] lineArray = line.split(",");
                    user.setUserID(lineArray[0]);
                    user.setName(lineArray[1]);
                    user.setEmail(lineArray[2]);
                    user.setPassword(lineArray[3]);
                    user.setPhoneNo(lineArray[4]);
                    user.setUserType(lineArray[5]);
                    userList.add(user);
                    //}
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        for(User user : userList){
            if(user.getUserID().equals(this.bookingUser.getUserID())){
                    this.bookingUser = new User();
                    this.bookingUser.setUserID(user.getUserID());
                    this.bookingUser.setName(user.getName());
                    this.bookingUser.setEmail(user.getEmail());
                    this.bookingUser.setPassword(user.getPassword());
                    this.bookingUser.setPhoneNo(user.getPhoneNo());
                    this.bookingUser.setUserType(user.getUserType());
                    break;
               // }
            }
        }
        //System.out.println(bookingUser.getClass());

        ArrayList<TrainStation> trainStationList = new ArrayList<TrainStation>(); //create trainStationList to store all the trainStation objects
         try {    // Read the file and store the data into the trainStationList 
             File fileTS = new File("trainStation.txt");
             if (fileTS.exists() && fileTS.canRead() && fileTS.canWrite()){
                    Scanner fileScanner = new Scanner(fileTS);
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        TrainStation trainStation = new TrainStation();
                        String[] lineArray = line.split(",");
                        trainStation.setStationID(Integer.parseInt(lineArray[0]));
                        trainStation.setStationLocation(lineArray[1]);
                        trainStation.setStationDepartTime1(lineArray[2]);
                        trainStation.setStationDepartTime2(lineArray[3]);
                        trainStationList.add(trainStation);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        
       //display train schedule
       this.bookingTrain.displayTrainSchedule();

        Scanner scanner = new Scanner(System.in);
        int arrivalStationNo, departStationNo;
        
        do{
        //ask user to choose departure station
          while(true){   
            System.out.print("Please enter your departure station ID: ");
            departStationNo = scanner.nextInt();

            if((departStationNo <= 5006) && (departStationNo >= 5001)){
                this.departureStation.setStationID(departStationNo);
                break;
            }
            else 
            System.out.println("Invalid input. Please try again.");
  
        }
        
        //ask user to choose arrival station
          while(true){   
            System.out.print("Please enter your arrival station ID: ");
            arrivalStationNo = scanner.nextInt();

            if((arrivalStationNo <= 5006) && (arrivalStationNo >= 5001)){
                this.arrivalStation.setStationID(arrivalStationNo);
                break;
            }
            else 
            System.out.println("Invalid input. Please try again.");
        }

        //assign departure station and arrival station to departureStation and arrivalStation object
        for(int i = 0; i < trainStationList.size(); i++) {
            if(departStationNo == trainStationList.get(i).getStationID()) {
                this.departureStation = trainStationList.get(i);
            }
            if(arrivalStationNo == trainStationList.get(i).getStationID()) {
                this.arrivalStation = trainStationList.get(i);
            }
        }
        
        if(arrivalStationNo > departStationNo){
                this.bookingTrain.setTrainID("T001");
                System.out.println("Train ID: " + this.bookingTrain.getTrainID());
                break;
            }
        else if(arrivalStationNo < departStationNo){
                this.bookingTrain.setTrainID("T002");
                System.out.println("Train ID: " + this.bookingTrain.getTrainID());
                break;
            }
        else {System.out.println("Please re-enter your departure and arrival station.");
            }
        }while(arrivalStationNo == departStationNo);

        //display booking page
        bookTicket();
        if(this.bookingStatus.toUpperCase().equals("PENDING")) {
            System.out.print("Proceeding to payment page...");
            // for(int i = 0; i < 10; i++) {
            //     System.out.print("#");
            //         try {
            //         Thread.sleep(500);
            //         } catch (Exception e) {
            //            System.out.println( "Error: " + e.getMessage());
            //         }
            // }
            System.out.println();
            makePayment();
        }
        else 
            System.out.println("----Booking Invalid.----");}
            


    
    //methods
    public void bookTicket() {
        
        Scanner scan = new Scanner(System.in);

        //ask user how many seats to book
        System.out.println("How many seats you want to book? (Max 5 seats)");
        int seatNum = scan.nextInt();

        //check if the seat number is valid, try...catch is used to avoid typing other than number incident
        try {
            if(seatNum > 5 || seatNum < 1 )
                {System.out.println("Invalid input. Please try again.");
                bookTicket();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please try again.");
            bookTicket();
            }

            int i = 1;
            int seatRow, seatNumber;
            do{
                
                bookingTrain.checkSeatAvailability(bookingTrain.getTrainID());

                while(true){
                    System.out.print("Enter the seat row number (0-19): ");
                    seatRow = scan.nextInt();
                    if((seatRow <= 19) && (seatRow >= 0)){
                        break;
                    } else 
                        System.out.println("Invalid input. Please try again.");
                }

                while(true) {
                    System.out.print("Enter the seat number (0-3): ");
                    seatNumber = scan.nextInt();
                    if((seatNumber <= 3) && (seatNumber >= 0)){
                        break;
                    } else 
                        System.out.println("Invalid input. Please try again.");
                }
                bookingTrain.reserveSeat(seatRow, seatNumber);
                Max5Seats.add(seatRow + "" + seatNumber);
                i++;
                bookingTrain.saveSeatToFile(bookingTrain.getTrainID());
            }while (i <= seatNum);
        System.out.println("Your seat(s): " + this.Max5Seats + "\n\n");


        //display booking page
        System.out.println("===========================================");
        System.out.println("||\tYou are entering Booking page\t||");
        System.out.println("===========================================");
        System.out.println("#Reminder: Your ticket is valid for tomorrow's journey.                                  #");
        System.out.println("#          You cannot make any cancellation and refunds after successful payment.        #");
        System.out.println("booking ID: #" + this.bookingID);
        System.out.println("Train ID: " + this.bookingTrain.getTrainID());
        System.out.println("Your seat number: " + this.Max5Seats );

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");  
        bookingDate = sdf.format(new Date());
        System.out.print("Your booking date: "  + this.bookingDate );
        DateFormat df = new SimpleDateFormat("hh:mm:ss a");
        bookingTime = df.format(new Date());
        System.out.println("\tTime: "  + this.bookingTime);
        System.out.println("Departure Station: " + this.departureStation.getStationLocation() + "\tArrival Station: " + this.arrivalStation.getStationLocation());
        if (arrivalStation.getStationID() > departureStation.getStationID())
            System.out.println("Departure Time: " + this.departureStation.getStationDepartTime1() + "\tArrival Time: " + this.arrivalStation.getStationDepartTime1());
        else
        System.out.println("Departure Time: " + this.departureStation.getStationDepartTime2() + "\t\tArrival Time: " + this.arrivalStation.getStationDepartTime2());
        
        

        //calculate payment amount with discount rate based on user type
        setPaymentAmount(((double)seatNum) * 20 * ((double)(Math.abs(this.arrivalStation.getStationID() - this.departureStation.getStationID()))));
        System.out.println("User Type: " + bookingUser.getUserType());
        if(bookingUser.getUserType().equals("OKU")) {
            paymentAmount = 0.3 * getPaymentAmount();
            System.out.println("Discount Rate: 70%");
            System.out.println("Payment Amount: " + "RM" + paymentAmount);
        }
        else if(bookingUser.getUserType().equals("Student")) {
            paymentAmount = 0.4 * getPaymentAmount();
            System.out.println("Discount Rate: 60%");
            System.out.println("Payment Amount: " + "RM" + paymentAmount);
        }
        else if(bookingUser.getUserType().equals("Senior Citizen")) {
            paymentAmount = 0.5 * getPaymentAmount();
            System.out.println("Discount Rate: 50%");
            System.out.println("Payment Amount: " + "RM" + paymentAmount);
        }
        else {
            paymentAmount = getPaymentAmount();
            System.out.println("Discount Rate: 0%");
            System.out.println("Payment Amount: " + "RM" + paymentAmount);
        }   System.out.println(bookingUser.getUserType());

        setBookingStatus("PENDING");
        // scan.close();
    }

    public boolean viewTicket() {
        System.out.println("====================================");
        System.out.println("\tTicket Infomation\t\t");
        System.out.println("====================================");
        System.out.println("Booking ID: #" + bookingID);
        System.out.println("Booking Date: " + bookingDate + "\tTime: " + bookingTime);
        System.out.println("Name: " + bookingUser.getName());
        System.out.println("Email: " + bookingUser.getEmail());
        System.out.println("Phone Number: " + bookingUser.getPhoneNo());
        System.out.println("Train ID: "+ bookingTrain.getTrainID());
        System.out.println("Booked Seat(s): " + Max5Seats);
        System.out.println("Departure Station: " + this.departureStation.getStationLocation() + "\tArrival Station: " + this.arrivalStation.getStationLocation());
        System.out.println("Departure Time: " + departureStation.getStationDepartTime1() + "\tArrival Time: " + arrivalStation.getStationDepartTime1());
        System.out.println(" ");

        return true;
    }

    public void makePayment() {
        
        //display payment info with payment method and tell user to pay
        System.out.println("Payment Amount: "  + "RM" + this.paymentAmount);
        System.out.println("Select your Payment Method: ");
        System.out.println("1. Credit/Debit Card");
        System.out.println("2. Online Banking");
        System.out.println("3. Cancel Payment");
        

        Scanner ar = new Scanner(System.in);  
        System.out.print("Enter your payemnt method: ");
        String paymentMethod = ar.next();
        // ar.close();

        switch(paymentMethod) {
            case "1":
                System.out.println("Proceeding to Credit/Debit Card payment page...");
                for(int i = 0; i < 10; i++) {
                    System.out.print("##");
                        try {
                        Thread.sleep(5);
                             } catch (Exception e) {
                               System.out.println( "Error: " + e.getMessage());
                            }
                }
                System.out.println("\n");
                System.out.println("Payment Successful!");
                System.out.println("Thank you for using our service!");
                System.out.println("Your booking status is now: " + "Booked" );
                setBookingStatus("BOOKED");
                writeBookingFile();
                updateSeatFile();
                break;
            case "2":
                System.out.println("Proceeding to Online Banking payment page...");
                for(int i = 0; i < 10; i++) {
                    System.out.print("##");
                        try {
                        Thread.sleep(500);
                             } catch (Exception e) {
                               System.out.println( "Error: " + e.getMessage());
                            }
                }
                System.out.println("");
                System.out.println("Payment Successful!");
                System.out.println("Thank you for using ETS service!");
                System.out.println("Your booking status is now: " + "Booked" );
                setBookingStatus("BOOKED");
                writeBookingFile();
                updateSeatFile();
                break;
            case "3":
                System.out.println("");
                System.out.println("Payment Cancelled!");
                System.out.println("Your booking status is now: " + "Cancelled" );
                setBookingStatus("CANCELLED");
                break;
            default:
                System.out.println("Invalid input. Please try again.");
                makePayment();
                break;
        }
        return;
    }
    //read from text file (T001_Seat.txt or T002_Seat.txt) and update the available seat into reserved seat booked from Max5Seats
    public void updateSeatFile(){
        try{
            File file = new File(this.bookingTrain.getTrainID() + "_Seats.txt");
            Scanner scanner = new Scanner(file);
            ArrayList<String> seatList = new ArrayList<String>();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] lineArray = line.split(",");
                seatList.add(lineArray[0]);
            }
            for(int i = 0; i < seatList.size(); i++){
                for(int j = 0; j < Max5Seats.size(); j++){
                    if(seatList.get(i).equals(Max5Seats.get(j))){
                        seatList.set(i, seatList.get(i) + ",Reserved");
                    }
                }
            }
            FileWriter fw = new FileWriter(this.bookingTrain.getTrainID() + "_Seats.txt", false);
            PrintWriter pw = new PrintWriter(fw);
            for(int i = 0; i < seatList.size(); i++){
                pw.println(seatList.get(i));
            }
            pw.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void writeBookingFile(){
        try{
            //save all booking details from all instance variables to bookingList.txt
            FileWriter fw = new FileWriter("bookingList.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            if(this.arrivalStation.getStationID() > this.departureStation.getStationID()){
                pw.println(this.bookingID + "," + this.bookingDate + "," + this.bookingTime + "," + this.bookingUser.getUserID() + "," + this.bookingUser.getName() + "," 
                + this.bookingUser.getEmail() + "," + this.bookingUser.getPhoneNo() + "," + this.bookingTrain.getTrainID() + "," + this.departureStation.getStationLocation() + ","  
                + this.departureStation.getStationDepartTime1() + "," + this.arrivalStation.getStationLocation() + "," + this.arrivalStation.getStationDepartTime1() + "," + this.paymentAmount +"," + Max5Seats);
            }
            else 
            {
                pw.println(this.bookingID + "," + this.bookingDate + "," + this.bookingTime + "," + this.bookingUser.getUserID() + "," + this.bookingUser.getName() + "," 
                + this.bookingUser.getEmail() + "," + this.bookingUser.getPhoneNo() + "," + this.bookingTrain.getTrainID() + "," + this.departureStation.getStationLocation() + ","  
                + this.departureStation.getStationDepartTime2() + "," + this.arrivalStation.getStationLocation() + "," + this.arrivalStation.getStationDepartTime2() + "," + this.paymentAmount +","+ Max5Seats);
            }

            pw.close();

        }catch(Exception e){
            System.out.println(e.getMessage());;
        }
    }

    public void viewAllBookings() {
        for (Booking booking : bookings) {
            booking.viewTicket();
            System.out.println("====================================");
        }
    }
}