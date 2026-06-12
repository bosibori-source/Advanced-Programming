package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;
import java.util.Calendar;

/**
 * BaseTransaction represents a concrete transaction implementing TransactionInterface.
 * It provides base fields, getters, and a default implementation of common transaction methods.
 */
public class BaseTransaction implements TransactionInterface {
    private final int amount;
    private final Calendar date;
    private final String transactionID;

    /**
     * Constructs a BaseTransaction with a specified amount and date.
     *
     * @param amount the transaction amount
     * @param date the transaction date (must not be null)
     */
    public BaseTransaction(int amount, @NotNull Calendar date)  {
        this.amount = amount;
        this.date = (Calendar) date.clone();
        // Fixed: Parentheses added around Math.random() * 10000 to cast the result instead of casting Math.random() to 0
        int uniq = (int) (Math.random() * 10000);
        this.transactionID = date.getTimeInMillis() + "_" + uniq;
    }

    /**
     * Returns the transaction amount.
     *
     * @return the transaction amount
     */
    @Override
    public double getAmount() {
        return amount;
    }

    /**
     * Returns a copy of the transaction date.
     *
     * @return the transaction date
     */
    @Override
    public Calendar getDate() {
        return (Calendar) date.clone();
    }

    /**
     * Returns the unique identifier for the transaction.
     *
     * @return the transaction ID
     */
    @Override
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Prints the details of this transaction to the standard output.
     */
    public void printTransactionDetails() {
        System.out.println("Transaction ID: " + getTransactionID());
        System.out.println("Date:           " + getDate().getTime());
        System.out.println("Amount:         $" + getAmount());
    }

    /**
     * Applies the transaction to the specified bank account.
     * The base implementation is a generic operation that does not modify the account balance.
     *
     * @param ba the BankAccount to apply the transaction to
     * @throws InsufficientFundsException if the transaction cannot be applied due to insufficient funds
     */
    public void apply(BankAccount ba) throws InsufficientFundsException {
        System.out.println("Warning: Applying a generic BaseTransaction of amount " 
                           + getAmount() + " on the account. Balance remains unchanged.");
    }
}

