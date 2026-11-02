package org.example.Servicos.calc;

public class Discount {
    public double discount(addAmount amount, double discount){
        return amount.getAmount() - discount;
    }
}
