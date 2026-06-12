package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;
import java.util.Calendar;

/**
 * DepositTransaction represents a transaction that deposits funds into a BankAccount.
 * All deposits are irreversible.
 */
public class DepositTransaction extends BaseTransaction {

    /**
     * Constructs a DepositTransaction with a specified amount and date.
     *
     * @param amount the deposit amount
     * @param date the transaction date (must not be null)
     */
    public DepositTransaction(int amount, @NotNull Calendar date) {
        super(amount, date);
    }

    /**
     * Validates that the deposit amount is non-negative.
     *
     * @param amt the amount to validate
     * @return true if the amount is non-negative, false otherwise
     */
    private boolean checkDepositAmount(int amt) {
        return amt >= 0;
    }

    /**
     * Prints a transaction receipt for this deposit transaction.
     */
    @Override
    public void printTransactionDetails() {
        System.out.println("--- Deposit Transaction Receipt ---");
        System.out.println("Transaction ID: " + getTransactionID());
        System.out.println("Date:           " + getDate().getTime());
        System.out.println("Amount:         $" + getAmount());
        System.out.println("Status:         Completed (Irreversible)");
        System.out.println("-----------------------------------");
    }

    /**
     * Applies this deposit transaction to the specified bank account by increasing its balance.
     *
     * @param ba the BankAccount to apply the deposit to
     */
    @Override
    public void apply(BankAccount ba) {
        if (!checkDepositAmount((int) getAmount())) {
            System.out.println("Error: Cannot deposit a negative amount.");
            return;
        }
        double curr_balance = ba.getBalance();
        double new_balance = curr_balance + getAmount();
        ba.setBalance(new_balance);
        System.out.println("Deposit applied successfully. New balance: $" + new_balance);
    }
}
