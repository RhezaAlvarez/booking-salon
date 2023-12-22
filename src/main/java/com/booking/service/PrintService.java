package com.booking.service;

import java.util.List;

import com.booking.models.Person;
import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class PrintService {
    public static void printMenu(String title, String[] menuArr){
        int num = 1;
        System.out.println("=========");
        System.out.println(title);
        System.out.println("---------");
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {   
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);   
            num++;
        }
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public static void showRecentReservation(List<Reservation> reservationList){
        int num = 1;
        System.out.println("+==============================================================================================================================+");
        System.out.printf("| %-4s | %-10s | %-15s | %-50s | %15s | %-15s |\n", "No.", "ID", "Nama Customer", "Service", "Total Biaya", "Workstage");
        System.out.println("+==============================================================================================================================+");
        reservationList.stream().filter(reservation -> reservation.getWorkstage().equals("In Process")).forEach(reservation -> {
            System.out.printf("| %4s | %-10s | %-15s | %-50s | %15s | %-15s |\n", 
                num, reservation.getReservationId(), reservation.getCustomer().getName(), reservation.getServices(), reservation.getReservationPrice(), reservation.getWorkstage());
        });
        System.out.println("+==============================================================================================================================+");
    }

    public static void showAllCustomer(List<Person> personList){
        int num = 0;
        System.out.println("+===========================================================================================+");
        System.out.printf("| %-4s | %-10s | %-15s | %-15s | %-15s | %-15s |\n", "No.", "ID", "Nama Customer", "Alamat", "Membership", "Uang");
        System.out.println("+===========================================================================================+");
        personList.stream().filter(customer -> customer instanceof Customer).map(customer -> (Customer) customer).forEach(customer -> {  
            System.out.printf("| %4s | %-10s | %-15s | %-15s | %-15s | %15s |\n", num, customer.getId(), customer.getName(), customer.getAddress(), customer.getMember().getMembershipName(), customer.getWallet());
            // num++;
        });
        System.out.println("+===========================================================================================+");
    }

    public static void showAllEmployee(List<Person> personList){
        int num = 0;
        System.out.println("+===============================================================+");
        System.out.printf("| %-4s | %-10s | %-15s | %-10s | %-10s |\n", "No.", "ID", "Nama Employee", "Alamat","Pengalaman");
        System.out.println("+===============================================================+");
        personList.stream().filter(person -> person instanceof Employee).map(person -> (Employee) person).forEach(person -> {  
            System.out.printf("| %4s | %-10s | %-15s | %-10s | %10s |\n", num, person.getId(), person.getName(), person.getAddress(), person.getExperience());
            // num++;
        });
        System.out.println("+===============================================================+");
    }

    public static void showAllServices(List<Service> serviceList){
        int num = 0;
        System.out.println("+======================================================================+");
        System.out.printf("| %-4s | %-10s | %-30s | %-15s |\n", "No.", "ID", "Nama Service", "Harga");
        System.out.println("+======================================================================+");
        serviceList.stream().forEach(service -> {
            System.out.printf("| %4s | %-10s | %-30s | %15s |\n", 
                num, service.getServiceId(), service.getServiceName(), service.getPrice());
        });
        System.out.println("+======================================================================+");
    }

    public static void showHistoryReservation(List<Reservation> reservationList){
        int num = 0;
        System.out.println("+==============================================================================================================================+");
        System.out.printf("| %-4s | %-10s | %-15s | %-70s | %-15s | %-15s |\n", "No.", "ID", "Nama Customer", "Service", "Total Biaya", "Workstage");
        System.out.println("+==============================================================================================================================+");
        reservationList.stream().forEach(reservation -> {
            System.out.printf("| %4s | %-10s | %-15s | %50s | %15s | %-15s |\n", 
                num, reservation.getReservationId(), reservation.getCustomer().getName(), reservation.getServices(), reservation.getReservationPrice(), reservation.getWorkstage());
        });
        System.out.println("+==============================================================================================================================+");
        double totalKeuntunganTemp = reservationList.stream()
            .filter(reservation -> reservation
            .getWorkstage().equals("Finish"))
            .mapToDouble(reservation -> reservation.getReservationPrice())
            .sum();
        System.out.printf("| %-88s | %-33s |\n", "Total Keuntungan", "Rp" + totalKeuntunganTemp);
        System.out.println("+==============================================================================================================================+");   
    }
}
