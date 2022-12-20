package org.tukorea.atm.util;
import org.tukorea.atm.bank.Account;


public class Statistics {

        public static int sum(Account [] account, int size){
            int sum=0;
            for(int i=0;i<size;i++) {
                sum = sum + account[i].getBalance();
            }
            return sum;
        }
        public static double average(Account [] account, int size){
            double average;
            average=sum(account,size)/size;
            return average;
        }
        public static int max(Account [] account, int size){
            sort(account,size);
            return account[0].getBalance();
        }
        public static Account [] sort(Account [] account, int size){
            int i,j;
            Account[] sortAccount;
            for(i=0;i<size;i++) {
                for (j = 0; j < size - i - 1; j++) {
                    if (account[i].getBalance() < account[i + 1].getBalance()) {
                        Account temp = account[i + 1];
                        account[i + 1] = account[i];
                        account[i] = temp;
                    }
                }
            }
            sortAccount=account;
            return sortAccount;
        }
    }
