package org.tukorea.ticketbox.util;

import org.tukorea.ticketbox.payment.Receipt;

import java.util.HashMap;
import java.util.Iterator;

public class ReceiptWriter {
    public void printConsole(HashMap<Integer, Receipt>map){
        Iterator<Integer> keys = map.keySet().iterator();
        for(int i = 0; i < map.size(); i++)
            System.out.println(map.get(keys.next()).toBackupString());
    }

}
