package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Customer;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = {"Show Data", "Create Reservation", "Complete/cancel reservation", "Exit"};
        String[] subMenuArr = {"Recent Reservation", "Show Customer", "Show Available Employee", "History Reservation + Total Keuntungan","Back to main menu"};
    
        String optionMainMenu = "";
        String optionSubMenu = "";

		boolean backToMainMenu = false;
        boolean backToSubMenu = false;
        do {
            PrintService.printMenu("MAIN MENU", mainMenuArr);
            System.out.println("---------");
            do {
                System.out.print("Input : ");
                optionMainMenu = input.nextLine();

                if(!ValidationService.validateInput(optionMainMenu, "[0-3]")){
                    System.out.println("Aksi tersebut tidak tersedia");
                }
            } while (!ValidationService.validateInput(optionMainMenu, "[0-3]"));
            
            switch (Integer.valueOf(optionMainMenu)) {
                case 1:
                    do {
                        PrintService.printMenu("SHOW DATA", subMenuArr);
                        System.out.println("---------");
                        do {
                            System.out.print("Input : ");
                            optionSubMenu = input.nextLine();

                            if(!ValidationService.validateInput(optionSubMenu, "[0-4]")){
                                System.out.println("Aksi tersebut tidak tersedia");
                            }
                        } while (!ValidationService.validateInput(optionSubMenu, "[0-4]"));
                        // Sub menu - menu 1
                        switch (Integer.valueOf(optionSubMenu)) {
                            case 1:
                                // panggil fitur tampilkan recent reservation
                                PrintService.showRecentReservation(reservationList);
                                break;
                            case 2:
                                // panggil fitur tampilkan semua customer
                                PrintService.showAllCustomer(personList);
                                break;
                            case 3:
                                // panggil fitur tampilkan semua employee
                                PrintService.showAllEmployee(personList);
                                break;
                            case 4:
                                // panggil fitur tampilkan history reservation + total keuntungan
                                PrintService.showHistoryReservation(reservationList);
                                break;
                            case 0:
                                backToSubMenu = true;
                        }
                    } while (!backToSubMenu);
                    break;
                case 2:
                    // panggil fitur menambahkan reservation
                    ReservationService.createReservation(reservationList, personList);
                    break;
                case 3:
                    // panggil fitur mengubah workstage menjadi finish/cancel
                    ReservationService.editReservationWorkstage(reservationList);
                    break;
                case 0:
                    backToMainMenu = false;
                    break;
            }
        } while (!backToMainMenu);
		
	}
}
