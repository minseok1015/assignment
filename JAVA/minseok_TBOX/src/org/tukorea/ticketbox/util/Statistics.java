package org.tukorea.ticketbox.util;

import org.tukorea.ticketbox.payment.Receipt;

import java.util.HashMap;
import java.util.Iterator;

public class Statistics {
    public static double sum(HashMap<Integer, Receipt> map){
        Iterator<Integer> keys = map.keySet().iterator();
        double sum=0;
        while(keys.hasNext()) {
            int reservedId = keys.next();
            sum += Math.round(map.get(reservedId).getTotalAmount() * 10) / 10.0;
        }
        return sum;
    }
}
