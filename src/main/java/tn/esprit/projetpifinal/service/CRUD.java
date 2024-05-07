package tn.esprit.projetpifinal.service;

import tn.esprit.projetpifinal.models.Etablissement;

import java.sql.SQLException;
import java.util.List;
public interface CRUD<T> {
    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;

    void supprimer(int id) throws SQLException;

    public List<T> afficher() throws SQLException;
}