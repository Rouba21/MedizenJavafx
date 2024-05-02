package services;

import entites.Medicament;
import java.util.List;

public interface IMedicamentService {
    int ajouterMedicament(Medicament medicament);
    boolean modifierMedicament(Medicament medicament);
    boolean supprimerMedicament(Medicament medicament);
    Medicament getMedicamentById(int id);
    List<Medicament> searchMedicamentByName(String name);
    List<Medicament> afficherMedicaments();
}
