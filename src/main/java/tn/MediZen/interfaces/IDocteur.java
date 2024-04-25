package tn.MediZen.interfaces;

import tn.MediZen.models.Docteur;

public interface IDocteur<R> {
    void add(R R);
    void update(R R);
    void  delete(R R);
    R getOne(int id);

    void add(Docteur Docteur);

    void update(Docteur Docteur);

    void delete(Docteur Docteur);

}
