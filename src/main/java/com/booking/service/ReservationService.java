package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.ServiceRepository;

public class ReservationService {
    private static Scanner input = new Scanner(System.in);
    private static List<Service> serviceList = ServiceRepository.getAllService();

    public static void createReservation(List<Reservation> reservationList, List<Person> personList){
        String customerID = "";
        String employeeID = "";
        String serviceID = "";
        List<Service> servicesTemp = new ArrayList<>();
        String tambahService = "";

        int serviceTempCount = -1;

        Reservation reservationTemp;

        // Input customer id
        PrintService.showAllCustomer(personList);
        do {
            System.out.print("Silahkan masukan Customer ID : ");
            customerID = input.nextLine();

            if(!ValidationService.validateCustomerId(personList, customerID)){
                System.out.println("!!! Customer dengan ID tersebut tidak ditemukan");
            }
        } while (!ValidationService.validateCustomerId(personList, customerID));

        // Input employee id
        PrintService.showAllEmployee(personList);
        do {
            System.out.print("Silahkan masukan Employee ID : ");
            employeeID = input.nextLine();
            
            if(!ValidationService.validateEmployeeId(personList, employeeID)){
                System.out.println("!!! Employee dengan ID tersebut tidak ditemukan");
            }
        } while (!ValidationService.validateEmployeeId(personList, employeeID));
        
        // Input service id
        PrintService.showAllServices(serviceList);
        do {
            do {
                System.out.print("Silahkan masukan Service ID : ");
                serviceID = input.nextLine();

                if(!ValidationService.validateServiceId(serviceList, serviceID)){
                    System.out.println("!!! Service dengan ID tersebut tidak ditemukan");
                }
            } while (!ValidationService.validateServiceId(serviceList, serviceID));

            
            if(ValidationService.validateChoosenService(servicesTemp, serviceID)){
                System.out.println("---------");
                System.out.println("! Service sudah dipilih");
            }
            else{
                servicesTemp.add(getServiceByServiceId(serviceList, serviceID));
                serviceTempCount++;
            }

            System.out.println("---------");
            if(!(serviceTempCount == serviceList.size()-1)){
                do {
                    System.out.print("Ingin pilih service lain?(Y/T) : ");
                    tambahService = input.nextLine();
                    if(!ValidationService.validateAddMoreService(tambahService)){
                        System.out.println("!!! Aksi tersebut tidak tersedia");
                    }
                } while (!ValidationService.validateAddMoreService(tambahService));   
                System.out.println("---------");
            }
            
        } while (!tambahService.equalsIgnoreCase("T") && !(serviceTempCount == serviceList.size()-1));

        reservationTemp = new Reservation(null, getCustomerByCustomerId(personList, customerID), getEmployeeByEmployeeId(personList, employeeID), servicesTemp, "In Process");
        reservationList.add(reservationTemp);

        System.out.println("Booking berhasil");
        double totalBiayaTemp = servicesTemp.stream().mapToDouble(service -> service.getPrice()).sum();
        System.out.println("Total biaya booking : Rp" + totalBiayaTemp);
    }

    public static Customer getCustomerByCustomerId(List<Person> personList, String customerID){
        return personList.stream().
        filter(person -> person instanceof Customer)
        .map(person -> (Customer) person)
        .filter(person -> person.getId().equals(customerID))
        .findFirst()
        .orElse(null);
    }

    public static Employee getEmployeeByEmployeeId(List<Person> personList, String employeeID){
        return personList.stream().
        filter(person -> person instanceof Employee)
        .map(person -> (Employee) person)
        .filter(person -> person.getId().equals(employeeID))
        .findFirst()
        .orElse(null);
    }

    public static Service getServiceByServiceId(List<Service> serviceList, String serviceID){
        return serviceList
        .stream()
        .filter(service -> service.getServiceId()
        .equals(serviceID))
        .findFirst()
        .orElse(null);
    }

    public static void editReservationWorkstage(List<Reservation> reservationList){
        String reservationID = "";
        String action = "";

        PrintService.showRecentReservation(reservationList);

        if(!reservationList.isEmpty()){
            do {
                System.out.print("Silahkan masukan reservation ID : ");
                reservationID = input.nextLine();

                if(!ValidationService.validateReservationId(reservationList, reservationID) && !ValidationService.validateReservationWorkstage(reservationList, reservationID)){
                    System.out.println("!!! Reservation atas ID tersebut tidak ditemukan dan atau statusnya sudah \"finish\" atau \"canceled\"");
                } 
            } while (!ValidationService.validateReservationId(reservationList, reservationID) && !ValidationService.validateReservationWorkstage(reservationList, reservationID));

            do {
                System.out.print("Selesaikan reservasi : ");
                action = input.nextLine();

                if(!ValidationService.validateFinishReservationAction(action)){
                    System.out.println("!!! Aksi tersebut tidak tersedia");
                } 
            } while (!ValidationService.validateFinishReservationAction(action));

            finishingReservation(reservationList, reservationID, action);
        }
        else{
            System.out.println("(Tidak ada data)");
        }
    }

    public static void finishingReservation(List<Reservation> reservationList, String reservationID, String action){
        if(action.equalsIgnoreCase("Finish")){
            reservationList.stream().filter(reservation -> reservation.getReservationId().equals(reservationID)).forEach(reservation -> reservation.setWorkstage("Finish"));
        }
        else{
            reservationList.stream().filter(reservation -> reservation.getReservationId().equals(reservationID)).forEach(reservation -> reservation.setWorkstage("Canceled"));
        }

        System.out.println("Reservasi dengan id " + reservationID + "sudah " + action);
    }

    // Silahkan tambahkan function lain, dan ubah function diatas sesuai kebutuhan
}
