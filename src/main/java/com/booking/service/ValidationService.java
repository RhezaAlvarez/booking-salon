package com.booking.service;

import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class ValidationService {
    // Buatlah function sesuai dengan kebutuhan
    public static void validateInput(){

    }

    public static boolean validateCustomerId(List<Person> personList, String customerID){
        return personList.stream().filter(person -> person instanceof Customer).anyMatch(person -> person.getId().equals(customerID));
    }

    public static boolean validateEmployeeId(List<Person> personList, String employeeID){
        return personList.stream().filter(person -> person instanceof Employee).anyMatch(person -> person.getId().equals(employeeID));
    }

    public static boolean validateServiceId(List<Service> serviceList, String serviceID){
        return serviceList.stream().anyMatch(service -> service.getServiceId().equals(serviceID));
    }

    public static boolean validateReservationId(List<Reservation> reservationList, String reservationID){
        return reservationList.stream().anyMatch(reservation -> reservation.getReservationId().equals(reservationID));
    }

    public static boolean validateReservationWorkstage(List<Reservation> reservationList, String reservationID){
        return reservationList.stream().filter(reservation -> reservation.getReservationId().equals(reservationID)).anyMatch(reservation -> reservation.getReservationId().equals("In Process"));
    }

    public static boolean validateFinishReservationAction(String action){
        boolean result = false;
        if(action.equalsIgnoreCase("Finish") || action.equalsIgnoreCase("Cancel")){
            result = true;
        }
        return result;
    }
}