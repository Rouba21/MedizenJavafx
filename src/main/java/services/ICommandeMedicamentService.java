package services;

import entites.Commande;
import entites.CommandeMedicament;
import entites.Medicament;

import java.util.Date;
import java.util.List;

public interface ICommandeMedicamentService {
    int ajouterCommandeMedicament(CommandeMedicament commandeMedicament);
    boolean modifierCommandeMedicament(CommandeMedicament commandeMedicament);
    boolean supprimerCommandeMedicament(CommandeMedicament commandeMedicament);
    List<CommandeMedicament> getCommandeMedicamentByCommande(Commande commande);
    List<CommandeMedicament> getCommandeMedicamentByMedicament(Medicament medicament);
}
