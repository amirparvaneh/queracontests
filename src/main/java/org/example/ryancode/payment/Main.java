package org.example.ryancode.payment;

public class Main {
    public static void main(String[] args) {
        Account account1 = new Account("Ali", 1000);
        Account account2 = new Account("Sara", 2000);

        System.out.println(account1.logBalance());
        System.out.println(account2.logBalance());

        Payment[] payments = new Payment[]{
                new CreditCardPayment(account1, account2, 500),
                new DigitalWalletPayment(account2, account1, "wallet1", "email@email.com", 300),
                new CashPayment(account1, account2, 900) // این پرداخت باید خطای کمبود موجودی بدهد
        };

        for (Payment payment : payments) {
            System.out.println(payment.processPayment());
        }

        System.out.println(account1.logBalance());
        System.out.println(account2.logBalance());
    }
}