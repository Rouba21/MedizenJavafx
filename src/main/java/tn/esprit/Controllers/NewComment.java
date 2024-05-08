package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import tn.esprit.models.Commentaire;
import tn.esprit.models.Publication;
import tn.esprit.models.User;
import tn.esprit.services.CommentaireService;

import java.sql.Date;
import java.time.LocalDate;


public class NewComment {

    @FXML
    private TextArea textcomment;

    // Méthode appelée lorsque le bouton d'envoi est cliqué
    @FXML
    private void onSendClicked() {
        // Récupérer le contenu du commentaire depuis le champ TextArea
        String contenu = textcomment.getText();

        // Récupérer l'ID de la publication à laquelle le commentaire est associé (vous devez implémenter cette logique)
        int publicationId = 1;

        // Récupérer l'ID de l'utilisateur qui ajoute le commentaire (vous devez implémenter cette logique)
        int userId = 1;

        // Créer un objet Publication
        Publication publication = new Publication(publicationId);

        // Créer un objet User
        User user = new User(userId);

        // Créer un objet Commentaire avec les informations récupérées
        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(contenu);
        commentaire.setDatedecreation(  Date.valueOf(LocalDate.now())); // Utilisation de la date actuelle
        commentaire.setPublication(publication);
        commentaire.setUser(user);

        // Ajouter le commentaire en utilisant le service CommentaireService
        CommentaireService commentaireService = new CommentaireService();
        commentaireService.add(commentaire);

        // Effacer le champ de texte après l'ajout du commentaire
        textcomment.clear();

        // Vous pouvez ajouter ici une logique pour rafraîchir l'affichage des commentaires après l'ajout du nouveau commentaire
    }
}