package services;

import entites.Commande;
import java.util.List;

public interface ICommandeService {
    int ajouterCommande(Commande commande);
    boolean modifierCommande(Commande commande);
    boolean supprimerCommande(Commande commande);
    Commande getCommandeById(int id);
    List<Commande> searchCommandeByStatus(String status);
    List<Commande> afficherCommandes();
}
