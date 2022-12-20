package org.tukorea.ticketbox.payment;

public class CardPay implements Pay {
    public static final double CARD_COMMISION = 0.15;
    public Receipt charge(String product, double amount, String name, String number){
        Receipt receipt=new Receipt(name,product,"CardPay",number,amount,amount*(1+CARD_COMMISION));
        return receipt;
    }
}
