/*
 * This class represents a Regular Gym Member, extending the abstract GymMember class.
 * Regular members have a fixed attendance limit, eligibility for upgrades, and membership plans.
 * They can upgrade their membership based on attendance and reset their membership if needed.
 */
public class RegularMember extends GymMember {
    private final int attendanceLimit; // Maximum allowed attendance before needing an upgrade
    private boolean isEligibleForUpgrade; // Indicates if the member can upgrade their plan
    private String removalReason; // Stores the reason for membership termination or downgrade
    private String referralSource; // Source through which the member joined (e.g., friend, social media)
    private String plan; // Membership plan (basic, standard, deluxe)
    private double price; // Price of the current membership plan
    
    
    /*
     * Constructor to initialize a RegularMember with personal details and referral source.
     * By default, the plan is "basic", and the price is set to 6500.
     * The attendance limit is fixed at 30.
     */
    public RegularMember (int id, String name, String location, String phone, String email, String gender, String DOB,String membershipStartDate, String referralSource){
        super(id, name, location, phone, email, gender, DOB,membershipStartDate);
        this.referralSource= referralSource;
        this.isEligibleForUpgrade = false;
        this.attendanceLimit = 30;
        this.plan = "basic";
        this.price = 6500;
        this.removalReason = "";
    }
    
    // Getter methods-AccessModifier-
    public int getAttendanceLimit(){
        return this.attendanceLimit;
    }
    
    public boolean isEligibleForUpgrade(){  
        return this.isEligibleForUpgrade;
    }
    
    public String getRemovalReason(){
        return this.removalReason;
    }
    
    public String getReferralSource(){
        return this.referralSource;
    }
    
    public String getPlan(){
        return this.plan;
        
    }
    
    public double getPrice(){
        return this.price;
    }
    
    
    /*
     * Overrides the abstract markAttendance method.
     * Increases attendance count and adds 5 loyalty points per visit.
     */
    @Override
    public void markAttendance(){
        this.attendance++; 
        this.loyaltyPoints += 5;
        if (attendance >= attendanceLimit && !isEligibleForUpgrade){
            System.out.println("max attendance reached! Can upgrade.");
            this.attendance = this.attendance - 30;
            this.isEligibleForUpgrade = true;
        }
    }
    
    
    /*
     * Retrieves the price of a given membership plan.
     * Returns -1 if the provided plan is invalid.
     */
    public double getPlanPrice(String plan){
        switch(plan){
            case "basic":
                return 6500;
                
            case "standard":
                return 12500;
            
            case "deluxe":
                return 18500;
            
            default:
                return -1;
        }
    }
    
    /*
     * Allows the member to upgrade their membership plan if they have reached the attendance limit.
     * Checks if the plan is valid and ensures the member is not upgrading to the same plan.
     */
    public String upgradePlan(String plan) {
        if (isEligibleForUpgrade == true) {
            if (getPlanPrice(plan) == -1) {
                return "Please choose an appropriate plan"; 
            } else if (this.plan.equals(plan)) {
                return "The member is already subscribed to the current plan";
            } else if (this.plan.equals("deluxe")){
                return "The member is already at the highest tier \"deluxe\"";
            }else {
                this.plan = plan;
                this.price = getPlanPrice(plan);
                this.isEligibleForUpgrade = false;
                return "Current plan successfully changed\nplan is now on "+this.plan; 
            }
        } else {
            return "The member is not eligible for a plan upgrade";
        }
    }
    
    /*
     * Resets the member's details and sets their membership back to basic.
     * Also stores the reason for the reset.
     */
    public void revertRegularMember(String removalReason) {
        super.resetMember();
        isEligibleForUpgrade = false;
        plan = "basic";
        price = 6500;
        this.removalReason = removalReason;
    }
    
    /*
     * Displays the member's details, including their plan.
     * Fixes a logic issue: "removalReason" should always be displayed correctly.
     */
    public void display() {
        super.display(); // Call parent class display method
        System.out.println("\nCustomer's plan: " + this.plan);
        
        // print the removal reason
        if (!removalReason.isEmpty()) {
            System.out.println("Removal reason: " + this.removalReason);
        }
    }
}
