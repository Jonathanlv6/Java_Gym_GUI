import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.Color;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.Dimension;


public class GymGUI {
    /**
     * Global Color and Font Schemes
     */
    // Fonts Schemes
    private static final Font h1 = new Font("Trebuchet MS", Font.BOLD, 56);
    private static final Font h2 = new Font("Trebuchet MS", Font.PLAIN, 24);
    private static final Font h3 = new Font("Trebuchet MS",Font.PLAIN, 16);
    private static final Font btnFont = new Font("Verdana",Font.PLAIN, 16);
    private static final Font fieldFont = new Font("Verdana", Font.PLAIN, 16);
    private static final Font infoFont = new Font("Courier New", Font.PLAIN, 16);
    
    // Color Schemes -- Color.RED and Color.GREEN will also be used beside the ones mentioned below
    private static final Color c00 = new Color(240,240, 240);
    private static final Color c0 = new Color(200, 200, 200);; // lightest shade of gray
    private static final Color c1 = new Color(168, 168, 168);
    private static final Color c2 = new Color(124, 124, 124);
    private static final Color c3 = new Color(86, 86, 86);
    private static final Color c4 = new Color(51, 51, 51); // darkest shade of gray
    
    // Borders to be used
    private static final Border whiteLine = BorderFactory.createLineBorder(c0);
    private static final Border grayLine = BorderFactory.createLineBorder(c4);
    private static final Border whiteCompoundLine = BorderFactory.createCompoundBorder(
    BorderFactory.createLineBorder(c0),
    BorderFactory.createEmptyBorder(5, 10, 5, 10));
    
    //ArrayList to store all gym members
    private ArrayList<GymMember> gymMembers;
    
    
    //Declaring All the components
    private JFrame frame;
    /**
     * all the main Components to add as a base container in the main frame.
     * mainPanel is the landing panel of the program.
     * formPanel is the base Panel for registering new gym members.
     * titlePanel will be in every base panels as for holding the title label.
     * title label is the title for the program gui.
     * dummyPanel is an empty container at first which will be used for many purpose as a dummy.
     * dummyPanel will help in making the border title which is done manually in this program.
     * when using dummyPanel, use setBounds every time adding it to anything.
     */
    private JPanel mainPanel, formPanel, titlePanel, dummyPanel;
    private JLabel title;
    
    /**
     * all the components contained in the mainPanel;
     * butn Panel will contain buttons for adding, activating and deactivating gym members' subscription.
     * controlPanel will hold basic global functions for gym members like displaying, saving and reading files.
     * 
     */
    private JPanel butnPanel, controlPanel, srchPanel;
    private JButton preMemBtn, regMemBtn, actBtn, deActBtn; // all the buttons in butnPanel
    private JButton saveBtn, dspBtn, readBtn; // all the buttons in controlPanel
    private JButton srchBtn, srchClearBtn;
    private JTextField srchTxt;
    private JLabel addMemTitle, memControl;
    
    /*
     * for the srch Panel, there are dynamically changing JLabel which will show the details of current searched member.
     */
    private JLabel srchTitle, dId, dName, dLocation, dPhone, dEmail, dGender, dDOB, dMembershipStartDate, dAttendance, dLoyaltyPoints, dActiveStatus;
    private JLabel dAttendanceLimit, dIsEligibleForUpgrade, dRemovalReason, dReferralSource, dPlan, dPrice, dMembershipRank;
    private JLabel dPremiumCharge, dPersonalTrainer, dIsFullPayment, dPaidAmount, dDiscountAmount;
    private JButton getMoreInfo, mkaBtn, revRegMemBtn, upgdBtn;
    private JButton revPreMemBtn, calDisBtn, payDBtn;
    
    /*
     * disposal temporary containers for gymMember type and its subclass 
     */
    private GymMember currentGymMember;
    private RegularMember currentRegularMember;
    private PremiumMember currentPremiumMember;
    
    
    
    /**
     * all the components contained in the mainPanel;
     * submitPanel will hold the text fields and  labels when filling form for creating classes of regular and premium members
     * the main FormPanel will show regardless of adding regular or premium member.
     * The required textfields and labels and buttons are dynamically added to the submit and form panel.
     * 
     */
    private JPanel submitPanel;
    private JLabel idLabel, nameLabel, locationLabel, phoneLabel, emailLabel, genderLabel,DOBLabel,mStartLabel;
    private JLabel refSourceLabel, priceLabel;
    private JTextField idTextField, nameTextField, locationTextField, phoneTextField,emailTextField ;
    private JTextField refSourceTextField, priceTextField;
    private JButton backBtn, clearBtn, regSubmitBtn, preSubmitBtn;
    private ButtonGroup genderGroup;
    private JRadioButton mRadioBtn,fRadioBtn, oRadioBtn;
    private JComboBox<Integer> dobYearComboBox, dobDayComboBox, msDayComboBox, msYearComboBox;
    private JComboBox<String> dobMonthComboBox, msMonthComboBox;
 
    private JTextArea textArea; // textarea for storing content of text file.
    private JScrollPane scrollPane; // scroll Pane for the text area
    
    
    /*
     * GymGUI is the constructor of GymGUI class and is used to initialised all the GUI components with their size, 
     * cordinates, background, font, color, border, layout and many more. GymGUI also calls all the other methods 
     * like addMainPanel, allEventActions to set the landing GUI Panel in the main frame.
     */
    public GymGUI() {
        gymMembers = new ArrayList<GymMember>();
        frame = new JFrame();
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setTitle("Gym Membership Management");

        
        titlePanel = new JPanel();
        titlePanel.setBounds(0, 10, 800, 60);
        titlePanel.setBackground(c4);
        
        title = new JLabel("GYM MEMBERSHIP");
        title.setFont(h1);
        title.setForeground(c0);
        titlePanel.add(title);
        
        
        /**
         * Initializing and setting position and sizing all the componens for main Panel
         * four panels inside the mainPanel: titlePanel, butnPanel, controlPanel and srchPanel.
         */
        mainPanel = new JPanel();// landing panel
        mainPanel.setBounds(0, 0, 800, 800);
        mainPanel.setBackground(c4);
        mainPanel.setLayout(null);
        
        
        /*
         * inside the butnPanel
         */
        butnPanel = new JPanel();
        butnPanel.setBounds(100, 100, 600, 190);
        butnPanel.setBackground(c1);
        butnPanel.setLayout(null);
        butnPanel.setBorder(whiteLine);
        
        addMemTitle = new JLabel("Membership and Management");
        addMemTitle.setFont(h2);
        addMemTitle.setBounds(145, 4, 400, 28);
        addMemTitle.setForeground(c4);
        
        preMemBtn = new JButton("Add Premium Member");
        preMemBtn.setBounds(50, 40, 240, 50);
        preMemBtn.setBackground(c00);
        preMemBtn.setForeground(c4);
        preMemBtn.setBorder(grayLine);
        preMemBtn.setFocusPainted(false);
        preMemBtn.setFont(btnFont);
        
        regMemBtn = new JButton("Add Regular Member");
        regMemBtn.setBounds(50, 110, 240, 50);
        regMemBtn.setBackground(c00);
        regMemBtn.setForeground(c4);
        regMemBtn.setBorder(grayLine);
        regMemBtn.setFocusPainted(false);
        regMemBtn.setFont(btnFont);
        
        actBtn = new JButton("Activate Membership");
        actBtn.setBounds(310, 40, 240, 50);
        actBtn.setBackground(c00);
        actBtn.setForeground(c4);
        actBtn.setBorder(grayLine);
        actBtn.setFocusPainted(false);
        actBtn.setFont(btnFont);

        deActBtn = new JButton("Deactivate Membership");
        deActBtn.setBounds(310, 110, 240, 50);
        deActBtn.setBackground(c00);
        deActBtn.setForeground(c4);
        deActBtn.setBorder(grayLine);
        deActBtn.setFocusPainted(false);
        deActBtn.setFont(btnFont);
        
        
        /*
         * Inside the controlPanel 
         */
        controlPanel = new JPanel();
        controlPanel.setBounds(100, 300, 600, 100);
        controlPanel.setLayout(null);
        controlPanel.setBorder(whiteLine);
        controlPanel.setBackground(c4);

        dummyPanel = new JPanel(); // in this section, dummy panel is being used for border title effect
        dummyPanel.setBounds(125, 287, 150, 24);
        dummyPanel.setBackground(c4);
        
        memControl = new JLabel("Membership Control");
        memControl.setForeground(c0);
        memControl.setFont(h3);
        
        saveBtn = new JButton("Save to File");
        saveBtn.setBounds(30, 20, 160, 60);
        saveBtn.setBackground(c00);
        saveBtn.setForeground(c4);
        saveBtn.setBorder(grayLine);
        saveBtn.setFocusPainted(false);
        saveBtn.setFont(btnFont);
        
        dspBtn = new JButton("Display all Members");
        dspBtn.setBounds(210, 20, 180, 60);
        dspBtn.setBackground(c00);
        dspBtn.setForeground(c4);
        dspBtn.setBorder(grayLine);
        dspBtn.setFocusPainted(false);
        dspBtn.setFont(btnFont);
        
        readBtn = new JButton("Read from File");
        readBtn.setBounds(410, 20, 160, 60);
        readBtn.setBackground(c00);
        readBtn.setForeground(c4);
        readBtn.setBorder(grayLine);
        readBtn.setFocusPainted(false);
        readBtn.setFont(btnFont);
        
        
        /*
         * Inside the srchPanel
         */
        srchPanel = new JPanel();
        srchPanel.setBounds(100,410,600,280);
        srchPanel.setLayout(null);
        srchPanel.setBackground(c1);
        srchPanel.setBorder(whiteLine);
        
        srchTitle = new JLabel("Search For id: ");
        srchTitle.setBounds(10,10,110,32);
        srchTitle.setFont(h3);
        srchTitle.setForeground(c4);
        
        
        srchTxt = new JTextField();
        srchTxt.setBounds(120,10,290,32);
        srchTxt.setFont(fieldFont);
        srchTxt.setBackground(c4);
        srchTxt.setForeground(c00);
        srchTxt.setBorder(whiteCompoundLine);
        srchTxt.setCaretColor(c00);
        
        srchBtn = new JButton("srch🔍");
        srchBtn.setBounds(420,10,80, 32);
        srchBtn.setBackground(c4);
        srchBtn.setForeground(c00);
        srchBtn.setBorder(whiteLine);
        srchBtn.setFocusPainted(false);
        srchBtn.setFont(btnFont);
        
        
        srchClearBtn = new JButton("clr🗑");
        srchClearBtn.setBounds(510,10, 80, 32);
        srchClearBtn.setBackground(c4);
        srchClearBtn.setForeground(c00);
        srchClearBtn.setBorder(whiteLine);
        srchClearBtn.setFocusPainted(false);
        srchClearBtn.setFont(btnFont);
        
        
        dId = new JLabel();
        dId.setFont(infoFont); 
        
        dName  = new JLabel();
        dName.setFont(infoFont);
        
        dLocation = new JLabel();
        dLocation.setFont(infoFont);
        
        dPhone = new JLabel();
        dPhone.setFont(infoFont);
        
        dEmail = new JLabel();
        dEmail.setFont(infoFont);
        
        dGender = new JLabel();
        dGender.setFont(infoFont);
        
        dDOB = new JLabel();
        dDOB.setFont(infoFont);
        
        dMembershipStartDate = new JLabel();
        dMembershipStartDate.setFont(infoFont);
        
        dAttendance = new JLabel();
        dAttendance.setFont(infoFont);
        
        dLoyaltyPoints = new JLabel();
        dLoyaltyPoints.setFont(infoFont);
        
        dActiveStatus = new JLabel();
        dActiveStatus.setFont(infoFont);
        
        dAttendanceLimit = new JLabel();
        dAttendanceLimit.setFont(infoFont);
        
        dIsEligibleForUpgrade = new JLabel();
        dIsEligibleForUpgrade.setFont(infoFont);
        
        dRemovalReason = new JLabel();
        dRemovalReason.setFont(infoFont);
        
        dReferralSource = new JLabel();
        dReferralSource.setFont(infoFont);
        
        dPlan = new JLabel();
        dPlan.setFont(infoFont);
        
        dPrice = new JLabel();
        dPrice.setFont(infoFont);
        
        dMembershipRank = new JLabel();
        dMembershipRank.setFont(infoFont);
        
        dPremiumCharge = new JLabel();
        dPremiumCharge.setFont(infoFont);
        
        dPersonalTrainer = new JLabel();
        dPersonalTrainer.setFont(infoFont);
        
        dIsFullPayment = new JLabel();
        dIsFullPayment.setFont(infoFont);
        
        dPaidAmount = new JLabel();
        dPaidAmount.setFont(infoFont);
        
        dDiscountAmount = new JLabel();
        dDiscountAmount.setFont(infoFont);
        
        getMoreInfo = new JButton("See more Infomation");
        getMoreInfo.setBounds(10,224,350,46);
        getMoreInfo.setBackground(c4);
        getMoreInfo.setFont(btnFont);
        getMoreInfo.setBorder(whiteLine);
        getMoreInfo.setForeground(c00);
        getMoreInfo.setFocusPainted(false);
        
        mkaBtn = new JButton("mark Attendance");
        mkaBtn.setBounds(370,50,220,46);
        mkaBtn.setFont(btnFont);
        mkaBtn.setBackground(c4);
        mkaBtn.setForeground(c00);
        mkaBtn.setBorder(whiteLine);
        mkaBtn.setFocusPainted(false);
        
        
        //buttons exclusive to regular member
        revRegMemBtn = new JButton("Revert Regular Member");
        revRegMemBtn.setBounds(370, 108, 220, 46);
        revRegMemBtn.setFont(btnFont);
        revRegMemBtn.setBackground(c4);
        revRegMemBtn.setForeground(c00);
        revRegMemBtn.setBorder(whiteLine);
        revRegMemBtn.setFocusPainted(false);
        
        upgdBtn = new JButton("Upgrade Member");
        upgdBtn.setBounds(370, 166, 220, 46);
        upgdBtn.setFont(btnFont);
        upgdBtn.setBackground(c4);
        upgdBtn.setForeground(c00);
        upgdBtn.setBorder(whiteLine);
        upgdBtn.setFocusPainted(false);
        
        
        //buttons exclusive to premium members
        revPreMemBtn = new JButton("Revert Premium Member");
        revPreMemBtn.setBounds(370,108,220,46);
        revPreMemBtn.setBackground(c4);
        revPreMemBtn.setFont(btnFont);
        revPreMemBtn.setForeground(c00);
        revPreMemBtn.setBorder(whiteLine);
        revPreMemBtn.setFocusPainted(false);
        
        calDisBtn = new JButton("Calculate Discount");
        calDisBtn.setBounds(370,166,220,46);
        calDisBtn.setFont(btnFont);
        calDisBtn.setBackground(c4);
        calDisBtn.setForeground(c00);
        calDisBtn.setBorder(whiteLine);
        calDisBtn.setFocusPainted(false);
        
        payDBtn = new JButton("Pay Due Amount");
        payDBtn.setBounds(370, 224, 220, 46);
        payDBtn.setFont(btnFont);
        payDBtn.setBackground(c4);
        payDBtn.setForeground(c00);
        payDBtn.setBorder(whiteLine);
        payDBtn.setFocusPainted(false);
        
        
        
        /**
         * Initializing and setting position and sizing all the componens for form panel
         * 
         */
        formPanel = new JPanel();
        formPanel.setBounds(0, 0, 800, 800);
        formPanel.setBackground(c4);
        formPanel.setLayout(null);
        
        backBtn = new JButton("<-back");
        backBtn.setBounds(60,60,100,30);
        backBtn.setFont(btnFont);
        backBtn.setBackground(c3);
        backBtn.setForeground(c0);
        backBtn.setBorder(whiteLine);
        backBtn.setFocusPainted(false);
        
        submitPanel = new JPanel();
        submitPanel.setBounds(60, 100, 680, 400);
        submitPanel.setLayout(null);
        submitPanel.setBackground(c3);
        submitPanel.setBorder(whiteLine);
        
        idLabel = new JLabel("Member Id:");
        idLabel.setBounds(10,10, 150, 36);
        idLabel.setFont(h3);
        idLabel.setForeground(c0);
        
        idTextField = new JTextField();
        idTextField.setBounds(175,10,150,36);
        idTextField.setFont(h3);
        idTextField.setForeground(c3);
        idTextField.setBackground(c0);
        idTextField.setBorder(whiteCompoundLine);
        
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 50, 150, 36);
        nameLabel.setFont(h3);
        nameLabel.setForeground(c0);
        
        nameTextField = new JTextField();
        nameTextField.setBounds(175,50,150,36);
        nameTextField.setForeground(c3);
        nameTextField.setBackground(c0);
        nameTextField.setFont(h3);
        nameTextField.setBorder(whiteCompoundLine);
        
        locationLabel = new JLabel("Location:");
        locationLabel.setBounds(370,10,150,36);
        locationLabel.setFont(h3);
        locationLabel.setForeground(c0);
        
        locationTextField = new JTextField();
        locationTextField.setBounds(520,10,150,36);
        locationTextField.setFont(h3);
        locationTextField.setForeground(c3);
        locationTextField.setBackground(c0);
        locationTextField.setBorder(whiteCompoundLine);
        
        phoneLabel = new JLabel("Phone no:");
        phoneLabel.setBounds(370,50,150,36);
        phoneLabel.setFont(h3);
        phoneLabel.setForeground(c0);
        
        phoneTextField = new JTextField();
        phoneTextField.setBounds(520,50,150,36);
        phoneTextField.setFont(h3);
        phoneTextField.setForeground(c3);
        phoneTextField.setBackground(c0);
        phoneTextField.setBorder(whiteCompoundLine);
        
        genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(10,100,100,36);
        genderLabel.setForeground(c0);
        genderLabel.setFont(h3);
        
        
        mRadioBtn = new JRadioButton("Male");
        mRadioBtn.setBounds(175,100, 100, 36);
        mRadioBtn.setFont(h3);
        mRadioBtn.setBackground(c3);
        mRadioBtn.setForeground(c0);
        mRadioBtn.setFocusPainted(false);
        
        fRadioBtn = new JRadioButton("Female");
        fRadioBtn.setBounds(290,100, 100, 36);
        fRadioBtn.setFont(h3);
        fRadioBtn.setBackground(c3);
        fRadioBtn.setForeground(c0);
        fRadioBtn.setFocusPainted(false);
        
        oRadioBtn = new JRadioButton("Other");
        oRadioBtn.setBounds(410,100, 100, 36);
        oRadioBtn.setFont(h3);
        oRadioBtn.setBackground(c3);
        oRadioBtn.setForeground(c0);
        oRadioBtn.setFocusPainted(false);
        
        genderGroup = new ButtonGroup();
        genderGroup.add(mRadioBtn);
        genderGroup.add(fRadioBtn);
        genderGroup.add(oRadioBtn);
        mRadioBtn.setSelected(true);
        
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10,140,100,36);
        emailLabel.setForeground(c0);
        emailLabel.setFont(h3);
        
        emailTextField = new JTextField();
        emailTextField.setBounds(175,143,335,30);
        emailTextField.setFont(h3);
        emailTextField.setForeground(c3);
        emailTextField.setBackground(c0);
        emailTextField.setBorder(whiteCompoundLine);

        
        DOBLabel = new JLabel("Date of Birth:");
        DOBLabel.setBounds(10,190,150,36);
        DOBLabel.setForeground(c0);
        DOBLabel.setFont(h3);
        
        // Year ComboBox
        dobYearComboBox = new JComboBox<>();
        dobYearComboBox.setBounds(175,190,100,36);
        dobYearComboBox.setBackground(c3);
        dobYearComboBox.setForeground(c0);
        dobYearComboBox.setFont(h3);
        
        // Month ComboBox
        String[] months = new String[]{
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
        dobMonthComboBox = new JComboBox<>(months);
        dobMonthComboBox.setBounds(290,190,100,36);
        dobMonthComboBox.setBackground(c3);
        dobMonthComboBox.setForeground(c0);
        dobMonthComboBox.setFont(h3);

        // Day ComboBox (default 1–31)
        dobDayComboBox = new JComboBox<>();
        dobDayComboBox.setBounds(405,190,100,36);
        dobDayComboBox.setBackground(c3);
        dobDayComboBox.setForeground(c0);
        dobDayComboBox.setFont(h3);
        
        
        mStartLabel = new JLabel("Member Start Date:");
        mStartLabel.setBounds(10,240,150,36);
        mStartLabel.setFont(h3);
        mStartLabel.setForeground(c0);
        
        msYearComboBox = new JComboBox<>();
        msYearComboBox.setBounds(175,240,100,36);
        msYearComboBox.setBackground(c3);
        msYearComboBox.setForeground(c0);
        msYearComboBox.setFont(h3);
        
        msMonthComboBox = new JComboBox<>(months);
        msMonthComboBox.setBounds(290,240,100,36);
        msMonthComboBox.setBackground(c3);
        msMonthComboBox.setForeground(c0);
        msMonthComboBox.setFont(h3);
        
        msDayComboBox = new JComboBox<>();
        msDayComboBox.setBounds(405,240,100,36);
        msDayComboBox.setBackground(c3);
        msDayComboBox.setForeground(c0);
        msDayComboBox.setFont(h3);
        
        refSourceLabel = new JLabel();
        refSourceLabel.setBounds(10,290,150,36);
        refSourceLabel.setFont(h3);
        refSourceLabel.setForeground(c0);
        
        refSourceTextField = new JTextField();
        refSourceTextField.setBounds(175,290,150,36);
        refSourceTextField.setFont(h3);
        refSourceTextField.setBackground(c0);
        refSourceTextField.setForeground(c3);
        refSourceTextField.setBorder(whiteCompoundLine);
        
        priceLabel = new JLabel();
        priceLabel.setBounds(10,340,150,36);
        priceLabel.setFont(h3);
        priceLabel.setForeground(c0);
        
        priceTextField = new JTextField();
        priceTextField.setBounds(175,340,150,36);
        priceTextField.setFont(h3);
        priceTextField.setFont(h3);
        priceTextField.setForeground(c3);
        priceTextField.setBackground(c0);
        priceTextField.setEditable(false);
        priceTextField.setCaretColor(c0);
        
        clearBtn = new JButton("Clear all fields");
        clearBtn.setBounds(510,290,160,36);
        clearBtn.setFont(h3);
        clearBtn.setBackground(c0);
        clearBtn.setForeground(c3);
        clearBtn.setBorder(whiteLine);
        clearBtn.setFocusPainted(false);
        
        regSubmitBtn = new JButton("Add Regular Member");
        regSubmitBtn.setBounds(510,340,160,36);
        regSubmitBtn.setFont(h3);
        regSubmitBtn.setBackground(c0);
        regSubmitBtn.setForeground(c3);
        regSubmitBtn.setBorder(whiteLine);
        regSubmitBtn.setFocusPainted(false);
        
        preSubmitBtn = new JButton("Add Premium Member");
        preSubmitBtn.setBounds(510,340,160,36);
        preSubmitBtn.setFont(h3);
        preSubmitBtn.setBackground(c0);
        preSubmitBtn.setForeground(c3);
        preSubmitBtn.setBorder(whiteLine);
        preSubmitBtn.setFocusPainted(false);
        
        allEventActions();
        
        
        addMainPanel();
        
        

        frame.setVisible(true);
    }

    /*
     * main method is the main method of the whole program and will make a new GymGUI object for 
     * loading the program and the program will start.
     */
    public static void main(String[] args) {

        new GymGUI();
    }
    
    /*
     * addFormPanel is used to add all the GUI components that will need to be shown to the user when adding a new member 
     * (both regular and premium member). It will also initialise the JComboBox for DOB and MembershipStartDate. 
     * The GUI components are added in hierarchical order and the main component submitPanel is added to frame. 
     * The memberType on the parameter will decide on which form (for regular or premium member) will be displayed.
     */
    public void addFormPanel(String memberType) {
        submitPanel.add(idLabel);
        submitPanel.add(idTextField);
        
        submitPanel.add(nameLabel);
        submitPanel.add(nameTextField);
        
        submitPanel.add(locationLabel);
        submitPanel.add(locationTextField);
        
        submitPanel.add(phoneLabel);
        submitPanel.add(phoneTextField);
        
        submitPanel.add(genderLabel);
        submitPanel.add(mRadioBtn);
        submitPanel.add(fRadioBtn);
        submitPanel.add(oRadioBtn);
        
        submitPanel.add(emailLabel);
        submitPanel.add(emailTextField);
        
        dobYearComboBox.removeAllItems();
        msYearComboBox.removeAllItems();
        for (int i = 1950; i <= 2025; i++) {
            dobYearComboBox.addItem(i);
            msYearComboBox.addItem(i);
        }
        

        
        for (int i = 1; i <= 31; i++) {
            dobDayComboBox.addItem(i);
            msDayComboBox.addItem(i);
        }
        
        submitPanel.add(DOBLabel);
        submitPanel.add(dobYearComboBox);
        submitPanel.add(dobMonthComboBox);
        submitPanel.add(dobDayComboBox);
        
        submitPanel.add(mStartLabel);
        submitPanel.add(msYearComboBox);
        submitPanel.add(msMonthComboBox);
        submitPanel.add(msDayComboBox);
        
        refSourceLabel.setText(memberType=="regular"?"Referral Source:":"Trainer's Name:");
        
        submitPanel.add(refSourceLabel);
        submitPanel.add(refSourceTextField);
        
        submitPanel.add(priceLabel);
        submitPanel.add(priceTextField);
        
        submitPanel.add(clearBtn);
        if (memberType == "regular"){
            submitPanel.add(regSubmitBtn);
        }else if(memberType == "premium"){
            submitPanel.add(preSubmitBtn);
        }
        
        formPanel.add(backBtn);
        formPanel.add(titlePanel);
        formPanel.add(submitPanel);
        
        
        
        frame.add(formPanel);
    }

    /*
     * addMainPanel is used to add all the GUI components that will need to be shown to the user 
     * when first running program as well as going back from formPanel. When loading mainPanel, all GUI 
     * components are added in hierarchial order and the mainPanel is added to frame. Also currentGymMember, 
     * currentRegularMember and currentPremiumMember are set to null as to reset any task that might have loaded some 
     * objects of the memership classes.
     */
    public void addMainPanel() {
        /**
         * Adding to Panels and frame
         *
         */

        butnPanel.add(addMemTitle);
        butnPanel.add(preMemBtn);
        butnPanel.add(regMemBtn);
        butnPanel.add(actBtn);
        butnPanel.add(deActBtn);

        dummyPanel.add(memControl);
        
        controlPanel.add(saveBtn);
        controlPanel.add(dspBtn);
        controlPanel.add(readBtn);

        
        srchPanel.add(srchTitle);
        srchPanel.add(srchTxt);
        srchPanel.add(srchBtn);
        srchPanel.add(srchClearBtn);
        
        currentGymMember = null;
        currentRegularMember = null;
        currentPremiumMember = null;
        setUpMemberInfo(currentGymMember,null);
        

        mainPanel.add(titlePanel);

        mainPanel.add(butnPanel);
        mainPanel.add(dummyPanel);
        mainPanel.add(controlPanel);
        mainPanel.add(srchPanel);
        
        frame.add(mainPanel);
    }
    
    /*
     * in the search Panel, we need to set up member information which is being searched thorugh the search bar 
     * and this method is used for that purpose. GymMember currentGymMember, ActionEvent e are used for parameter 
     * for different purpose. currentGymMember is used to store the gym member which is currently being loaded temporary. 
     * And ActionEvent e will help us determine whether the searchpanel is being loaded for the first time or is being called 
     * for searching any member.
     */
    public void setUpMemberInfo(GymMember currentGymMember, ActionEvent e){
        if (currentGymMember == null || (e != null && e.getSource() == srchBtn)){
            dId.setBounds(10,50, 350, 18);
            dName.setBounds(10,70,400,18);
            dMembershipRank.setBounds(10,90,350,18);
            dActiveStatus.setBounds(10,110,350,18);
            dPhone.setBounds(10,130,350,18);
            dGender.setBounds(10,150,350,18);
            dAttendance.setBounds(10,170,350,18);
            dLoyaltyPoints.setBounds(10,190,350,18);
            if (currentGymMember == null){
                dId.setText("id:              N/A");
                dName.setText("name:            N/A");
                dMembershipRank.setText("membership type: N/A");
                dActiveStatus.setText("active status:   N/A");
                dPhone.setText("phone no.:       N/A");
                dGender.setText("gender:          N/A");
                dAttendance.setText("attendance:      N/A");
                dLoyaltyPoints.setText("loyalty points:  N/A");
                
                srchPanel.remove(getMoreInfo);
                srchPanel.remove(mkaBtn);
                srchPanel.remove(revRegMemBtn);
                srchPanel.remove(upgdBtn);
                srchPanel.remove(revPreMemBtn);
                srchPanel.remove(calDisBtn);
                srchPanel.remove(payDBtn);
            }
        }
        if (currentGymMember != null && (e != null && e.getSource() == srchBtn)){
            dId.setText("id:              " + currentGymMember.getId());
            dName.setText("name:            " + currentGymMember.getName());            
            dActiveStatus.setText("active status:   " + (currentGymMember.getActiveStatus()?"currently active":"currently deactive"));
            dPhone.setText("phone no.:       " + currentGymMember.getPhone());
            dGender.setText("gender:          " + currentGymMember.getGender());
            dAttendance.setText("attendance:      " + currentGymMember.getAttendance());
            dLoyaltyPoints.setText("loyalty points:  " + currentGymMember.getLoyaltyPoints());
            srchPanel.add(getMoreInfo);
            if (currentGymMember instanceof RegularMember){
                currentRegularMember = (RegularMember) currentGymMember;
                dMembershipRank.setText("membership type: Regular Member");
            } else if (currentGymMember instanceof PremiumMember){
                currentPremiumMember = (PremiumMember)currentGymMember;
                dMembershipRank.setText("membership type: Premium Member");
            }
            if (e != null && e.getSource() == srchBtn){
                srchPanel.add(getMoreInfo);
                srchPanel.add(mkaBtn);
                if (currentGymMember instanceof RegularMember){
                    srchPanel.add(revRegMemBtn);
                    srchPanel.add(upgdBtn);
                }else{
                    srchPanel.add(revPreMemBtn);
                    srchPanel.add(calDisBtn);
                    srchPanel.add(payDBtn);
                    
                }
            }
        }
        frame.revalidate();
        frame.repaint();
        srchPanel.add(dId);
        srchPanel.add(dName);
        srchPanel.add(dMembershipRank);
        srchPanel.add(dActiveStatus);
        srchPanel.add(dPhone);
        srchPanel.add(dGender);
        srchPanel.add(dAttendance);
        srchPanel.add(dLoyaltyPoints);
    }
    
    
    /*
     * allEventAction holds all the logic and work being done when any button or event is being invoked in the program. 
     * This method is called at the end of the constructor to hold all ActionEvent of every GUI components.
     */
    public void allEventActions(){
        regMemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                currentGymMember = null;
                currentRegularMember = null;
                currentPremiumMember = null;
                priceLabel.setText("Basic plan price:");
                priceTextField.setText("6500");
                submitPanel.remove(regSubmitBtn);
                submitPanel.remove(preSubmitBtn);
                frame.remove(mainPanel);
                addFormPanel("regular");
                frame.revalidate();
                frame.repaint();
            }
        });
        
        preMemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                currentGymMember = null;
                currentRegularMember = null;
                currentPremiumMember = null;
                priceLabel.setText("Premium price:");
                priceTextField.setText("50000");
                submitPanel.remove(preSubmitBtn);
                submitPanel.remove(regSubmitBtn);
                frame.remove(mainPanel);
                addFormPanel("premium");
                frame.revalidate();
                frame.repaint();
            }
        });
        
        
        srchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    int tempId = Integer.parseInt(srchTxt.getText());
                    for (GymMember gymMember: gymMembers){
                        if (gymMember.getId() == tempId ){
                            srchTxt.setText("Member found");
                            srchTxt.setForeground(Color.GREEN);
                            srchTxt.setEditable(false);
                            currentGymMember = gymMember;
                            setUpMemberInfo(currentGymMember, e);
                        }
                    }
                    if (currentGymMember == null){
                        srchTxt.setText("Member not found.");
                        srchTxt.setForeground(Color.RED);
                        srchTxt.setEditable(false);
                        setUpMemberInfo(currentGymMember, e);
                    }
                }catch(NumberFormatException ex){
                    srchTxt.setText("pls enter a valid number");
                    srchTxt.setForeground(Color.RED);
                    srchTxt.setEditable(false);
                    currentGymMember = null;
                    setUpMemberInfo(currentGymMember, e);
                    srchTxt.setEditable(false);
                }finally{
                    srchBtn.setEnabled(false);
                    srchBtn.setBackground(c2);
                    srchTxt.setCaretColor(c4);
                }
            }
        });
        
        
        srchClearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                srchTxt.setText(null);
                srchTxt.setEditable(true);
                srchTxt.setForeground(c00);
                currentGymMember = null;
                setUpMemberInfo(currentGymMember,e);
                srchBtn.setEnabled(true);
                srchBtn.setBackground(c4);
                srchTxt.setCaretColor(c00);
            }
        });
        
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                frame.remove(formPanel);
                clearTextFields(submitPanel);
                addMainPanel();
                srchTxt.setEditable(true);
                srchTxt.setText(null);
                srchBtn.setBackground(c4);
                srchTxt.setCaretColor(c00);
                srchBtn.setForeground(c00);
                srchTxt.setForeground(c00);
                srchBtn.setEnabled(true);
                frame.revalidate();
                frame.repaint();
            }
        });
        
        
        dobMonthComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDays(e);
            }
        });
        dobYearComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDays(e);
            }
        });
        
        msMonthComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDays(e);
            }
        });
        msYearComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDays(e);
            }
        });
        
        regSubmitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                addNewMemberForm("regular");
            }
        });
        
        preSubmitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                addNewMemberForm("premium");
            }
        });
        
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                clearTextFields(submitPanel);
            }
        });
        
        getMoreInfo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                showCurrentMember(currentGymMember,"task1");
            }
            
        });
        
        actBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (currentGymMember != null){
                    if (currentGymMember.getActiveStatus() == false){
                        currentGymMember.activateMembership();
                        addMainPanel();
                        srchTxt.setEditable(true);
                        srchTxt.setText(null);
                        srchBtn.setBackground(c4);
                        srchTxt.setCaretColor(c00);
                        srchBtn.setForeground(c00);
                        srchTxt.setForeground(c00);
                        srchBtn.setEnabled(true);
                        currentGymMember = null;
                        JOptionPane.showMessageDialog(
                            frame,
                            "Member Activated",
                            "Member Information",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }else{
                        JOptionPane.showMessageDialog(
                            frame,
                            "Member already Activated",
                            "Member Information",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }else{
                    JOptionPane.showMessageDialog(
                        frame,
                        "no Member selected\nAccess them from the Search Bar",
                        "Member Information",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
        
        deActBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (currentGymMember != null){
                    if (currentGymMember.getActiveStatus() == true){
                        currentGymMember.deactivateMembership();
                        addMainPanel();
                        srchTxt.setEditable(true);
                        srchTxt.setText(null);
                        srchBtn.setBackground(c4);
                        srchTxt.setCaretColor(c00);
                        srchBtn.setForeground(c00);
                        srchTxt.setForeground(c00);
                        srchBtn.setEnabled(true);
                        currentGymMember = null;
                        JOptionPane.showMessageDialog(
                            frame,
                            "Member Deactivated",
                            "Member Information",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }else{
                        JOptionPane.showMessageDialog(
                            frame,
                            "Member already Deactive",
                            "Member Information",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }else{
                    JOptionPane.showMessageDialog(
                        frame,
                        "no Member selected\nAccess them from the Search Bar",
                        "Member Information",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
        
        dspBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JPanel dspPanel = new JPanel();
                dspPanel.setLayout(new BoxLayout(dspPanel, BoxLayout.Y_AXIS));
                for (GymMember member: gymMembers){
                    dspPanel.add(new JLabel("member id: "+member.getId()+" | " + "member name: "+ member.getName()+" | " +
                    ((member instanceof RegularMember)?"Regular Member":"Premium Member")));
                }
                Component[] c = dspPanel.getComponents();
                if (c.length == 0){
                    dspPanel.add(new JLabel("no members in arrayList"));
                }
                JOptionPane.showMessageDialog(
                    frame,
                    dspPanel,
                    "Members Display",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            }
        });
        
        mkaBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (currentGymMember.getActiveStatus() == true){
                    if(currentGymMember instanceof RegularMember){
                        currentRegularMember = (RegularMember)currentGymMember;
                        currentRegularMember.markAttendance();
                    }else{
                        currentPremiumMember = (PremiumMember)currentGymMember;
                        currentPremiumMember.markAttendance();
                    }
                    addMainPanel();
                    srchTxt.setEditable(true);
                    srchTxt.setText(null);
                    srchBtn.setBackground(c4);
                    srchTxt.setCaretColor(c00);
                    srchBtn.setForeground(c00);
                    srchTxt.setForeground(c00);
                    srchBtn.setEnabled(true);
                    currentGymMember = null;
                    currentRegularMember = null;
                    currentPremiumMember = null;
                    JOptionPane.showMessageDialog(
                        frame,
                        "Attendance done",
                        "Attendance message",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }else {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Member currently deactive",
                        "Attendance message",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
        
        revRegMemBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                revertMember();
            }
        });
        
        revPreMemBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                revertMember();
            }
        });
        
        upgdBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                currentRegularMember = (RegularMember)currentGymMember;
                String tempPlan = "basic";
                if (currentRegularMember.getPlan()=="basic"){
                    tempPlan ="standard";
                }else if (currentRegularMember.getPlan()=="standard"){
                    tempPlan = "deluxe";
                }
                
                if (currentGymMember.getActiveStatus() == true){
                    JOptionPane.showMessageDialog(
                        frame,
                        currentRegularMember.upgradePlan(tempPlan),
                        "Upgrade member message",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    addMainPanel();
                    srchTxt.setEditable(true);
                    srchTxt.setText(null);
                    srchBtn.setBackground(c4);
                    srchTxt.setCaretColor(c00);
                    srchBtn.setForeground(c00);
                    srchTxt.setForeground(c00);
                    srchBtn.setEnabled(true);
                    currentGymMember = null;
                    currentRegularMember = null;
                    currentPremiumMember = null;
                }else{
                    JOptionPane.showMessageDialog(
                        frame,
                        "Member currently deactive",
                        "Upgrade member message",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
        
        calDisBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                currentPremiumMember = (PremiumMember) currentGymMember;
                double tempDis = currentPremiumMember.getDiscountAmount();
                currentPremiumMember.calculateDiscount();
                JOptionPane.showMessageDialog(
                    frame,
                    (currentPremiumMember.isFullPayment()==false)?("NO additional discount given\n discount:"+tempDis):("new Discount amount"+currentPremiumMember.getDiscountAmount()),
                    "Upgrade member message",
                    JOptionPane.INFORMATION_MESSAGE
                );
                addMainPanel();
                srchTxt.setEditable(true);
                srchTxt.setText(null);
                srchBtn.setBackground(c4);
                srchTxt.setCaretColor(c00);
                srchBtn.setForeground(c00);
                srchTxt.setForeground(c00);
                srchBtn.setEnabled(true);
                currentGymMember = null;
                currentRegularMember = null;
                currentPremiumMember = null;
            }
        });
        
        payDBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                currentPremiumMember = (PremiumMember) currentGymMember;
                try{
                    String tempResult = JOptionPane.showInputDialog(frame,"Amount: ","Amount input", JOptionPane.QUESTION_MESSAGE);
                    Double tempPrice = Double.parseDouble(tempResult);
                    JOptionPane.showMessageDialog(
                        frame,
                        currentPremiumMember.payDueAmount(tempPrice),
                        "Amount Payment",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    addMainPanel();
                    srchTxt.setEditable(true);
                    srchTxt.setText(null);
                    srchBtn.setBackground(c4);
                    srchTxt.setCaretColor(c00);
                    srchBtn.setForeground(c00);
                    srchTxt.setForeground(c00);
                    srchBtn.setEnabled(true);
                    currentGymMember = null;
                    currentRegularMember = null;
                    currentPremiumMember = null;
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(
                        frame,
                        "invalid amount",
                        "error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }catch (NullPointerException ex){
                     System.out.println("pressed Cancel");
                }  
            }
        });
        
        readBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                readMembersFromFile();
            }
        });
        
        saveBtn.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
                writeMembersToFile();
                JOptionPane.showMessageDialog(
                        frame,
                        "Members Saved.",
                        "File Writing",
                        JOptionPane.INFORMATION_MESSAGE
                );
           }
        });
    }
    
    /*
     * updateDays method is for all the JComboBox in the form panel of regular and premium member. 
     * It is used to make the JComboBox dynamic by changing days according to the year and month choosen 
     * in the form panel. For example, in a regular year, february has 28 days and when in leap year has 29 days. 
     * That’s how the method helps in making the JComboBox more dynamic.
     */
    private void updateDays(ActionEvent e) {
        Object source = e.getSource();

        JComboBox<Integer> yearComboBox = null;
        JComboBox<String> monthComboBox = null;
        JComboBox<Integer> dayComboBox = null;

        // Match source to its corresponding group
        if (source == dobYearComboBox || source == dobMonthComboBox) {
            yearComboBox = dobYearComboBox;
            monthComboBox = dobMonthComboBox;
            dayComboBox = dobDayComboBox;
        } else if (source == msYearComboBox || source == msMonthComboBox) {
            yearComboBox = msYearComboBox;
            monthComboBox = msMonthComboBox;
            dayComboBox = msDayComboBox;
        }

        if (yearComboBox == null || monthComboBox == null || dayComboBox == null) return;

        Integer year = (Integer) yearComboBox.getSelectedItem();
        int month = monthComboBox.getSelectedIndex(); // 0-based

        if (year == null || month == -1) return;

        int days;
        switch (month) {
            case 1: // February
                days = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
                break;
            case 3: case 5: case 8: case 10: // April, June, Sep, Nov
                days = 30;
                break;
            default:
                days = 31;
        }

        // Update the day combo box
        dayComboBox.removeAllItems();
        for (int i = 1; i <= days; i++) {
            dayComboBox.addItem(i);
        }
    }
    
    /*
     * This method is called from the form panel every time a attempt is made to add any new member. 
     * It will first validate appropriate contents of the JTextField and if all the contents are correct 
     * then new member is created and showCurrentMember() is called to confirm adding to the ArrayList.
     */
    public void addNewMemberForm(String memberType){
        JTextField[] fields = { idTextField, nameTextField, locationTextField, phoneTextField, emailTextField, refSourceTextField };
        int tempId;
        String selectedGender = "";
        String selectedDOB = "";
        String selectedMsDate = "";
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }
        }
        try{
            String phoneString = fields[3].getText();
            Long phoneNumber = Long.parseLong(phoneString);
            if (phoneString.length() != 10){
                JOptionPane.showMessageDialog(null, "Please Provide a valid phone Number");
                return;
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Please Provide a valid phone Number");
            return;
        }
        String emailString = fields[4].getText();
        if (!(emailString.contains("@")&&emailString.contains("."))){
            JOptionPane.showMessageDialog(null, "Please Provide a valid email address");
            return;
        }
        
        try{
            if (mRadioBtn.isSelected()) {
                selectedGender = "Male";
            } else if (fRadioBtn.isSelected()) {
                selectedGender = "Female";
            } else if(oRadioBtn.isSelected()) {
                selectedGender = "Other";
            }
            tempId = Integer.parseInt(idTextField.getText());
            selectedDOB = dobYearComboBox.getSelectedItem() +" " +dobMonthComboBox.getSelectedItem()+" "+dobDayComboBox.getSelectedItem();
            selectedMsDate = msYearComboBox.getSelectedItem() +" "+msMonthComboBox.getSelectedItem()+" "+msDayComboBox.getSelectedItem();
            
            if (memberType == "regular"){
                currentRegularMember = new RegularMember(tempId,fields[1].getText().trim(),fields[2].getText().trim(),fields[3].getText().trim(),fields[4].getText().trim(),selectedGender,selectedDOB,selectedMsDate,fields[5].getText().trim());
                showCurrentMember(currentRegularMember, "");
            }else if(memberType == "premium"){
                currentPremiumMember = new PremiumMember(tempId,fields[1].getText().trim(),fields[2].getText().trim(),fields[3].getText().trim(),fields[4].getText().trim(),selectedGender,selectedDOB,selectedMsDate,fields[5].getText().trim());
                showCurrentMember(currentPremiumMember, "");
            }
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(frame,"Please enter valid information}\n"+ex);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frame,"unexpected error found! please try again");
        }
        
        
    }
    
    
    /*
     * loads the current member from currentMember and displays it in a Option Pane.
     */
    public void showCurrentMember(GymMember currentMember, String arg){
        if (currentMember == null) {
            JOptionPane.showMessageDialog(null, "No member selected.");
            return;
        }
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        infoPanel.add(new JLabel("ID: " + currentMember.getId()));
        infoPanel.add(new JLabel("Name: " + currentMember.getName()));
        infoPanel.add(new JLabel("Location: " + currentMember.getLocation()));
        infoPanel.add(new JLabel("Phone: " + currentMember.getPhone()));
        infoPanel.add(new JLabel("Email: " + currentMember.getEmail()));
        infoPanel.add(new JLabel("Gender: " + currentMember.getGender()));
        infoPanel.add(new JLabel("Date of Birth: " + currentMember.getDOB()));
        infoPanel.add(new JLabel("Membership Start Date: " + currentMember.getMembershipStartDate()));
        infoPanel.add(new JLabel("Attendance: " + currentMember.getAttendance()));
        infoPanel.add(new JLabel("Loyalty Points: " + currentMember.getLoyaltyPoints()));
        infoPanel.add(new JLabel("Active Status: " + (currentMember.getActiveStatus()?"Active":"Inactive")));
        
        if (currentMember instanceof RegularMember) {
            currentRegularMember = (RegularMember)currentMember;
            infoPanel.add(new JLabel("Referral Source: " + currentRegularMember.getReferralSource()));
            infoPanel.add(new JLabel("Membership Plan: " + currentRegularMember.getPlan()));
            infoPanel.add(new JLabel("Price: " + currentRegularMember.getPrice()));
            if (!currentRegularMember.getRemovalReason().isEmpty()){
                infoPanel.add(new JLabel("Removal Reason: " + currentRegularMember.getRemovalReason()));
            }
            infoPanel.add(new JLabel("Can Upgrade?: " + (currentRegularMember.isEligibleForUpgrade()?"Yes":"Not Yet")));
            currentRegularMember.display();
        } else if (currentMember instanceof PremiumMember) {
            currentPremiumMember = (PremiumMember)currentMember;
            infoPanel.add(new JLabel("Premium Charge: " + currentPremiumMember.getPremiumCharge()));
            infoPanel.add(new JLabel("Trainer: " + currentPremiumMember.getPersonalTrainer()));
            infoPanel.add(new JLabel("Current Paid Amount: " + currentPremiumMember.getPaidAmount()));
            infoPanel.add(new JLabel("Current Discount: " + currentPremiumMember.getDiscountAmount()));
            infoPanel.add(new JLabel("Paid Full Amount?: " + (currentPremiumMember.isFullPayment()?"Yes":"Not Yet")));
            currentPremiumMember.display();
        }
        
        if (arg.equals("")){
            for(GymMember member: gymMembers){
                if(currentMember.getId() == member.getId()){
                    JOptionPane.showMessageDialog(
                        frame,
                        "Member with the id already exists",
                        "duplicate id found",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
            }
            int result = JOptionPane.showConfirmDialog(
                frame,
                infoPanel,
                "Add this member to the gym?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION){
                System.out.println("Member added.");
                JOptionPane.showMessageDialog(
                    frame,
                    "Member added",
                    "Member added",
                    JOptionPane.INFORMATION_MESSAGE
                );
                gymMembers.add(currentMember);
            }
        }else if (arg.equals("task1")){
                JOptionPane.showMessageDialog(
                    frame,
                    infoPanel,
                    "Member Information",
                    JOptionPane.INFORMATION_MESSAGE
                );
        }else{
                System.out.println("Unknown arg: " + arg);
        }
        
    }
    
    
    /*
     * This method is for clearing all Text area in the form panel excluding some.
     */
    public void clearTextFields(Container container) {
        for (Component c : container.getComponents() ) {
            if (c instanceof JTextField && c != priceTextField) {
                ((JTextField) c).setText("");
            } else if (c instanceof Container) {
                clearTextFields((Container) c); // recursive for nested panels
            }
        }
    }
    
    // Calls methods from respective RegularMember and PremiumMember and implements it to the gui
    public void revertMember(){
        if (currentGymMember.getActiveStatus() == true){
            if(currentGymMember instanceof RegularMember){
                currentRegularMember = (RegularMember)currentGymMember;
                String tempResult = JOptionPane.showInputDialog(frame,"Removal Reason:","revert input", JOptionPane.QUESTION_MESSAGE);
                currentRegularMember.revertRegularMember(tempResult);
            }else{
                currentPremiumMember = (PremiumMember)currentGymMember;
                currentPremiumMember.revertPremiumMember();
            }
            addMainPanel();
            srchTxt.setEditable(true);
            srchTxt.setText(null);
            srchBtn.setBackground(c4);
            srchTxt.setCaretColor(c00);
            srchBtn.setForeground(c00);
            srchTxt.setForeground(c00);
            srchBtn.setEnabled(true);
            currentGymMember = null;
            currentRegularMember = null;
            currentPremiumMember = null;
            JOptionPane.showMessageDialog(
                frame,
                "Successfully Reverted member",
                "Revert message",
                JOptionPane.INFORMATION_MESSAGE
            );
        }else {
            JOptionPane.showMessageDialog(
                frame,
                "Member currently deactive",
                "error message",
                JOptionPane.INFORMATION_MESSAGE
            );
                }
    }
    
    
    /*
     * Writing to text file "MemberDetails.txt" using BufferedWriter and FileWriter.
     * First line is the Header line 
     * and each new line is being added as Long formatted string from ArrayList.
     */
    public void writeMembersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("MemberDetails.txt"))) {
            writer.write(String.format(
                "%-5s %-15s %-15s %-15s %-25s %-10s %-20s %-25s %-15s %-10s %-10s %-15s %-10s %-15s %-15s %-15s %-15s %-15s\n",
                "ID", "Name", "Location", "Phone", "Email","Gender","DOB",
                "Membership Start Date", "Plan", "Price", "Attendance", "Loyalty Points",
                "Active", "Full Payment", "Discount", "Net Paid", "Referral Source", "Trainer"
            ));
            
            String data = "";
            for (GymMember member : gymMembers) {
                
                if (member instanceof RegularMember){
                    RegularMember tempRegMember = (RegularMember)member;
                    data = String.format(
                    "%-5d %-15s %-15s %-15s %-25s %-10s %-20s %-25s %-15s %-10.2f %-10d %-15.2f %-10s %-15s %-15s %-15s %-15s %-15s\n",
                    tempRegMember.getId(), tempRegMember.getName(), tempRegMember.getLocation(), tempRegMember.getPhone(),
                    tempRegMember.getEmail(),tempRegMember.getGender() ,tempRegMember.getDOB(),tempRegMember.getMembershipStartDate(), tempRegMember.getPlan(),
                    tempRegMember.getPrice(), tempRegMember.getAttendance(), tempRegMember.getLoyaltyPoints(),
                    tempRegMember.getActiveStatus() ? "Yes" : "No", "N/A","N/A", "N/a", tempRegMember.getReferralSource(), "N/A"
                    );
                }
                else if (member instanceof PremiumMember){
                    PremiumMember tempPreMember = (PremiumMember)member;
                    data = String.format(
                    "%-5d %-15s %-15s %-15s %-25s %-10s %-20s %-25s %-15s %-10.2f %-10d %-15.2f %-10s %-15s %-15.2f %-15.2f %-15s %-15s\n",
                    tempPreMember.getId(), tempPreMember.getName(), tempPreMember.getLocation(), tempPreMember.getPhone(),
                    tempPreMember.getEmail(),tempPreMember.getGender(),tempPreMember.getDOB(), tempPreMember.getMembershipStartDate(), "Premium",
                    tempPreMember.getPremiumCharge(), tempPreMember.getAttendance(), tempPreMember.getLoyaltyPoints(),
                    tempPreMember.getActiveStatus() ? "Yes" : "No", tempPreMember.isFullPayment() ? "Yes" : "No",
                    tempPreMember.getDiscountAmount(), tempPreMember.getPaidAmount(),"N/A", tempPreMember.getPersonalTrainer()
                    );
                }
                writer.write(data);
            }
    
            JOptionPane.showMessageDialog(null, "Data written using BufferedWriter!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Reading from text file "MemberDetails.txt" using BufferedReader and FileReader.
     * First line is the Header line 
     * and each new line is being added to teh text area as string.
     */
    public void readMembersFromFile() {
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(infoFont); // for alignment
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(900, 400));
    
        try (BufferedReader reader = new BufferedReader(new FileReader("MemberDetails.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            JOptionPane.showMessageDialog(null, scrollPane, "Member Details", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}