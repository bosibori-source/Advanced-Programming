package Lecture4_interfaces_abstract_classes;

/**
 * Custom checked exception thrown when a bank account does not have
 * sufficient funds to cover a withdrawal transaction.
 */
public class InsufficientFundsException extends Exception {
    private final double amount;
    private final double balance;

    /**
     * Constructs a new InsufficientFundsException with a custom message.
     *
     * @param message the detail message
     */
    public InsufficientFundsException(String message) {
        super(message);
        this.amount = 0.0;
        this.balance = 0.0;
    }

    /**
     * Constructs a new InsufficientFundsException with detailed transaction information.
     *
     * @param message the detail message
     * @param amount the requested withdrawal amount
     * @param balance the current available balance
     */
    public InsufficientFundsException(String message, double amount, double balance) {
        super(message + " (Requested: " + amount + ", Available: " + balance + ")");
        this.amount = amount;
        this.balance = balance;
    }

    /**
     * Gets the requested amount that caused the exception.
     *
     * @return the requested amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the available balance at the time of the exception.
     *
     * @return the available balance
     */
    public double getBalance() {
        return balance;
    }
}
