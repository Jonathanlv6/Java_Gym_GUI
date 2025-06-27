/*
 * This is an abstract class representing a gym member.
 * It contains common attributes and behaviors that all types of gym members share.
 *
 * The class provides basic information such as ID, name, location, phone, and email.
 * It also tracks attendance, loyalty points, and membership status.
 * Some methods are abstract, meaning subclasses must implement them.
 */
public abstract class GymMember{
    // Member details (protected so subclasses can access them directly)
    protected int id;
    protected String name;
    protected String location;
    protected String phone;
    protected String email;
    protected String gender;
    protected String DOB; // Date of Birth 
    protected String membershipStartDate;
    protected int attendance;// Tracks how many times the member has attended the gym
    protected double loyaltyPoints; // Earned through attendance streaks
    protected boolean activeStatus;
    
    
    /*
     * Constructor to initialize a GymMember object with given details.
     * Attendance and loyalty points are set to zero by default.
     * Membership starts as inactive.
     */
    public GymMember(int id, String name, String location, String phone, String email, String gender, String DOB,String membershipStartDate){
        
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB; 
        this.membershipStartDate = membershipStartDate;
        this.attendance = 0;
        this.loyaltyPoints = 0;
        activeStatus = false;
    }
    
    // Getter methods to retrieve member details (provides encapsulation)
    public int getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    public String getLocation(){
        return this.location;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getEmail(){
        return this.email;
    }
    public String getGender(){
        return this.gender;
    }
    public String getDOB(){
        return this.DOB;
    }
    public String getMembershipStartDate(){
        return this.membershipStartDate;
    }
    public int getAttendance(){
        return this.attendance;
    }
    public double getLoyaltyPoints(){
        return this.loyaltyPoints;
    }
    public boolean getActiveStatus(){
        return this.activeStatus;
    }


    
    // Abstract method (must be implemented by subclasses)
    public abstract void markAttendance();
    
    /*
     * Activates the membership if it's currently inactive.
     * Prevents redundant activation.
     */
    public void activateMembership(){
        this.activeStatus = true;
    }
    
    /*
     * Deactivates the membership if it's currently active.
     * Prevents redundant deactivation.
     */
    public void deactivateMembership(){
        this.activeStatus = false;
    }
    
    /*
     * Resets the member's status, attendance, and loyalty points.
     * Useful for restarting membership benefits.
     */
    public void resetMember(){
        this.activeStatus = false;
        this.attendance = 0;
        this.loyaltyPoints = 0;
    }
    
    /*
     * Displays member details in a readable format.
     * Note: There's a bug where "Customer's Name" is printed twice. Fix that!
     */
    public void display(){
        System.out.print(
        "Customer's ID: "+
        this.id+
        "\nCustomer's Name: "+
        this.name+
        "\nCustomer's Location: "+
        this.location+
        "\nCustomer's Phone: "+
        this.phone+
        "\nCustomer's email: "+
        this.email+
        "\nCustomer's gender: "+
        this.gender+
        "\nCustomer's Date Of Birth: "+
        this.DOB+
        "\nMembership Start Date: "+
        this.membershipStartDate+
        "\nCustomer's Attendence: "+
        this.attendance+
        "\nCustomer's Loyalty points: "+
        this.loyaltyPoints+  
        "\nMembership Status: "
        );
        
        // Print the membership status
        if (activeStatus == true){
            System.out.print("Active");
        }else{
            System.out.print("Inactive");
        }
    }
}