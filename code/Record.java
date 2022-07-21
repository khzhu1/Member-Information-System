/* This class is used to store visitor number and total income */

public class Record {

    /* create basic types of records */
    private String date;
    private int number;
    private int income;

    // used to create an empty record
    public Record(){
        date = "";
        number = 0;
        income = 0;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    // the printed information of the record, this string used to store in the csv file
    public String toSting(){
        return date + "," + number+","+income;
    }
}
