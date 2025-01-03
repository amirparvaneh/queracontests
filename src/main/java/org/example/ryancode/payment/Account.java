package org.example.ryancode.payment;

public class Account {
	private String accountName;
	private int balance;

	public Account(String accountName, int initialBalance) {
		this.accountName = accountName;
		this.balance = initialBalance;
	}

	public void deposit(int amount) {
		balance += amount;
	}

	public void withdraw(int amount) throws IllegalArgumentException {
		if (amount > balance) {
			throw new IllegalArgumentException("Insufficient funds in account: " + accountName);
		}
		balance -= amount;
	}

	public String logBalance() {
		return "Account: " + accountName + ", Balance: " + balance;
	}

	public String getAccountName() {
		return accountName;
	}

	public int getBalance() {
		return balance;
	}
}