import Lecture1_adt.*; // Import all classes from Lecture1_adt package to be used in this client code
import Lecture4_interfaces_abstract_classes.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/*
* Client Code for accessing the Lecture1_adt.TransactionInterface.java module
 */
public class Main {

    public static void testTransaction1() {
        Calendar d1 = new GregorianCalendar(); // d1 is an Object [Objects are Reference types]
        Lecture1_adt.Transaction1 t1 = new Lecture1_adt.Transaction1(1000, d1); // amount and d1 are arguments

        System.out.println(t1.toString());
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t " + t1.amount);
        System.out.println("Lecture1_adt.TransactionInterface Date: \t " + t1.date);

        // Please note that the Client Codes can access the data in the class directly through the dot operator
        // This kind of exposure is a threat to both the Representation Independence and Preservation of Invariants
    }


    /** @return a transaction of same amount as t, one month later
     * This is a PRODUCER of the class Lecture1_adt.Transaction2
     * This code will help demostrate the Design exposures still present in transaction2 class
     * */

    public static Transaction2 makeNextPayment(Transaction2 t) {
        Calendar d =  t.getDate();
        d.add(Calendar.MONTH, 1);
        return new Transaction2(t.getAmount(), d);
    }

    /*
    Testing Transaction2 class
     */
    public static void testTransaction2() {

        Calendar d1 = new GregorianCalendar();

        Lecture1_adt.Transaction2 t = new Lecture1_adt.Transaction2(1000, d1);

        Lecture1_adt.Transaction2 modified_t = makeNextPayment(t);

        System.out.println("\n\nState of the Object T1 After Client Code Tried to Change the Amount");
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+modified_t.getAmount());
        System.out.println("Lecture1_adt.TransactionInterface Date: \t "+modified_t.getDate().getTime());

        System.out.println("\n\nHow does T2 Look Like?????");
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+modified_t.getAmount());
        System.out.println("Lecture1_adt.TransactionInterface Date: \t "+modified_t.getDate().getTime());

        /* Please note that Although we have solved the problem of Transaction1
        * And client code can no longer use the dot (.) operator to directly access the data
        * There is still some exposure especially if we pass an object of a previous Transaction2 to create a new Transaction2 object
         */

    }


    /** @return a list of 12 monthly payments of identical amounts
     * This code will help demostrate the Design exposures still present in transaction3 class
     * */
    public static List<Transaction3> makeYearOfPayments (int amount) throws NullPointerException {

        List<Transaction3> listOfTransaction3s = new ArrayList<Transaction3>();
        Calendar date = new GregorianCalendar(2024, Calendar.JANUARY, 3);


        for (int i = 0; i < 12; i++) {
            listOfTransaction3s.add(new Transaction3(amount, date));
            date.add(Calendar.MONTH, 1);
        }
        return listOfTransaction3s;
    }

    /*
    Testing Transaction3 class
     */
    public static void testTransaction3() {

        List<Transaction3> allPaymentsIn2024 = makeYearOfPayments(1000);

        for (Transaction3 t3 : allPaymentsIn2024) {

            // Display all the 12 Transactions
            for (Transaction3 transact : allPaymentsIn2024) {
                System.out.println("\n\n  ::::::::::::::::::::::::::::::::::::::::::::\n");
                System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+transact.getAmount());
                System.out.println("Lecture1_adt.TransactionInterface Date: \t "+transact.getDate().getTime());
            }
        }

        /* Please Check all the 12 transactions displayed and hwo their dates look like
         * Note that Although Transaction3 class resolves to an extent the exposure in Transaction2 class
         * There is still some exposure especially if we pass an object of a previous Transaction3 to create a
         * new Transaction3 object
         */
    }


    /** @return a list of 12 monthly payments of identical amounts
     * This code Show that by judicious copying and defensive programming we eliminate the exposure in Transaction3
     * As defined in the constructor of Transaction4 class
     * */

    public static List<Transaction4> makeYearOfPaymentsFinal (int amount) throws NullPointerException {

        List<Transaction4> listOfTransaction4s = new ArrayList<Transaction4>();
        Calendar date = new GregorianCalendar(2024, Calendar.JANUARY, 3);


        for (int i = 0; i < 12; i++) {
            listOfTransaction4s.add(new Transaction4(amount, date));
            date.add(Calendar.MONTH, 1);
        }
        return listOfTransaction4s;
    }

    /*
    Testing Transaction3 class
     */
    public static void testTransaction4() {

        /*
         * Call the function to make all the Twelve transaction in a year of our business
         */

        List<Transaction4> transactionsIn2024 = makeYearOfPaymentsFinal(1200);

        // Display all the 12 Transactions
        for (Transaction4 transact : transactionsIn2024) {
            System.out.println("\n\n  ::::::::::::::::::::::::::::::::::::::::::::\n");
            System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+transact.getAmount());
            System.out.println("Lecture1_adt.TransactionInterface Date: \t "+transact.getDate().getTime());
        }

        // Please Take a look at all the 12 transaction now and compare with the outputs of the Transaction3 class
    }


    public static void testLecture4Transactions() {
        System.out.println("\n==========================================");
        System.out.println("  Testing Lecture 4 Transaction System   ");
        System.out.println("==========================================\n");

        // 1. Create a BankAccount
        System.out.println("[Step 1] Initializing Bank Account with $500.00");
        BankAccount account = new BankAccount(500.00);
        System.out.println("Initial Balance: $" + account.getBalance());

        Calendar now = new GregorianCalendar();

        // 2. BaseTransaction behavior
        System.out.println("\n[Step 2] Testing BaseTransaction");
        BaseTransaction baseTx = new BaseTransaction(150, now);
        baseTx.printTransactionDetails();
        try {
            baseTx.apply(account); // Will print warning and not change balance
        } catch (InsufficientFundsException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        System.out.println("Balance after BaseTransaction: $" + account.getBalance());

        // 3. DepositTransaction behavior
        System.out.println("\n[Step 3] Testing DepositTransaction");
        DepositTransaction depositTx = new DepositTransaction(250, now);
        depositTx.printTransactionDetails();
        depositTx.apply(account); // Balance should become 750
        System.out.println("Balance after DepositTransaction: $" + account.getBalance());

        // 4. WithdrawalTransaction (Successful Case)
        System.out.println("\n[Step 4] Testing WithdrawalTransaction (Successful Full Withdrawal)");
        WithdrawalTransaction withdrawalTx1 = new WithdrawalTransaction(300, now);
        withdrawalTx1.printTransactionDetails();
        try {
            withdrawalTx1.apply(account); // Balance should become 450
        } catch (InsufficientFundsException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        System.out.println("Balance after WithdrawalTransaction: $" + account.getBalance());

        // 5. WithdrawalTransaction (Insufficient Funds Case - Standard apply)
        System.out.println("\n[Step 5] Testing WithdrawalTransaction (Insufficient Funds - Exception Thrown)");
        WithdrawalTransaction withdrawalTx2 = new WithdrawalTransaction(1000, now);
        withdrawalTx2.printTransactionDetails();
        try {
            System.out.println("Attempting to withdraw $1000 from balance of $" + account.getBalance());
            withdrawalTx2.apply(account); // Should throw InsufficientFundsException
        } catch (InsufficientFundsException e) {
            System.out.println("Caught Expected Exception: " + e.getMessage());
            System.out.println("Deficit details - Requested: " + e.getAmount() + ", Available: " + e.getBalance());
        }
        System.out.println("Balance after failed withdrawal: $" + account.getBalance());

        // 6. WithdrawalTransaction (Insufficient Funds Case - Overloaded apply with partial allowance)
        System.out.println("\n[Step 6] Testing Overloaded apply(ba, true) for Partial Withdrawal");
        // Remaining balance is 450. Attempting to withdraw 600 with partial allowed.
        WithdrawalTransaction withdrawalTx3 = new WithdrawalTransaction(600, now);
        System.out.println("Attempting to withdraw $600 with partial allowed. Balance: $" + account.getBalance());
        withdrawalTx3.apply(account, true); // Should withdraw 450, set balance to 0, record 150 not withdrawn
        withdrawalTx3.printTransactionDetails();
        System.out.println("Balance after partial withdrawal: $" + account.getBalance());

        // 7. Reversal behavior
        System.out.println("\n[Step 7] Testing Withdrawal Reversal");
        System.out.println("Reversing partial withdrawal...");
        boolean reverseResult = withdrawalTx3.reverse(); // Should restore 450, balance becomes 450
        System.out.println("Reversal success: " + reverseResult);
        System.out.println("Balance after reversal: $" + account.getBalance());
        withdrawalTx3.printTransactionDetails();

        System.out.println("\nReversing full withdrawal from Step 4...");
        boolean reverseResultFull = withdrawalTx1.reverse(); // Should restore 300, balance becomes 750
        System.out.println("Reversal success: " + reverseResultFull);
        System.out.println("Balance after reversing full withdrawal: $" + account.getBalance());

        // 8. Polymorphism and Type Casting (Late Binding vs Early Binding)
        System.out.println("\n[Step 8] Testing Polymorphism & Upcasting (Late vs Early Binding)");
        // Upcasting subtype objects to BaseTransaction reference
        BaseTransaction polyDeposit = (BaseTransaction) new DepositTransaction(100, now);
        BaseTransaction polyWithdrawal = (BaseTransaction) new WithdrawalTransaction(200, now);

        System.out.println("\nRunning polyDeposit.apply() [Declared type: BaseTransaction, Actual type: DepositTransaction]");
        try {
            // Late binding: JVM resolves the actual class at runtime and executes DepositTransaction.apply()
            polyDeposit.apply(account); // Balance should become 750 + 100 = 850
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Balance after polymorphic deposit: $" + account.getBalance());

        System.out.println("\nRunning polyWithdrawal.apply() [Declared type: BaseTransaction, Actual type: WithdrawalTransaction]");
        try {
            // Late binding: JVM resolves the actual class at runtime and executes WithdrawalTransaction.apply()
            polyWithdrawal.apply(account); // Balance should become 850 - 200 = 650
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Balance after polymorphic withdrawal: $" + account.getBalance());

        System.out.println("\nDemonstrating Early (Static) Binding vs Late (Dynamic) Binding:");
        System.out.println("- Early Binding occurs at compile-time. If we called a private or static method, or constructor, the compiler would bind it based on the reference type.");
        System.out.println("- Late Binding occurs at runtime. The JVM binds the overridden 'apply' and 'printTransactionDetails' methods based on the actual object type on the heap.");
        System.out.print("Polymorphic Deposit details: ");
        polyDeposit.printTransactionDetails(); // Executes DepositTransaction.printTransactionDetails
    }

    public static void main(String[] args) {
        // Run Lecture 4 tests
        testLecture4Transactions();
    }
}