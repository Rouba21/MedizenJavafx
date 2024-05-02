package tn.esprit.interfaces;

import tn.esprit.models.Sponseur;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    //CRUD

    public int add(T t) throws SQLException;



    public List<?> getAllSponseurs() throws SQLException;

    public List<?> getAllEvents() throws SQLException;

    public void update(T t) throws SQLException;
    public void delete(int id) throws SQLException;
    public List<T> afficher() throws SQLException;

    List<?> getAllEvent() throws SQLException;

    public void addSponseurToEvent(int eventId, int sponseurId) throws SQLException;

    public void updateSponseurInEvent(int eventId, int sponseurId) throws SQLException;
    public List<Sponseur> getSponseursByEvent(int eventId) throws SQLException;

    void ajouterAssociationEvenementSponseur(int eventId, int sponseurId) throws SQLException;



}
