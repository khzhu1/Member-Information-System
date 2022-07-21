/* This class contains some operations to deal with csv file */

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Operations {

    private Date date = new Date(); // used to get current date
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy"); // date format

    // used to get information from a membership csv file
    public void getData(String fileName, HashSet<Membership> memberSet){

        try{
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String data = null;
            int len = 0;

            if (fileName.equals("customerlist.csv")){
                len = 7; //in the customerlist.csv file, there are 7 columns of information
            }else {
                len = 13; //in the file I created, there are 13 columns of information
            }

            // read file util there is no more lines
            while ((data = br.readLine()) != null){
                setDataInSet(data, memberSet, len);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // put the data from the membership csv file into a set
    public void setDataInSet(String data, Collection<Membership> memberSet, int len){
        List<String> dataList = new ArrayList<>(); //create an array to store each membership information
        for (int i = 0; i < len; i++){
            dataList.add("");
        }
        String[] dataArr = data.split(",");

        // if there is any data contains ",", do operation
        if (dataArr.length > len){
            for (int i = 0; i < dataArr.length-1; i++){
                if (dataArr[i].startsWith("\"") && (i + 1) < dataArr.length && dataArr[i+1].endsWith("\"")){
                    dataArr[i] = dataArr[i].substring(1);
                    dataArr[i+1] = dataArr[i+1].substring(0, dataArr[i+1].length()-1);
                    String result = dataArr[i] + "," + dataArr[i+1];
                    dataArr[i] = result;
                    for (int j = i+1; j < dataArr.length-1; j++){
                        dataArr[j] = dataArr[j+1];
                    }
                }
            }
            setDataInList(dataArr, dataList, len);
        }else {
            setDataInList(dataArr, dataList, dataArr.length);
        }
        Membership member = new Membership();

        // put information into membership structure, create memberships
        for (int i = 0; i < len; i++){
            switch (i) {
                case 0: member.setId(dataList.get(0));break;
                case 1: member.setLast_name(dataList.get(1));break;
                case 2: member.setFirst_name(dataList.get(2));break;
                case 3: member.setDate_of_birth(dataList.get(3));break;
                case 4: member.setGender(dataList.get(4));break;
                case 5: member.setPostal_address(dataList.get(5));break;
                case 6: member.setTelephone_number(dataList.get(6));break;
                case 7: member.setAge(dataList.get(7));break;
                case 8: member.setStart(dataList.get(8));break;
                case 9: member.setEnd(dataList.get(9));break;
                case 10: member.setType(dataList.get(10));break;
                case 11: member.setFee(dataList.get(11));break;
                case 12: member.setOther(dataList.get(12));break;
                default:break;
            }
        }

        memberSet.add(member); // put memberships into set
    }

    // used to store each split data into a list
    public void setDataInList(String[] dataArr, List<String> dataList, int length){
        for (int i = 0; i < length; i++){
            dataList.set(i, dataArr[i]);
        }
    }

    // put membership data extracted into a new csv file
    public void setData(String fileName, Collection<Membership> memberList){

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile(); // create the csv file if it is not exist
            }
            FileOutputStream fos = new FileOutputStream(file);

            // store all information from set into the file
            for (Membership membership : memberList) {
                byte[] bytes = membership.toString().getBytes();
                fos.write(bytes);
                fos.write("\n".getBytes());
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // write the visitor number and total income into a csv file
    public void recordWrite(String fileName, String date, int num, int income){
        Record vNum = new Record();
        vNum.setNumber(num);
        vNum.setDate(date);
        vNum.setIncome(income);
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile(); // create the csc file if it is not exist
            }
            FileOutputStream fos = new FileOutputStream(file, true);
            byte[] bytes = vNum.toSting().getBytes();
            fos.write(bytes);
            fos.write("\n".getBytes());

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // get total income this year
    public int getIncome(String fileName){

        int income = 0;
        try{
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String year;
            String data = null;

            while ((data = br.readLine()) != null){
                year = data.split(",")[0].split("/")[2];
                if (year.equals(df.format(date).split("/")[2])){
                    income = income + Integer.parseInt(data.split(",")[2]);
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return income;
    }

    // get visitor number for a specific day
    public int getVisitor(String fileName, String d){
        int num = 0;
        try{
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String day;
            String data = null;

            while ((data = br.readLine()) != null){
                day = data.split(",")[0];
                if (day.equals(d)){
                    num = num + Integer.parseInt(data.split(",")[1]);
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return num;
    }

}
