/*
 * This class represents a Premium Gym Member, which extends the GymMember class.
 * Premium members have additional benefits, such as personal trainers, special discounts, and different payment handling.
 */
public class PremiumMember extends GymMember {
    private final double premiumCharge; // Fixed cost for premium membership
    private String personalTrainer; // Assigned personal trainer for the premium member
    private boolean isFullPayment; // Indicates if the full membership fee has been paid
    private double paidAmount; // Total amount paid by the member so far
    private double discountAmount; // Discount applied upon full payment
    
    /*
     * Constructor initializes the PremiumMember with required details.
     * Default premium charge is set to $50,000, and initially, no amount is paid.
     */
    public PremiumMember(int id, String name, String location, String phone, String email, String gender, String DOB,String membershipStartDate, String personalTrainer){
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.personalTrainer = personalTrainer;
        this.premiumCharge = 50000;
        this.paidAmount = 0;
        this.isFullPayment = false;
        this.discountAmount = 0;
    }
    
    // getter Methods
    public double getPremiumCharge(){
        return this.premiumCharge;
    }
    
    public String getPersonalTrainer(){
        return this.personalTrainer;
    }
    
    
    public double getPaidAmount (){
        return this.paidAmount;
    }
    
    public double getDiscountAmount(){
        return this.discountAmount;
    }
    
    public boolean isFullPayment(){
        return this.isFullPayment;
    }
    

    
    
    /*
     * Allows the member to pay their due amount in installments.
     * If the full payment is already made, it returns a message indicating no further payment is needed.
     * Ensures the member does not overpay beyond the premium charge.
     */
    public String payDueAmount(double paidAmount){
        if (isFullPayment){
            return "Amount already Paid";
        }
        double remainingAmount = premiumCharge - this.paidAmount;
        
        // Prevents overpayment
        if(paidAmount>remainingAmount){
            return "Payment unsuccessful! execeeds the premium charge\n Amount due: $" + remainingAmount;
        }
        this.paidAmount += paidAmount;
        remainingAmount -= paidAmount;
        
        // If the full amount is paid, update status and apply discount
        if (remainingAmount == 0){
            isFullPayment = true;
            return "Payment successfull! No amount due";
        }else {
            return "Payment successfull! Amount due: $" + remainingAmount;
        }
        
    }
    
    /*
     * Calculates discount if the full payment has been made.
     * A discount of $5000 is applied upon full payment.
     */
    public void calculateDiscount(){
        if (isFullPayment==true){
            this.discountAmount = this.premiumCharge*(10.0/100); // 10 % applied
            System.out.println("Discount given: "+this.discountAmount);
        }else{
            this.discountAmount=0;
            System.out.println("No full Amount Paid: no further discount");
        }
    }
    
    /*
     * Resets the premium member's status, clearing all payments and trainer assignment.
     */
    public void revertPremiumMember(){
        super.resetMember();
        this.personalTrainer = "";
        this.isFullPayment = false;
        this.paidAmount = 0;
        this.discountAmount = 0;
    }
    
    /*
     * Overrides the abstract markAttendance method.
     * Premium members earn 10 loyalty points per visit.
     */
    @Override
    public void markAttendance(){
        this.attendance++;
        this.loyaltyPoints += 10;
    }
    

    //Displays the member’s details, including payment status and discounts.
    public void display() {
        super.display(); // Call parent class display method
        
        System.out.println("\nPremium Member Details:");
        System.out.println("Personal Trainer: " + personalTrainer);
        System.out.println("Paid Amount: $" + paidAmount);
        System.out.println("Full Payment: " + (isFullPayment ? "Yes" : "No"));
        
        // Calculate and display remaining balance
        double remainingAmount = premiumCharge - paidAmount;
        System.out.println("Premium Charge: $" + premiumCharge);
        System.out.println("Remaining Amount: $" + remainingAmount);
        
        // Display discount amount if full payment is complete
        if (isFullPayment) {
            System.out.println("Discount Amount: $" + discountAmount);
        }
    }
}
