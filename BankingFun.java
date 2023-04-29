//@Authur Tyon Davis

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
  
public class Account {
    private int accountNumber;
    private String accountHolderName;
    private double balance;
    private ReentrantLock lock; // Lock for thread safety
  
    public Account(int accountNumber, String accountHolderName, double balance) {
        // Constructor to initialize account details
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        lock = new ReentrantLock();
    }
    
    public void deposit(double amount) {
        // Method to deposit money into the account
        lock.lock(); // Acquire lock before accessing shared resource
        try {
            balance += amount;
        } finally {
            lock.unlock(); // Release lock after accessing shared resource
        }
    }
    
    public void withdraw(double amount) {
        // Method to withdraw money from the account
        lock.lock(); // Acquire lock before accessing shared resource
        try {
            if (balance >= amount) {
                balance -= amount;
            } else {
                System.out.println("Insufficient funds.");
            }
        } finally {
            lock.unlock(); // Release lock after accessing shared resource
        }
    }
    
    public double getBalance() {
        // Method to get the current balance of the account
        lock.lock(); // Acquire lock before accessing shared resource
        try {
            return balance;
        } finally {
            lock.unlock(); // Release lock after accessing shared resource
        }
    }
  
    public int getAccountNumber() {
        // Method to get the account number of the account
        return accountNumber;
    }
//end of class
}
  
public class SavingsAccount extends Account {
    private double interestRate;
  
    public SavingsAccount(int accountNumber, String accountHolderName, double balance, double interestRate) {
      // Constructor to initialize savings account details
      super(accountNumber, accountHolderName, balance);
      this.interestRate = interestRate;
    }
  
    @Override
    public void withdraw(double amount) {
      // Override withdraw method to ensure that balance cannot be negative
      lock.lock(); // Acquire lock before accessing shared resource
      try {
          if (getBalance() - amount >= 0) {
              super.withdraw(amount);
          } else {
              System.out.println("Insufficient funds.");
          }
      } finally {
          lock.unlock(); // Release lock after accessing shared resource
      }
    }
  }
  
  public class CheckingAccount extends Account {
    private double overdraftLimit;
    private ReentrantLock lock; // Lock for thread safety
  
    public CheckingAccount(int accountNumber, String accountHolderName, double balance, double overdraftLimit) {
      // Constructor to initialize checking account details
      super(accountNumber, accountHolderName, balance);
      this.overdraftLimit = overdraftLimit;
      lock = new ReentrantLock();
    }
  
    @Override
    public void withdraw(double amount) {
      // Override withdraw method to allow for overdrafts within the limit
      lock.lock(); // Acquire lock before accessing shared resource
      try {
          if (getBalance() - amount >= -overdraftLimit) {
              super.withdraw(amount);
          } else {
              System.out.println("Exceeded overdraft limit.");
          }
      } finally {
          lock.unlock(); // Release lock after accessing shared resource
      }
    }
  }
  
  public class Bank {
    private List<Account> accounts;
    private ReentrantLock lock; // Lock for thread safety
  
    public Bank() {
      // Constructor to initialize the bank with an empty list of accounts
      accounts = new ArrayList<>();
      lock = new ReentrantLock();
    }
  
    public void addAccount(Account account) {
        // Method to add an account to the bank
        lock.lock(); // Acquire lock
         // Method to add an account to the bank
        accounts.add(account);
        lock.unlock()
    
    
    }
    
    public void removeAccount(Account account) {
      // Method to remove an account from the bank
      accounts.remove(account);
    }
    
    public Account searchAccount(int accountNumber) {
      // Method to search for an account in the bank using account number
      for (Account account : accounts) {
        if (account.getAccountNumber() == accountNumber) {
          return account;
        }
      }
      return null;
    }
    }
  
      public class Main {
        public static void main(String[] args) {
          Bank bank = new Bank();
      
          // Create some accounts and add them to the bank
          Account account1 = new SavingsAccount(1, "Alice", 1000, 0.01);
          Account account2 = new CheckingAccount(2, "Bob", 500, 200);
          Account account3 = new SavingsAccount(3, "Charlie", 2000, 0.02);
          bank.addAccount(account1);
          bank.addAccount(account2);
          bank.addAccount(account3);
      
          // Perform some transactions between accounts
          bank.transfer(account1, account2, 200);
          bank.transfer(account2, account3, 1000);
          bank.transfer(account3, account1, 500);
      
          // Print out the final balances of the accounts
          System.out.println("Final balances:");
          for (Account account : bank.getAccounts()) {
            System.out.printf("%s: %.2f\n", account.getAccountHolderName(), account.getBalance());
          }
        }
      }
