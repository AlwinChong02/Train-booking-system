//package Train;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class Train {
    private String trainID;
    protected TrainStation[] stationList;
    private List<List<String>> seatNumbers; // Using a List for dynamic sizing
    private List<List<String>> seatStatuses;

    public Train(String trainID, TrainStation[] stationList) {
        this.trainID = trainID;
    
        this.stationList = stationList;

        // Initialize seatNumbers and seatStatuses here
        initializeSeatData();
    }

    public Train() {
		initializeSeatData();
	}

	// Getter 
    public String getTrainID() { return trainID; }
    public TrainStation getStation(int sequence) {
        if (sequence >= 0 && sequence < stationList.length) {
            return stationList[sequence];
        }
        return null;
    }
   
    // Setter 
    public void setTrainID(String trainID) { this.trainID = trainID;  }



    public void printSeatInfo() {
        for (int i = 0; i < seatNumbers.size(); i++) {
            for (int j = 0; j < seatNumbers.get(i).size(); j++) {
                System.out.println(seatNumbers.get(i).get(j) + " " + seatStatuses.get(i).get(j) + "\t");
            }
        }
    }
 
    private void initializeSeatData() {
        seatNumbers = new ArrayList<>();
        seatStatuses = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            List<String> rowNumbers = new ArrayList<>();
            List<String> rowStatuses = new ArrayList<>();

            for (int j = 0; j < 4; j++) {
                rowNumbers.add(Integer.toString(i * 10 + j));
                rowStatuses.add("Available");
            }

            seatNumbers.add(rowNumbers);
            seatStatuses.add(rowStatuses);
        }
    }
    
    public void reserveSeat( int seatRow, int seatNumber) {
        if (//sequence >= 0 && sequence < timeList.length &&
            seatRow >= 0 && seatRow <= seatNumbers.size() &&
            seatNumber >= 0 && seatNumber <= seatNumbers.get(seatRow).size()) {

            String seatStatus = seatStatuses.get(seatRow).get(seatNumber);
            if (seatStatus.equals("Available")) {
                seatStatuses.get(seatRow).set(seatNumber, "Reserved");
                System.out.println("Seat " + seatNumbers.get(seatRow).get(seatNumber) + " reserved successfully.");
            } else {
                System.out.println("Seat " + seatNumbers.get(seatRow).get(seatNumber) + " is already reserved.");
            }
        } else {
            System.out.println("Invalid seat selection.");
        }
    }

    public void displayTrainSchedule(){

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

        System.out.println("Train T001 Schedule:");
        for(int i = 0; i < trainStationList.size(); i++){
            System.out.println("Station: " + trainStationList.get(i).getStationLocation() + " (ID: " + trainStationList.get(i).getStationID() + ")" 
            + "\tDeparture Time: " + trainStationList.get(i).getStationDepartTime1());
        }

        System.out.println("\nTrain T002 Schedule:");
        for(int i = trainStationList.size() - 1; i >= 0; i--){
            System.out.println("Station: " + trainStationList.get(i).getStationLocation() + " (ID: " + trainStationList.get(i).getStationID() + ")" 
            + "\tDeparture Time: " + trainStationList.get(i).getStationDepartTime2());
        }
    }

    //read from file T001_Seat.txt and T002_Seat.txt and save seat numbers and seat status back into respective file
    public void saveSeatToFile(String trainID){
        try {
            if(trainID.equals("T001")){
                File fileARS = new File("T001_Seats.txt");
                if (fileARS.exists() && fileARS.canRead() && fileARS.canWrite()){
                    PrintWriter fileAR1Writer = new PrintWriter(fileARS);
                    for (int i = 0; i < seatNumbers.size(); i++) {
                        for (int j = 0; j < seatNumbers.get(i).size(); j++) {
                            fileAR1Writer.println(seatNumbers.get(i).get(j) + " (" + seatStatuses.get(i).get(j) + ")");
                        }
                    }
                    fileAR1Writer.close();
                }
            }
            if(trainID.equals("T002")){
                File fileARS = new File("T002_Seats.txt");
                if (fileARS.exists() && fileARS.canRead() && fileARS.canWrite()){
                    PrintWriter fileAR2Writer = new PrintWriter(fileARS);
                    for (int i = 0; i < seatNumbers.size(); i++) {
                        for (int j = 0; j < seatNumbers.get(i).size(); j++) {
                            fileAR2Writer.println(seatNumbers.get(i).get(j) + " (" + seatStatuses.get(i).get(j) + ")");
                        }
                    }
                    fileAR2Writer.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Read the files T001_Seat.txt and T002_Seat.txt and categorises the data by displaying 4 seats in each row where the files are already stored in the arrangement of 1 seat per row
    public void checkSeatAvailability(String trainID) {
        try {
            String fileName = trainID.equals("T001") ? "T001_Seats.txt" : "T002_Seats.txt";
            File fileARS = new File(fileName);
            
            if (fileARS.exists() && fileARS.canRead()) {
                Scanner fileScanner = new Scanner(fileARS);
                int columnCount = 0;
                
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    System.out.print(line + "\t");
                    columnCount++;
    
                    if (columnCount == 4) {
                        System.out.println(); // Move to the next row after printing four columns
                        columnCount = 0;
                    }
                }
                
                // If there are remaining columns, add line breaks to complete the row
                while (columnCount > 0 && columnCount < 4) {
                    System.out.print("\t");
                    columnCount++;
                }
                System.out.println(); // Complete the last row
                
                fileScanner.close();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
    


