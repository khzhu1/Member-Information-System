/* This class is used to store a membership's information for the club */

public class Membership {

    /* create basic types of membership */
    private String id;
    private String first_name;
    private String last_name;
    private String date_of_birth;
    private String gender;
    private String postal_address;
    private String telephone_number;
    private String age;
    private String start;
    private String end;
    private String type;
    private String fee;
    private String other;

    // used to create an empty membership
    public Membership(){
        id = "";
        first_name = "";
        last_name = "";
        date_of_birth = "";
        gender = "";
        postal_address = "";
        telephone_number = "";
        age = "";
        start = "";
        end = "";
        type = "";
        fee = "";
        other = "";
    }

    // used to create a membership with information
    private Membership(String id, String first_name, String last_name, String date_of_birth,
                       String gender, String postal_address, String telephone_number,
                       String age, String start, String end, String type,
                       String fee, String other){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.postal_address = postal_address;
        this.telephone_number = telephone_number;
        this.age = age;
        this.start = start;
        this.end = end;
        this.type = type;
        this.fee = fee;
        this.other = other;
    }

    // the hash code for set
    public int hashCode() {
        return 13 * last_name.hashCode() + 17 * first_name.hashCode() + 11 * date_of_birth.hashCode();
    }

    // the equal judge for the set
    public boolean equals(Object obj) {
        // if obj is null, the part after || will not be evaluated
        if (obj == null || !(obj instanceof Membership)) {
            return false;
        }
        Membership m = (Membership) obj;
        return last_name.equals(m.last_name) && first_name.equals(m.first_name) && date_of_birth.equals(m.date_of_birth);
    }

    // the printed information of the membership, this string used to store in the csv file
    public String toString(){
        return tfStr(id)+","+tfStr(last_name)+","+tfStr(first_name)+","+tfStr(date_of_birth)
                +","+tfStr(gender)+","+tfStr(postal_address)+","+tfStr(telephone_number)+","+tfStr(age)
                +","+tfStr(start)+","+tfStr(end)+","+tfStr(type)+","+tfStr(fee)+","+tfStr(other);
    }

    // if an information contains ",", using "" surrounds it
    public String tfStr(String content){
        if (content == null){
            return "";
        }
        if (content.contains(",")){
            content = "\"" + content + "\"";
        }
        return content;
    }

    // a format of print information
    public String getInfo(){
        return "ID: "+tfStr(id)+", Last name: "+tfStr(last_name)+", First name: "+tfStr(first_name)
                +", Date of birth: "+tfStr(date_of_birth) +",\nGender: "+tfStr(gender)+
                ", Address: "+tfStr(postal_address)+", Telephone number: "+tfStr(telephone_number)
                +",\nAge: "+tfStr(age) +", Start time: "+tfStr(start)+", End time: "+tfStr(end)
                +",\nMember type: "+tfStr(type)+", Fee: "+tfStr(fee)+", Other: "+tfStr(other);
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPostal_address() {
        return postal_address;
    }

    public void setPostal_address(String postal_address) {
        this.postal_address = postal_address;
    }

    public String getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(String telephone_number) {
        this.telephone_number = telephone_number;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
