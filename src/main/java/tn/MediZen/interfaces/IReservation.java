package tn.MediZen.interfaces;

import tn.MediZen.models.Reservation;

import java.util.List;

public interface IReservation<R> {
    void add(R R);

    void update(R R);

    void delete(R R);

    R getOne(int id);

    void add(Reservation reservation);

    void update(Reservation reservation);

    List<R> getAll();

    void delete(Reservation reservation);

}
