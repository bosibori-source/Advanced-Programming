package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;
import java.util.Calendar;

/**
 * WithdrawalTransaction represents a transaction that withdraws funds from a BankAccount.
 * Unlike deposits, withdrawals are reversible.
 */
public class WithdrawalTransaction extends BaseTransaction {
    private BankAccount associatedAccount;
    private double amountWithdrawn = 0.0;
    private double amountNotWithdrawn = 0.0;
    private boolean isReversed = false;

    /**
     * Constructs a WithdrawalTransaction with a specified amount and date.
     *
     * @param amount the withdrawal amount
     * @param date the transaction date (must not be null)
     */
    public WithdrawalTransaction(int amount, @NotNull Calendar date) {
        super(amount, date);
    }

    /**
     * Checks if the transaction amount is positive.
     *
     * @param amt the amount to check
     * @return true if the amount is positive, false otherwise
     */
    private boolean checkWithdrawalAmount(double amt) {
        return amt > 0;
    }

    /**
     * Reverses this withdrawal transaction, restoring the withdrawn amount
     * back to the associated bank account.
     *
     * @return true if the reversal was successful, false otherwise
     */
    public boolean reverse() {
        if (associatedAccount == null) {
            System.out.println("Error: Transaction has not been successfully applied. Cannot reverse.");
            return false;
        }
        if (isReversed) {
            System.out.println("Error: Transaction has already been reversed.");
            return false;
        }
        double currentBalance = associatedAccount.getBalance();
        associatedAccount.setBalance(currentBalance + amountWithdrawn);
        isReversed = true;
        System.out.println("Withdrawal transaction successfully reversed. Restored $" + amountWithdrawn);
        return true;
    }

    /**
     * Prints the receipt details for this withdrawal transaction.
     */
    @Override
    public void printTransactionDetails() {
        System.out.println("--- Withdrawal Transaction Receipt ---");
        System.out.println("Transaction ID:      " + getTransactionID());
        System.out.println("Date:                " + getDate().getTime());
        System.out.println("Requested Amount:    $" + getAmount());
        System.out.println("Amount Withdrawn:    $" + amountWithdrawn);
        System.out.println("Amount Not Withdrawn:$" + amountNotWithdrawn);
        System.out.println("Reversal Status:     " + (isReversed ? "Reversed" : "Not Reversed"));
        System.out.println("--------------------------------------");
    }

    /**
     * Applies this withdrawal transaction to the specified bank account.
     * Throws an InsufficientFundsException if the account balance does not cover the withdrawal.
     *
     * @param ba the BankAccount to apply the withdrawal to
     * @throws InsufficientFundsException if the account balance is less than the transaction amount
     */
    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        if (!checkWithdrawalAmount(getAmount())) {
            System.out.println("Error: Withdrawal amount must be positive.");
            return;
        }
        double curr_balance = ba.getBalance();
        if (curr_balance < getAmount()) {
            throw new InsufficientFundsException("Insufficient funds to complete full withdrawal.", getAmount(), curr_balance);
        }
        double new_balance = curr_balance - getAmount();
        ba.setBalance(new_balance);
        this.associatedAccount = ba;
        this.amountWithdrawn = getAmount();
        this.amountNotWithdrawn = 0.0;
        System.out.println("Withdrawal applied successfully. New balance: $" + new_balance);
    }

    /**
     * Overloaded apply method that can allow partial withdrawals when funds are insufficient.
     * If allowPartial is true, and the balance is positive but less than the withdrawal amount,
     * it withdraws all remaining funds, setting the account balance to 0, and records the unpaid amount.
     * Uses a try-catch-finally block to handle the exception thrown by full withdrawal attempts.
     *
     * @param ba the BankAccount to apply the withdrawal to
     * @param allowPartial true if partial withdrawal is permitted when full funds are unavailable
     */
    public void apply(BankAccount ba, boolean allowPartial) {
        this.associatedAccount = ba;
        try {
            // Attempt to perform full withdrawal
            apply(ba);
        } catch (InsufficientFundsException e) {
            double balance = ba.getBalance();
            if (allowPartial && balance > 0) {
                // 0 < balance < withdrawal amount
                this.amountWithdrawn = balance;
                this.amountNotWithdrawn = getAmount() - balance;
                ba.setBalance(0.0);
                System.out.println("Insufficient funds. Overloaded method applied partial withdrawal.");
                System.out.println("Withdrew remaining balance: $" + balance);
                System.out.println("Amount not withdrawn (deficit): $" + amountNotWithdrawn);
            } else {
                // balance is <= 0, or partial withdrawals not allowed
                this.amountWithdrawn = 0.0;
                this.amountNotWithdrawn = getAmount();
                System.out.println("Withdrawal failed: " + e.getMessage());
            }
        } finally {
            System.out.println("Finally block: Withdrawal execution attempt complete. Current balance: $" + ba.getBalance());
        }
    }

    /**
     * Gets the amount that was successfully withdrawn.
     *
     * @return the amount withdrawn
     */
    public double getAmountWithdrawn() {
        return amountWithdrawn;
    }

    /**
     * Gets the amount that was not withdrawn due to insufficient funds.
     *
     * @return the amount not withdrawn
     */
    public double getAmountNotWithdrawn() {
        return amountNotWithdrawn;
    }

    /**
     * Checks if this transaction has been reversed.
     *
     * @return true if reversed, false otherwise
     */
    public boolean isReversed() {
        return isReversed;
    }

    /**
     * Gets the bank account associated with this transaction.
     *
     * @return the associated BankAccount
     */
    public BankAccount getAssociatedAccount() {
        return associatedAccount;
    }
}
