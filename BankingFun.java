
//@author Tyon Davis
import java.util.ArrayList;
import java.util.List;

public class BankingFun {
    public class Account {
        private final int accountNumber;
        private final String accountHolderName;
        private double balance;

        public Account(int accountNumber, String accountHolderName, double balance) {
            // Constructor to initialize account details
            this.accountNumber = accountNumber;
            this.accountHolderName = accountHolderName;
            this.balance = balance;
        }

        public void deposit(double amount) {
            // Method to deposit money into the account
            balance += amount;
        }

        public void withdraw(double amount) {
            // Method to withdraw money from the account
            if (balance >= amount) {
                balance -= amount;
            } else {
                System.out.println("Insufficient funds.");
            }
        }

        public String getAccountHolderName() {
            return accountHolderName;
        }

        public double getBalance() {
            // Method to get the current balance of the account
            return balance;
        }

        public int getAccountNumber() {
            // Method to get the account number of the account
            return accountNumber;
        }
    }

    public class SavingsAccount extends Account {
        private final double interestRate;

        public SavingsAccount(int accountNumber, String accountHolderName, double balance, double interestRate) {
            // Constructor to initialize savings account details
            super(accountNumber, accountHolderName, balance);
            this.interestRate = interestRate;
        }

        public double getInterestRate() {
            return interestRate;
        }

        public double calculateInterest() {
            // Method to calculate the interest earned on the account balance
            return getBalance() * interestRate / 100;
        }
    }

    public class CheckingAccount extends Account {
        private double overdraftLimit;

        public CheckingAccount(int accountNumber, String accountHolderName, double balance, double overdraftLimit) {
            // Constructor to initialize checking account details
            super(accountNumber, accountHolderName, balance);
            this.overdraftLimit = overdraftLimit;
        }

        public double getOverdraftLimit() {
            return overdraftLimit;
        }

        public void setOverdraftLimit(double overdraftLimit) {
            this.overdraftLimit = overdraftLimit;
        }

        @Override
        public void withdraw(double amount) {
            // Override withdraw method to allow for overdrafts within the limit
            if (getBalance() - amount >= -overdraftLimit) {
                super.withdraw(amount);
            } else {
                System.out.println("Exceeded overdraft limit.");
            }
        }
    }

    public class Bank {
        private final String bankName;
        private final List<Account> accounts;

        public Bank(String bankName) {
            // Constructor to initialize bank details
            this.bankName = bankName;
            accounts = new ArrayList<>();
        }

        public void addAccount(Account account) {
            // Method to add an account to the bank
            accounts.add(account);
        }

        public void removeAccount(Account account) {
            // Method to remove an account from the bank
            accounts.remove(account);
        }

        public double getTotalBalance() {
            // Method to get the total balance of all accounts in the bank
            double totalBalance = 0;
            for (Account account : accounts) {
                totalBalance += account.getBalance();
            }
            return totalBalance;
        }

        public void transferMoney(Account fromAccount, Account toAccount, double amount) {
            // Method to transfer money between accounts in the bank
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        }
    }

    public static void main(String[] args) {

        BankingFun bankingFun = new BankingFun();
        Bank bank = bankingFun.new Bank("My Bank");

        SavingsAccount savings1 = bankingFun.new SavingsAccount(1001, "John Doe", 1000, 2.5);
        CheckingAccount checking1 = bankingFun.new CheckingAccount(2001, "Jane Smith", 500, 1000);
        SavingsAccount savings2 = bankingFun.new SavingsAccount(1002, "Bob Johnson", 2500, 3.0);
        CheckingAccount checking2 = bankingFun.new CheckingAccount(2002, "Alice Brown", 1000, 500);

        bank.addAccount(savings1);
        bank.addAccount(checking1);
        bank.addAccount(savings2);
        bank.addAccount(checking2);

        System.out.println("Total balance of all accounts: " + bank.getTotalBalance());

        checking1.withdraw(200);
        savings2.deposit(500);

        System.out.println("New balance of checking1: " + checking1.getBalance());
        System.out.println("New balance of savings2: " + savings2.getBalance());

        bank.transferMoney(checking2, savings1, 300);

        System.out.println("New balance of checking2: " + checking2.getBalance());
        System.out.println("New balance of savings1: " + savings1.getBalance());
    }
}
