import java.util.*;
import java.text.*;

class Medicine {
    String name;
    int quantity;
    double price;
    Medicine next;
    Date manufactureDate;
    Date expiryDate;

    public Medicine(String name, int quantity, double price ,Date manufactureDate, Date expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
        this.manufactureDate = manufactureDate;
        this.next = null;
    }
}

class Pharmacy {
    Scanner sc = new Scanner(System.in);
    Medicine head;
    Date billingDate;
    Date mfg;
    Date exp;
    public void addMedicine(String name1, int quantity, double price,Date manufactureDate, Date expiryDate) throws Exception{
        int totalQuantity = 0;
        // SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd");
        //         dateFormat.setLenient(false); // Disable lenient parsing (strict mode)
        //         try {
        //         mfg = dateFormat.parse(manufactureDate);
        //         b1=false;
        //         } 
        //         catch (ParseException e) {
        //         System.out.println("Date is not in the correct format.");
        //         b1 = true;
        //         }
        //     }
        Medicine newMedicine = new Medicine(name1, quantity, price, manufactureDate, expiryDate);
        if (head == null) {
            head = newMedicine;
        }
        else {
            Medicine current1 = head;
            boolean b3 = false;
           do {
            Date mf = current1.manufactureDate;
            Date ex = current1.expiryDate;
            double pri = current1.price;

                if (current1.name.equals(name1)) {     
                    if(current1.price == price && current1.manufactureDate==manufactureDate && current1.expiryDate==expiryDate){
                        totalQuantity = current1.quantity+quantity;
                        current1.quantity = totalQuantity;
                        b3 = true;
                        break;
                    }
                    else{
                        b3 = false;
                    }      
                }
                else{
                    b3=false;
                    
                }
            current1 = current1.next;
            } while (current1 != null);  
            if(!b3){
                Medicine current2 = head;
                while(current2.next != null){
                    current2 = current2.next;
                }
                current2.next = newMedicine;
          }
        }
    }
    public void deleteMedication(String name1,int quantity1) {
        if (head == null) {
            System.out.println("--Pharmacy inventory is empty--");
            return;
        }
        Medicine temp = head;
        int leftMedicine;
        if(head.name.equals(name1)){
        leftMedicine = head.quantity - quantity1;
        if(leftMedicine > 0){
            head.quantity = leftMedicine;
        }
        else if (leftMedicine == 0){
            head = head.next;
        System.out.println(name1 + " is not present in the inventory");
        }
    }
   else{

        while(!temp.next.name.equals(name1)){
            temp = temp.next;
        }
        String x = temp.next.name;
        System.out.println(x);
        leftMedicine = temp.next.quantity - quantity1;
        if(leftMedicine > 0){
            temp.next.quantity = leftMedicine;
        }
        else if (leftMedicine == 0){
            Medicine z = temp.next;
            temp.next = z.next;
            z = null;
            System.out.println(name1 + " stock has been over--");
   
        }
    }
}

public void deleteByExpiry(Date exp1) throws Exception{
    Medicine temp =head;
    do{
        if(!exp1.before(temp.next.expiryDate)){
            temp.next = temp.next.next;
        }
        else{
            temp = temp.next;
        }
    } while(temp.next != null);
    if(!exp1.before(head.expiryDate))
    {
        head = head.next;
    }
}
    

  

    public void searchByName(String name1){
        Medicine temp = head;
        boolean isPresent = true;
        while(temp!=null){
            if(temp.name.equals(name1)){
                System.out.println("Name of medicine :"+temp.name);
                System.out.println("Quantity left in storage :"+temp.quantity);
                System.out.println("Price per quantity :"+temp.price);
                System.out.println("Manufacture date of the medicine :"+temp.manufactureDate);
                System.out.println("Expiry date of the medicine :"+temp.expiryDate);
                isPresent = false;
            }
            temp = temp.next;
        }
        if(isPresent){
            System.out.println("Medicine not available in inventory");
        }
    }

    public boolean compareDate(String manufactureDate,String expiryDate) throws Exception
    {
            SimpleDateFormat compareDate = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 =compareDate.parse(manufactureDate);
            Date date2 = compareDate.parse(expiryDate);
            compareDate.format(date1);
            compareDate.format(date2);
            if(date1.before(date2)){
                return true;
            }
            else
            {
                System.out.println("--Invalid Expiry Date--");
                return false;
            }

    }

    public boolean checkFormat(String date){
        String dateFormatPattern = "yyyy-MM-dd";
                SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
                dateFormat.setLenient(false); // Disable lenient parsing (strict mode)
                try {
                billingDate = dateFormat.parse(date);
                return false;
                }                 
                catch (ParseException e) {
                System.out.println("Date is not in the correct format.");
                return true;
                }
    }


    public boolean checkAvaibility(String medicine){
        Medicine temp = head;
        boolean b1 = false;
        while(temp!=null){
            if(temp.name.equals(medicine)){
                b1=true;
                break;
            }
            else{
                b1 = false;
            }
            temp = temp.next;
        }
        return b1;
    }

    public double billPrice(String medicine,int amount){
         Medicine temp = head;
         double totalPrice = 0;
        while(temp!=null){
            if(temp.name.equals(medicine)){
                double price11 = temp.price;
                totalPrice = ((double)amount*price11);
            }
            temp = temp.next;
        }
        return totalPrice;
    }

    public void billing(){
        String medicine = "";
        String x="";
        int amount=0;
        double totalBill=0;
        System.out.println("\tEnter the customer details for the bill--");
        System.out.println();
        System.out.print("Enter the name of the customer : ");
        String customer = sc.next();
        boolean b5 = true;
        while(b5){
        System.out.print("Enter the date of billing : ");
        x = sc.next();
        b5 = checkFormat(x);
        }

        System.out.println("");
        while(true)
        {
            System.out.print("Enter the name of the medicine you want to buy (or 'quit' to exit) : ");
            medicine = sc.next();

            if (medicine.equalsIgnoreCase("quit"))
            {
                break;
            }
            if(checkAvaibility(medicine)){
                System.out.println("--Medicine available--");
                System.out.print("Enter the amount you want to buy : ");
                amount = sc.nextInt();
                totalBill = billPrice(medicine, amount);
                deleteMedication(medicine, amount);
            }
            else{
                System.out.println("--Medicine not available--");
            } 
            displayBilling(customer, x, medicine, amount, totalBill);
        }
        
        
    }

    public void displayBilling(String name,String date,String medicine,int quantity,double totalPrice){
        double total = totalPrice*1.07;
        System.out.println("\tBill");
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println("Date of transaction :"+date);
        System.out.println("Name of recipient :"+name);
        System.out.println("Name of medicine :"+medicine);
        System.out.println("Purchased quantity :"+quantity);
        System.out.println("Total Bill before tax :"+totalPrice);
        System.out.println("Total tax applied : 7%");
        System.out.println("Grand Total :"+total);
        System.out.println("------------------------------------------");
    }

    public void displayMedicines() {
        if (head == null) {
            System.out.println("No medicines available in inventory--");
            return;
        }
        Medicine current = head;
        System.out.println("\t\tMedicine Inventory:");
        System.out.println();
        System.out.println("Name \t\t Quantity\tPrice \t\t Mfg. Date \t\t\t\t Exp. Date");
        while (current != null) {
            System.out.println(current.name + " \t " + current.quantity + " \t\t " + current.price+" \t\t "+current.manufactureDate+" \t\t "+current.expiryDate);
            current = current.next;
        }
    }
}

class PharmacyManagementSystem {
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        Pharmacy pharmacy = new Pharmacy();
        String username = "pharmacy";
        String password = "pharmacy123"; 
        System.out.println("\t\t..........................................");
        System.out.println("\t\t. WELCOME TO PHARAMACY MANAGEMENT SYSTEM .");
        System.out.println("\t\t..........................................");

        System.out.println();
        System.out.println();
        System.out.println("Please enter your credentials--");
        System.out.println();
        for(int i = 3;i>=1;i--){
            System.out.print("Enter username :");
            String user = scanner.next();
            // System.out.println();
            if(user.equals(username)){
                System.out.print("Enter password :");
                String pass = scanner.next();
                System.out.println();
                if(pass.equals(password)){
                    break;
                }
                else{
                    System.out.println("Incorrect password.Please enter correct password");
                    System.out.println(i-1+" attempts remaining");
                    if(i-1==0){
                        System.exit(0);
                    }
                    else{
                        continue;
                    }     
                }
            }
            else{
                System.out.println("Incorrect username. Please enter correct Username");
                System.out.println(i-1+" attempts remaining");
                if(i-1==0){
                    System.exit(0);
                }
                else{
                    continue;
                }
            }

        }
        System.out.println();
        System.out.println("Entering Pharmacy Management System---");
        System.out.println();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        sd.setLenient(false);
        pharmacy.addMedicine("Paracetamol", 200, 200,sd.parse("2023-1-1"),sd.parse("2023-12-12"));
        pharmacy.addMedicine("Actemra", 90, 350, new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-02"),new SimpleDateFormat("yyyy-MM-dd").parse("2024-10-3"));
        pharmacy.addMedicine("Vomicine", 70, 320, new SimpleDateFormat("yyyy-mm-dd").parse("2021-02-03"),new SimpleDateFormat("yyyy-mm-dd").parse("2023-12-24"));
        pharmacy.addMedicine("Disprine", 120, 100, new SimpleDateFormat("yyyy-mm-dd").parse("2023-03-10"),new SimpleDateFormat("yyyy-mm-dd").parse("2025-2-22"));
        pharmacy.addMedicine("Asprine", 25, 200, new SimpleDateFormat("yyyy-mm-dd").parse("2022-04-11"),new SimpleDateFormat("yyyy-mm-dd").parse("2024-05-14"));
        pharmacy.addMedicine("Cyclizine", 145, 130, new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-13"),new SimpleDateFormat("yyyy-mm-dd").parse("2025-04-20"));
        pharmacy.addMedicine("Digoxin", 123, 560, new SimpleDateFormat("yyyy-mm-dd").parse("2022-02-05"),new SimpleDateFormat("yyyy-mm-dd").parse("2024-10-21"));
        pharmacy.addMedicine("Insulin", 150, 110, new SimpleDateFormat("yyyy-mm-dd").parse("2021-03-04"),new SimpleDateFormat("yyyy-mm-dd").parse("2023-03-22"));
        pharmacy.addMedicine("Unienzyme", 78, 60, new SimpleDateFormat("yyyy-mm-dd").parse("2023-04-09"),new SimpleDateFormat("yyyy-mm-dd").parse("2025-12-23"));
        pharmacy.addMedicine("Lithium", 84, 120, new SimpleDateFormat("yyyy-mm-dd").parse("2022-05-13"),new SimpleDateFormat("yyyy-mm-dd").parse("2024-11-25"));
        pharmacy.addMedicine("Cipladine", 91, 250, new SimpleDateFormat("yyyy-mm-dd").parse("2021-01-15"),new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-19"));
        pharmacy.addMedicine("Doxapram", 70, 240, new SimpleDateFormat("yyyy-mm-dd").parse("2023-02-07"),new SimpleDateFormat("yyyy-mm-dd").parse("2025-02-18"));
        pharmacy.addMedicine("Xylometazoline", 150, 330, new SimpleDateFormat("yyyy-mm-dd").parse("2022-03-23"),new SimpleDateFormat("yyyy-mm-dd").parse("2024-08-17"));
        String manufactureDate="";
        String expiryDate ="";
        Date manufactureDate1=null;
        Date expiryDate1 = null;
        Date exp1 = null;
        

        while (true) {
            System.out.println("\nPharmacy Management System Menu:");
            System.out.println("1. Add Medicine");
            System.out.println("2. Delete Medicine by expiry date");
            System.out.println("3. Search Medicine By Name");
            System.out.println("4. Billing");
            System.out.println("5. Display all Medicines");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter medicine name : ");
                    String name = scanner.nextLine();
                    System.out.print("Enter quantity : ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter price : ");
                    double price = scanner.nextDouble();
                    boolean b1 = true;
                    while(b1){
                    System.out.print("Enter manufacture date(yyyy-mm-dd) :");
                    manufactureDate = scanner.next(); 
                    String dateFormatPattern = "yyyy-MM-dd";
                SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
                dateFormat.setLenient(false); // Disable lenient parsing (strict mode)
                try {
                manufactureDate1 = dateFormat.parse(manufactureDate);
                b1=false;
                } 
                catch (ParseException e) {
                System.out.println("Date is not in the correct format.");
                b1 = true;
                }
            }
                boolean b2 = true;
                    while(b2){
                    System.out.print("Enter expiry date(yyyy-mm-dd) :");
                    expiryDate = scanner.next();
                String dateFormatPattern1 = "yyyy-MM-dd";
                SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateFormatPattern1);
                dateFormat1.setLenient(false); // Disable lenient parsing (strict mode)
                try {
                expiryDate1 = dateFormat1.parse(expiryDate);
                b2=false;
                } 
                catch (ParseException e) {
                System.out.println("Date is not in the correct format.");
                b2 = true;
                }
                    if(!b2){
                        if(pharmacy.compareDate(manufactureDate, expiryDate)){
                            System.out.println("valid manufacture date");
                            break;
                        }
                        else{
                            b2 = true;
                        }
                    }
                    
                }
                pharmacy.addMedicine(name, quantity, price, manufactureDate1, expiryDate1);
                break;
            
                case 2 :boolean b10 = true;
                    while(b10){
                    System.out.print("Enter expiry date(yyyy-mm-dd) :");
                    String exp = scanner.next();
                String dateFormatPattern1 = "yyyy-MM-dd";
                SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateFormatPattern1);
                dateFormat1.setLenient(false); // Disable lenient parsing (strict mode)
                try {
                exp1 = dateFormat1.parse(exp);
                b10=false;
                } 
                catch (ParseException e) {
                System.out.println("Date is not in the correct format.");
                b10 = true;
                }
                   
                    
                }
               
                pharmacy.deleteByExpiry(exp1);
                break; 
                case 3:
                   System.out.print("Enter the name of the medicine you want to search : ");
                   String name1 = scanner.next();
                   pharmacy.searchByName(name1);
                   break;

                case 4 :pharmacy.billing();
                break;

                case 5:
                    pharmacy.displayMedicines();
                    break;
                
                case 6 :
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}