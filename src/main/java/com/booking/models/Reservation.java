package com.booking.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private static int idNum = 0; 
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;
    //   workStage (In Process, Finish, Canceled)

    public Reservation(String reservationId, Customer customer, Employee employee, List<Service> services,
            String workstage) {
        this.reservationId = generateReservationID();
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice();
        this.workstage = workstage;
    };

    private String generateReservationID(){
        if(idNum < 10){
            idNum++;
            return "Rsv-0" + idNum;
        }
        else{
            idNum++;
            return "Rsv-" + idNum;
        }
    }

    private double calculateReservationPrice(){
        return services.stream().mapToDouble(service -> service.getPrice()).sum();
    }
}