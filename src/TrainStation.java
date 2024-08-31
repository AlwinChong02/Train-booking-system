//package Train;

public class TrainStation {
   

  private int stationID;
  private String stationLocation;
  private String stationDepartTime1;
  private String stationDepartTime2;

  public TrainStation() {
    stationID = 0;
    stationLocation = "";
    stationDepartTime1 = "";
    stationDepartTime2 = "";
  }
  public TrainStation(int ID,String stationlocation) {
    this.stationID = ID;
    this.stationLocation = stationlocation;
  }


  //getter method
  public String getStationLocation() { return stationLocation; }
  public int getStationID() { return stationID;  }
  public String getStationDepartTime1() { return stationDepartTime1; }
  public String getStationDepartTime2() { return stationDepartTime2; }

  //setter method
  public void setStationLocation(String stationLocation) { this.stationLocation = stationLocation; }
  public void setStationID(int stationID) { this.stationID = stationID; }
  public void setStationDepartTime1(String stationDepartTime1) { this.stationDepartTime1 = stationDepartTime1; }
  public void setStationDepartTime2(String stationDepartTime2) { this.stationDepartTime2 = stationDepartTime2; }


}
