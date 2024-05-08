package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.Controllers.publication.CommentItemController;
import tn.esprit.Controllers.publication.PublicationItemController;
import tn.esprit.models.Commentaire;
import tn.esprit.models.Like;
import tn.esprit.models.Publication;
import tn.esprit.models.User;
import tn.esprit.services.CommentaireService;
import tn.esprit.services.LikeService;
import tn.esprit.services.PublicationService;
import tn.esprit.util.ControleDeSaisie;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AffichePub implements Initializable {
    boolean isLiked = false;
    @FXML
  private Button btnOptions ;
    @FXML
    private ImageView imgProfile;

    @FXML
    private Label username;

    @FXML
    private Label datepub;

    @FXML
    private Label contenupub;

    @FXML
    private ImageView imagepub;
    @FXML
    private ImageView imgReaction;
    @FXML
    private Text nbrLike;
    @FXML
    private  VBox Area;

    @FXML
    private VBox CommentList;

    @FXML
    private HBox commentArea;
    @FXML
    private Text NbrCommentaire;






    @FXML
    private Label nbReactions;
    @FXML
    private TextArea commentaireContenu;


    public long startTime = 0;
    private int x=0;
    private Publication lastDisplayedPublication;


    private final PublicationService publicationService = new PublicationService();

    @FXML
    void Commenter(ActionEvent event) {
       if( commentaireContenu.getText().isEmpty()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("vous devez entrer un commentaire!");
           alert.showAndWait();

       }else if(ControleDeSaisie.containsBadWords(commentaireContenu.getText())){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Ce Commentaire Contient Des Mots que ne convient pas avec nos reglement!");
           alert.showAndWait();

       }
       else {
           String contenu = commentaireContenu.getText();
           Date date = Date.valueOf(LocalDate.now());
           Publication publ = PublicationItemController.getPublicationToGetLikes();
           CommentaireService commentaireService = new CommentaireService();
           Commentaire c =new Commentaire(0, 0 ,contenu, null, date, publ,new User(1));
           commentaireService.add(c);
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Success");
           alert.setHeaderText(null);
           alert.setContentText("Commentaire Ajouté Avec Success!");
           alert.showAndWait();


           try {


               Publication publl = PublicationItemController.getPublicationToGetLikes();


               CommentaireService commentaireService1=new CommentaireService();

               List<Commentaire> commentaires=commentaireService1.getCommentsForPub(publl.getId());
               Area.getChildren().clear();
               for (int i = 0; i < commentaires.size(); i++) {
                   FXMLLoader fxmlLoader = new FXMLLoader();
                   fxmlLoader.setLocation(getClass().getResource("/publicationInterfaces/CommentItem.fxml"));
                   HBox pubItem = fxmlLoader.load();
                   CommentItemController userItemController = fxmlLoader.getController();
                   userItemController.setData(commentaires.get(i));
                   Area.getChildren().add(pubItem);
               }
           } catch (SQLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }


       }

        CommentaireService commentaireService=new CommentaireService();
        try {
            Publication pub = PublicationItemController.getPublicationToGetLikes();

            List<Commentaire> commentaires=commentaireService.getCommentsForPub(pub.getId());
            NbrCommentaire.setText(String.valueOf(commentaires.size()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void commentArea(MouseEvent event) {
        if (x==0) {
                CommentList.setVisible(true);
        x=1;
        } else if(x==1)
             {
                CommentList.setVisible(false);
                x=0;
        }


    }

    public void initialize(URL location, ResourceBundle resources) {
        CommentList.setVisible(false);
       Publication pub = PublicationItemController.getPublicationToGetLikes();
       LikeService likeService= new LikeService();
       List<Like> likes = likeService.getLikesForPub(pub.getId());
        nbrLike.setText(String.valueOf(likes.size()));
        CommentaireService commentaireService=new CommentaireService();
        try {
            List<Commentaire> commentaires=commentaireService.getCommentsForPub(pub.getId());
            NbrCommentaire.setText(String.valueOf(commentaires.size()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Supprimer");
        MenuItem editItem = new MenuItem("Modifier");

        deleteItem.setOnAction(event -> {
            // Récupérer la publication actuellement affichée
            Publication publication = getLastDisplayedPublication();

            if (publication != null) {
                try {
                    // Supprimer la publication de la base de données
                    publicationService.delete(publication.getId());

                    // Effacer les données affichées
                    clearDisplayedPublication();

                    // Afficher un message de confirmation ou mettre à jour l'interface utilisateur
                    System.out.println("Publication supprimée avec succès !");
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Gérer les exceptions liées à la suppression de la publication
                }
            } else {
                System.out.println("Aucune publication à supprimer !");
            }
        });



        editItem.setOnAction(event -> {
            try {
                // Charger le fichier FXML de la vue ModifierPub.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPub.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène avec le contenu chargé
                Scene scene = new Scene(root);

                // Créer une nouvelle fenêtre pour afficher la scène
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Modifier Publication");
                stage.initModality(Modality.APPLICATION_MODAL); // La nouvelle fenêtre est modale, elle doit être fermée avant de pouvoir interagir avec la fenêtre principale
                stage.show();

                // Obtenir le contrôleur de la vue ModifierPub.fxml et appeler la méthode setPublication() pour lui passer la publication à modifier
                ModifierPub controller = loader.getController();
                controller.setPublication(lastDisplayedPublication); // Passer la publication à modifier au contrôleur

            } catch (IOException e) {
                e.printStackTrace();
                // Gérer les erreurs de chargement de la vue ModifierPub.fxml
            }
        });

        contextMenu.getItems().addAll(deleteItem, editItem);

        // Associer le menu contextuel au bouton
        btnOptions.setContextMenu(contextMenu);
        // Charger les sujets disponibles
        try {
            // Récupérer la dernière publication ajoutée et afficher ses données
            Publication lastPublication = publicationService.getLastAddedPublication();
            if (lastPublication != null) {
                lastDisplayedPublication = lastPublication; // Assurez-vous que lastDisplayedPublication est correctement initialisé
                afficherPublication(lastPublication);

                // Charger et afficher l'image de la dernière publication si elle existe
                String imagePath = lastPublication.getImagePath();
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        Image image = new Image(imageFile.toURI().toString(), 123, 117, false, true);
                        imagepub.setImage(image);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions liées à la récupération des publications
        }


        try {


            Publication publ = PublicationItemController.getPublicationToGetLikes();


            CommentaireService commentaireService3=new CommentaireService();

            List<Commentaire> commentaires=commentaireService3.getCommentsForPub(publ.getId());
            for (int i = 0; i < commentaires.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/publicationInterfaces/CommentItem.fxml"));
                HBox pubItem = fxmlLoader.load();
                CommentItemController userItemController = fxmlLoader.getController();
                userItemController.setData(commentaires.get(i));
                Area.getChildren().add(pubItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Publication getLastDisplayedPublication() {return lastDisplayedPublication;
    }

    private void clearDisplayedPublication() {
        // Effacez les données affichées en réinitialisant les valeurs des éléments d'interface utilisateur
        // Par exemple :
        username.setText("");
        datepub.setText("");
        contenupub.setText("");
        imagepub.setImage(null); // Effacez l'image
        nbReactions.setText("0");

    }
    public void afficherPublication(Publication publication) {
        // Affichage des données de la publication
        username.setText("Montaha Al Adeb");
        datepub.setText(publication.getDatedecreation().toString());
        contenupub.setText(publication.getContenu());
        lastDisplayedPublication = publication;
        // Charger l'image à partir du nom de fichier si disponible
        String imageName = publication.getImage();
        if (imageName != null && !imageName.isEmpty()) {
            // Construire le chemin d'accès complet à partir du nom de fichier (vous devez spécifier le chemin de base)
            String imagePath = "/img/" + imageName; // Assurez-vous que le chemin est correct, et remplacez "/img/" par votre chemin réel
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            if (inputStream != null) {
                try {
                    Image image = new Image(inputStream);
                    imagepub.setImage(image);
                } catch (Exception e) {
                    // Gestion des erreurs
                    e.printStackTrace();
                    System.err.println("Erreur lors du chargement de l'image : " + imagePath);
                }
            } else {
                System.err.println("L'image n'existe pas : " + imagePath);
            }
        } else {
            // Si aucune image n'est associée à la publication, vous pouvez afficher une image par défaut ou laisser vide
            // Par exemple : imagepub.setImage(null);
        }

         // Remplacer "0" par le nombre réel de réactions

    }

    @FXML
    void onLikeContainerPressed(MouseEvent event) {
        if (!isLiked) {

            // Charger l'image de l'icône "like"
            Image likeImage = new Image(getClass().getResourceAsStream("/img/ic_like.png"));

            // Appliquer l'image de l'icône "like" à l'ImageView imgReaction
            imgReaction.setImage(likeImage);

            // Créer une instance de Like avec les données nécessaires
            Like like = new Like(0, lastDisplayedPublication.getId(), 1); // Remplacez userId par l'ID de l'utilisateur actuel

            // Ajouter ce like dans la base de données en utilisant le service LikeService
            LikeService likeService = new LikeService();
            likeService.add(like);

            // Mettre à jour l'interface utilisateur ou afficher un message de confirmation
            System.out.println("Like enregistré avec succès !");

            // Marquer la publication comme aimée
            isLiked = true;
            Publication pub = PublicationItemController.getPublicationToGetLikes();
            LikeService likeService1= new LikeService();
            List<Like> likes = likeService1.getLikesForPub(pub.getId());
            nbrLike.setText(String.valueOf(likes.size()));
        } else {

            // Supprimer le "like" de la base de données en utilisant le service LikeService
            LikeService likeService = new LikeService();
            likeService.deleteByPublicationAndUser(lastDisplayedPublication.getId(), 1); // Remplacez userId par l'ID de l'utilisateur actuel

            // Réinitialiser l'image à celle initiale
            // Chargez l'image initiale ou utilisez une autre méthode pour la réinitialiser
            Image initialImage = new Image(getClass().getResourceAsStream("/img/ic_like_outline.png")); // Remplacez "/img/initial_image.png" par le chemin de votre image initiale
            imgReaction.setImage(initialImage);

            // Mettre à jour l'interface utilisateur ou afficher un message de confirmation
            System.out.println("Like supprimé avec succès !");

            // Marquer la publication comme non aimée
            isLiked = false;
            Publication pub = PublicationItemController.getPublicationToGetLikes();
            LikeService likeService1= new LikeService();
            List<Like> likes = likeService1.getLikesForPub(pub.getId());
            nbrLike.setText(String.valueOf(likes.size()));
        }
    }
    @FXML
    public void showContextMenu(MouseEvent event) {
        btnOptions.getContextMenu().show(btnOptions, event.getScreenX(), event.getScreenY());

    }
    @FXML
    void naviguer(MouseEvent event) {
    }

}