package org.example.Servicos.calc;

public class addAmount {
    private double amount;
    public double addAmount(double amount){
        this.amount +=amount;
        return amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
