import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.text.*;

public class ClubMembership extends JFrame implements ActionListener {

    /* create basic types of GUI */
    private JPanel fPanel;
    private JTextArea text;
    private Operations op = new Operations();
    private HashSet<Membership> memberSet = new HashSet<>();
    private Membership membership;
    private int visitorNum = 0;
    private int income = 0;

    private JTextField firstNameField = new JTextField();
    private JTextField lastNameField = new JTextField();
    private JTextField DOBField = new JTextField();
    private ButtonGroup gender = new ButtonGroup();
    private JRadioButton male = new JRadioButton("Male", true);
    private JRadioButton female = new JRadioButton("Female", false);
    private JRadioButton gOther = new JRadioButton("Other", false);
    private JTextField addressField = new JTextField();
    private JTextField phoneField = new JTextField();
    private JTextField otherField = new JTextField();
    private JTextField day = new JTextField();
    private ButtonGroup memberType = new ButtonGroup();
    private JRadioButton individual = new JRadioButton("Individual Member", true);
    private JRadioButton family = new JRadioButton("Family Member", false);
    private JRadioButton visitor = new JRadioButton("Visitor", false);
    private RandomNumber randomNumber = new RandomNumber();
    private int[] IDArr = randomNumber.randomArray(1000000);
    private int IDCount = 0;

    private Date date = new Date(); // used to get current date
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy"); // date format

    // the main page
    public ClubMembership(){
        op.getData("memberList.csv", memberSet); //load the data from the new csv file as the basic database
        setTitle("Membership Management"); // GUI title

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        this.setBounds((int)width/6, (int)height/6, (int)width/3*2, (int)height/3*2);

        Container contentPane = this.getContentPane();

        JPanel buttons = new JPanel(new GridLayout(5,1));

        makeButton(buttons, "Load/Save the customer data from/to a csv format file", this);
        makeButton(buttons, "Add/Delete a customer to/from the system", this);
        makeButton(buttons, "Add/Revise entries for existing customers", this);
        makeButton(buttons, "Search any customer record in the system", this);
        makeButton(buttons, "Quit", this);

        contentPane.add(buttons, BorderLayout.WEST);

        fPanel = new JPanel(new BorderLayout());

        contentPane.add(fPanel, BorderLayout.CENTER);
    }

    // used to clear typed information
    private void reset(){
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        DOBField = new JTextField();
        male.setSelected(true);
        female.setSelected(false);
        gOther.setSelected(false);
        addressField = new JTextField();
        phoneField = new JTextField();
        otherField = new JTextField();
        individual.setSelected(true);
        family.setSelected(false);
        visitor.setSelected(false);
    }

    // used to create buttons
    private void makeButton(JPanel p, String name, ActionListener target) {
        JButton b = new JButton(name);
        p.add(b);
        b.addActionListener(target);
    }

    // used to create fields
    private void makeField(JTextField fieldName, String name, JPanel p){
        JPanel panel = new JPanel(new GridLayout(1,2));
        JLabel label = new JLabel(name);
        panel.add(label);
        panel.add(fieldName);
        p.add(panel);
    }

    // used to create fields with words in it
    private void makeField(JTextField fieldName, String name, JPanel p, String desc){
        JPanel panel = new JPanel(new GridLayout(1,2));
        JLabel label = new JLabel(name);
        fieldName.setText(desc);
        panel.add(label);
        panel.add(fieldName);
        p.add(panel);
    }

    // listen to the button click and response
    public void actionPerformed(ActionEvent e) {

        // identify the button
        String command = e.getActionCommand();
        System.out.println(command);

        // exit the program, save the data into new csv file before exit
        if (command.equals("Quit")) {
            op.setData("memberList.csv", memberSet);
            op.recordWrite("record.csv", df.format(date), visitorNum, income);
            System.exit(0);
        }
        if (command.equals("Load/Save the customer data from/to a csv format file")){
            reset();
            load_save();
        }
        if (command.equals("Add/Delete a customer to/from the system")){
            reset();
            add_delete();
        }

        if (command.equals("Add/Revise entries for existing customers")){
            reset();
            add_revise();
        }

        if (command.equals("Search any customer record in the system")) {
            reset();
            search();
        }

        if (command.equals("Load data from customerlist.csv")){
            op.getData("customerlist.csv", memberSet); // get data from customerlist.csv file
        }
        if (command.equals("Load data")){
            op.getData("memberList.csv", memberSet); // get data from new csv file
        }
        if (command.equals("Save data")){
            op.setData("memberList.csv", memberSet); // store membership data into new csv file
            op.recordWrite("record.csv", df.format(date), visitorNum, income); //store visitor number and income
            visitorNum = 0;
            income = 0;
        }
        if (command.equals("Add a customer")){
            reset();
            choose_type();
        }
        if (command.equals("Continue")){
            //only record membership information when the member type is individual or family
            if (visitor.isSelected()){
                visitorNum ++;
                income += 6;
                visitorAdd();
            }else if (individual.isSelected()){
                add();
            }else {
                add();
            }
        }
        if (command.equals("Delete a customer")){
            reset();
            delete();
        }
        if (command.equals("Confirm to add")){
            add_member();
        }
        if (command.equals("Confirm to delete")){
            delete_member();
        }
        if (command.equals("Revise entries")){
            revise();
        }
        if (command.equals("Confirm to revise")){
            reviseInfo();
        }
        if (command.equals("Search")){
            searchMember();
        }
        if (command.equals("Calculate income")){
            op.recordWrite("record.csv", df.format(date), visitorNum, income); //store visitor number and income
            visitorNum = 0;
            income = 0;
            cIncome();
        }
        if (command.equals("Visitor number")){
            op.recordWrite("record.csv", df.format(date), visitorNum, income); //store visitor number and income
            visitorNum = 0;
            income = 0;
            vNumber();
        }
        if (command.equals("Search visitor number")){
            getVNum();
        }

    }

    // get visitor number from record csv file
    private void getVNum(){
        int totalVNum = 0;
        totalVNum = op.getVisitor("record.csv", day.getText());
        fPanel.removeAll();
        fPanel.repaint();
        JPanel tPanel = new JPanel();
        text = new JTextArea();
        text.setText("Visitor number: " + totalVNum);
        tPanel.add(text);
        fPanel.add(tPanel, BorderLayout.NORTH);
        revalidate();
    }

    // skip to visitor number search page
    private void vNumber(){
        fPanel.removeAll();
        fPanel.repaint();
        JPanel panel = new JPanel(new GridLayout(2, 1));
        makeField(day, "Type date (dd/MM/yy): ", panel);
        makeButton(panel, "Search visitor number", this);
        fPanel.add(panel, BorderLayout.SOUTH);
        revalidate();
    }

    // get this year's income from record csv file
    private void cIncome(){
        int totalIncome = 0;
        totalIncome = op.getIncome("record.csv");
        fPanel.removeAll();
        fPanel.repaint();
        JPanel tPanel = new JPanel();
        text = new JTextArea();
        text.setText("Income: " + totalIncome);
        tPanel.add(text);
        fPanel.add(tPanel, BorderLayout.NORTH);
        revalidate();
    }

    // Every time a visitor enter, there will show Welcome to show one visitor record is recorded
    private void visitorAdd(){
        fPanel.removeAll();
        fPanel.repaint();
        JPanel tPanel = new JPanel();
        text = new JTextArea();
        text.setText("Welcome!");
        tPanel.add(text);
        fPanel.add(tPanel, BorderLayout.NORTH);
        revalidate();
    }

    // before add a new member, choose the member type in this page
    private void choose_type(){
        fPanel.removeAll();
        fPanel.repaint();
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JPanel radioPanel = new JPanel(new GridLayout(1,3));
        radioPanel.add(individual);
        memberType.add(individual);
        radioPanel.add(family);
        memberType.add(family);
        radioPanel.add(visitor);
        memberType.add(visitor);
        panel.add(radioPanel);
        makeButton(panel, "Continue", this);
        fPanel.add(panel, BorderLayout.SOUTH);
        revalidate();
    }

    // search members by type part of/whole first name or/and last name
    private void searchMember(){
        fPanel.removeAll();
        fPanel.repaint();

        HashSet<Membership> temp = new HashSet<>();
        int count = 0;
        for (Membership m : memberSet){
            if (m.getFirst_name().contains(firstNameField.getText()) && (m.getLast_name().contains(lastNameField.getText()))){
                temp.add(m);
                count++;
            }
        }
        JPanel tPanel = new JPanel(new GridLayout(count, 1));
        for (Membership m : temp){
            JTextArea info = new JTextArea();
            info.setText(m.getInfo());
            tPanel.add(info);
        }
        fPanel.add(tPanel);
        revalidate();
    }

    // revise information for a member
    private void reviseInfo(){
        fPanel.removeAll();
        fPanel.repaint();

        for (Membership m : memberSet) {
            if (m.equals(membership)) {
                m.setFirst_name(firstNameField.getText());
                m.setLast_name(lastNameField.getText());
                m.setPostal_address(addressField.getText());
                m.setTelephone_number(phoneField.getText());
                m.setOther(otherField.getText());
                //System.out.println(m);
            }
        }

        JPanel tPanel = new JPanel();
        text = new JTextArea();
        text.setText("Revise successfully");
        tPanel.add(text);
        fPanel.add(tPanel, BorderLayout.NORTH);
        revalidate();
    }

    // the page to allow type revised information for a member
    private void revise(){
        fPanel.removeAll();
        fPanel.repaint();

        membership = new Membership();
        JPanel tPanel = new JPanel();
        text = new JTextArea();
        text.setText("");
        membership.setFirst_name(firstNameField.getText());
        membership.setLast_name(lastNameField.getText());
        membership.setDate_of_birth(DOBField.getText());

        JPanel panel = new JPanel(new GridLayout(6,1));

        if (memberSet.contains(membership)){
            for (Membership m : memberSet){
                if (m.equals(membership)){
                    membership = m;
                }
            }
            makeField(firstNameField, "First name: ", panel, membership.getFirst_name());
            makeField(lastNameField, "Last name: ", panel, membership.getLast_name());
            makeField(addressField, "Address: ", panel, membership.getPostal_address());
            makeField(phoneField, "Telephone: ", panel, membership.getTelephone_number());
            makeField(otherField, "Other: ", panel, membership.getOther());

            makeButton(panel, "Confirm to revise", this);

            fPanel.add(panel, BorderLayout.SOUTH);
        }else {
            text.setText("No such person");
        }
        tPanel.add(text);
        fPanel.add(tPanel, BorderLayout.NORTH);
        revalidate();
    }

    // judge if a new member's age is conformed, and start adding member if conformed
    private void add_member(){

        membership = new Membership();
        JPanel tPanel = new JPanel();
        text = new JTextArea();
        String[] birth = DOBField.getText().split("/");
        int edge1 = 0;
        int edge2 = 0;
        int age = 0;
        String[] today = df.format(date).split("/");
        if (Integer.parseInt(birth[1]) < 9 || (Integer.parseInt(birth[1]) == 9 && Integer.parseInt(birth[0]) == 1)) {
            edge1 = 12;
            edge2 = 18;
        }else {
            edge1 = 13;
            edge2 = 19;
        }
        if (individual.isSelected() || visitor.isSelected()) {
            if (Integer.parseInt(today[2]) - Integer.parseInt(birth[2]) >= edge1
                    && Integer.parseInt(birth[2]) < 100  && Integer.parseInt(birth[2]) > -1) {
                if (edge1 == 12) {
                    age = Integer.parseInt(today[2]) - Integer.parseInt(birth[2]);
                }else{
                    age = Integer.parseInt(today[2]) - Integer.parseInt(birth[2]) - 1;
                }
                fPanel.removeAll();
                fPanel.repaint();
                membership.setAge(String.valueOf(age));
                text.setText("Add successfully");
                infoType();
            } else if (Integer.parseInt(today[2]) - Integer.parseInt(birth[2]) < 0
                    && Integer.parseInt(birth[2]) < 100  && Integer.parseInt(birth[2]) > -1) {
                if (edge1 == 12) {
                    age = 100 + Integer.parseInt(today[2]) - Integer.parseInt(birth[2]);
                }else{
                    age = 100 + Integer.parseInt(today[2]) - Integer.parseInt(birth[2]) - 1;
                }
                fPanel.removeAll();
                fPanel.repaint();
                membership.setAge(String.valueOf(age));
                text.setText("Add successfully");
                infoType();
            }else {
                text.setText("Age does not meet the requirements");
            }
        } else {
            if (Integer.parseInt(today[2]) - Integer.parseInt(birth[2]) >= edge2
                    && Integer.parseInt(birth[2]) < 100  && Integer.parseInt(birth[2]) > -1) {
                if (edge2 == 18) {
                    age = Integer.parseInt(today[2]) - Integer.parseInt(birth[2]);
                }else {
                    age = Integer.parseInt(today[2]) - Integer.parseInt(birth[2]) - 1;
                }
                fPanel.removeAll();
                fPanel.repaint();
                membership.setAge(String.valueOf(age));
                text.setText("Add successfully");
                infoType();
            } else if (Integer.parseInt(today[2]) - Integer.parseInt(birth[2]) < 0
                    && Integer.parseInt(birth[2]) < 100  && Integer.parseInt(birth[2]) > -1) {
                if (edge2 == 18) {
                    age = 100 + Integer.parseInt(today[2]) - Integer.parseInt(birth[2]);
                }else {
                    age = 100 + Integer.parseInt(today[2]) - Integer.parseInt(birth[2]) - 1;
                }
                fPanel.removeAll();
                fPanel.repaint();
                membership.setAge(String.valueOf(age));
                text.setText("Add successfully");
                infoType();
            }else {
                text.setText("Age does not meet the requirements");
            }
        }
        tPanel.add(text);
        fPanel.add(tPanel, BorderLayout.NORTH);
        //System.out.println(membership);
        revalidate();
    }

    // create a new member with information
    private void infoType(){
        String type = "";
        String fee = "";
        if (individual.isSelected()){
            type = "individual";
            fee = "36";
            income += 36;
        }else if(family.isSelected()){
            type = "family";
            fee = "60";
            income += 60;
        }else{
            type = "visitor";
            fee = "6";
        }
        membership.setType(type);
        membership.setFee(fee);
        int id = IDArr[IDCount];
        boolean repetition = false;
        do {
            repetition = false;
            for (Membership membership1 : memberSet) {
                if (membership1.getId().equals(String.valueOf(id))) {
                    IDCount++;
                    id = IDArr[IDCount];
                    repetition = true;
                }
            }
        }while (repetition);
        membership.setId(String.valueOf(id));
        membership.setFirst_name(firstNameField.getText());
        membership.setLast_name(lastNameField.getText());
        membership.setDate_of_birth(DOBField.getText());
        String gender = "";
        if (male.isSelected()){
            gender = "male";
        }else if (female.isSelected()){
            gender = "female";
        }else{
            gender = "other";
        }
        membership.setGender(gender);
        membership.setPostal_address(addressField.getText());
        membership.setTelephone_number(phoneField.getText());
        membership.setStart(df.format(date));
        if (type.equals("individual") || type.equals("family")){
            membership.setEnd(df.format(new Date((date.getTime() + 30L * 24 * 60 * 60 * 1000))));
        }else {
            membership.setEnd(df.format(date));
        }
        membership.setOther(otherField.getText());
        for (Membership m : memberSet){
            if (m.equals(membership)){
                text.setText("Member already exist");
            }
        }
        memberSet.add(membership);
    }

    // the load/save data page
    private void load_save(){
        fPanel.removeAll();
        fPanel.repaint();

        JPanel panel = new JPanel(new GridLayout(3,1));
        makeButton(panel, "Load data from customerlist.csv", this);
        makeButton(panel, "Load data", this);
        makeButton(panel, "Save data", this);

        JPanel record = new JPanel(new GridLayout(2, 1));
        makeButton(record, "Calculate income", this);
        makeButton(record, "Visitor number", this);

        fPanel.add(record, BorderLayout.NORTH);
        fPanel.add(panel, BorderLayout.SOUTH);
        revalidate();
    }

    // the add/delete member page
    private void add_delete(){
        fPanel.removeAll();
        fPanel.repaint();

        JPanel panel = new JPanel(new GridLayout(2,1));
        makeButton(panel, "Add a customer", this);
        makeButton(panel, "Delete a customer", this);

        fPanel.add(panel, BorderLayout.SOUTH);
        revalidate();
    }

    // the page to fill information for a new member
    private void add(){
        fPanel.removeAll();
        fPanel.repaint();

        JPanel tPanel = new JPanel();
        text = new JTextArea();
        text.setText("Birth type as dd/MM/yy");
        tPanel.add(text);
        fPanel.add(tPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(8,1));

        makeField(firstNameField, "First name: ", panel);
        makeField(lastNameField, "Last name: ", panel);
        makeField(DOBField, "Date of birth: ", panel);
        JPanel genderPanel = new JPanel(new GridLayout(1,2));
        JPanel genderS = new JPanel(new GridLayout(1,3));
        JLabel label = new JLabel("Gender: ");
        genderPanel.add(label);
        genderS.add(male);
        gender.add(male);
        genderS.add(female);
        gender.add(female);
        genderS.add(gOther);
        gender.add(gOther);
        genderPanel.add(genderS);
        panel.add(genderPanel);
        makeField(addressField, "Address: ", panel);
        makeField(phoneField, "Telephone: ", panel);
        makeField(otherField, "Other: ", panel);

        makeButton(panel, "Confirm to add", this);

        fPanel.add(panel, BorderLayout.SOUTH);
        revalidate();
    }

    // page to type information of a member to delete
    private void delete(){
        fPanel.removeAll();
        fPanel.repaint();

        JPanel tPanel = new JPanel();
        text = new JTextArea();
        text.setText("Birth type as dd/MM/yy");
        tPanel.add(text);
        fPanel.add(tPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4,1));
        makeField(firstNameField, "First name: ", panel);
        makeField(lastNameField, "Last name: ", panel);
        makeField(DOBField, "Date of birth: ", panel);
        makeButton(panel, "Confirm to delete", this);

        fPanel.add(panel, BorderLayout.SOUTH);
        revalidate();
    }

    // delete the member
    private void delete_member(){
        membership = new Membership();
        JPanel tPanel = new JPanel();
        text = new JTextArea();
        membership.setFirst_name(firstNameField.getText());
        membership.setLast_name(lastNameField.getText());
        membership.setDate_of_birth(DOBField.getText());
        if (memberSet.contains(membership)){
            memberSet.remove(membership);
            text.setText("Delete successfully");
        }else {
            text.setText("No such person");
        }
        tPanel.add(text);
        fPanel.add(tPanel, BorderLayout.NORTH);
        revalidate();
    }

    // page for revise information for a member
    private void add_revise(){
        fPanel.removeAll();
        fPanel.repaint();

        JPanel panel = new JPanel(new GridLayout(4,1));

        makeField(firstNameField, "First name: ", panel);
        makeField(lastNameField, "Last name: ", panel);
        makeField(DOBField, "Date of birth: ", panel);

        makeButton(panel, "Revise entries", this);

        fPanel.add(panel, BorderLayout.SOUTH);
        revalidate();
    }

    // page for type information of searching page
    private void search(){
        fPanel.removeAll();
        fPanel.repaint();

        JPanel panel = new JPanel(new GridLayout(3,1));

        makeField(firstNameField, "First name: ", panel);
        makeField(lastNameField, "Last name: ", panel);

        makeButton(panel, "Search", this);

        fPanel.add(panel, BorderLayout.SOUTH);
        revalidate();
    }

    // main function to run the GUN
    public static void main(String[] args) {

        JFrame frm = new ClubMembership();
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }

}
