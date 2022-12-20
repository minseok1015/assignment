package org.tukorea.ticketbox.payment;

import javax.swing.plaf.PanelUI;

public class MovieTicket {
    public static final char SEAT_EMPTYMARK='-';
    public static final char SEAT_RESERVED_MARK='R';
    public static final char SEAT_PAY_COMPLETION_MARK='$';

    public MovieTicket(int row,int col){
        this.row=row;
        this.col=col;
        this.status=SEAT_EMPTYMARK;
    }
    private int row;        //좌석행
    private int col;        //좌석열
    private char status;    //좌석생태 =EMPTY , RESERVED , PAY_COMPLETION

    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public char getStatus(){
        return status;
    }
    public void setStatus(char status){
        this.status=status;
    }

    private int reservedId;

    public void setSeat(int row,int col){
        setStatus(SEAT_RESERVED_MARK);
    }
    public void setReservedId(int id){
        this.reservedId=id;
    }
    public int getReservedId(){
        return this.reservedId;
    }
}
