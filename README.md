# BankingFun
simulates a banking system. It consists of several classes:

The Account class represents a bank account and has methods for depositing and withdrawing money, checking the balance, and getting the account number.
The SavingsAccount class extends the Account class and adds an interest rate to the account.
The CheckingAccount class also extends the Account class and allows for overdrafts within a certain limit.
The Bank class manages a list of accounts and has methods for adding and removing accounts, as well as searching for an account by account number.
The Main class is the entry point of the program and creates several accounts, adds them to the bank, and performs transactions between them.
The program uses the ReentrantLock class for thread safety when accessing shared resources (i.e., the account balance). The withdraw method is overridden in the SavingsAccount and CheckingAccount classes to ensure that the balance cannot be negative or overdrafted beyond the limit, respectively.

Overall, the program demonstrates some basic concepts of object-oriented programming in Java and simulates the basic functionality of a banking system.
