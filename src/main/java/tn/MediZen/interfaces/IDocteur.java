package tn.MediZen.interfaces;

import tn.MediZen.models.Docteur;

import java.util.List;

public interface IDocteur<D> {
    void add(D D);
    void update(D D);
    void  delete(D D);
    D getOne(int id);

    void add(Docteur Docteur);

    void update(Docteur Docteur);
    List<D> getAll();
    void delete(Docteur Docteur);

}
