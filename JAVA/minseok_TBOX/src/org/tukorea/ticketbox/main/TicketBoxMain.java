package org.tukorea.ticketbox.main;

import org.tukorea.ticketbox.cinema.Screen;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TicketBoxMain {
    public static void main(String[] args) {
        TicketBox ticketBox = new TicketBox();
        Scanner scanner = new Scanner(System.in);
        Screen screen = null;
        boolean mainMenu = true;
        ticketBox.initScreen();

        while (true) {
            if (mainMenu) {
                screen = ticketBox.selectScreen();
                if (screen == null) {
                    System.out.print("안녕히 가세요!");
                    scanner.close();
                    System.exit(0);
                }
                mainMenu = false;
            }
            screen.showScreenMenu();
            System.out.print("메뉴를 선택하세요>>");
            try {
                int select = scanner.nextInt();
                switch (select) {
                    case 1:
                        screen.showMovieinfo();
                        break;
                    case 2:
                        screen.showSeatMap();
                        break;
                    case 3:
                        screen.reserveTicket();
                        break;
                    case 4:
                        screen.cancelReservation();
                        break;
                    case 5:
                        screen.payment();
                        break;
                    case 6:
                        screen.printTicket();
                        break;
                    case 7:
                        mainMenu=true;
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(" 정확하게 입력해주세요.");
                scanner.nextLine();
                continue;
            }
        }
    }
}
