package tn.esprit.Controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.models.Event;
import tn.esprit.services.EventService;
import tn.esprit.util.sendEmail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardController {

    private sendEmail mailSender;
    private Event event;
    private EventService eventService;
    private boolean participationNotificationShown = false;

    @FXML
    private ImageView event_image;

    @FXML
    private Label event_name; // Déclaration du Label pour le nom de l'événement

    @FXML
    private void initialize() {
        this.mailSender = new sendEmail();
        this.eventService = new EventService();
    }

    public void setData(Event event) {
        this.event = event;
        String imageURL = event.getImageURL();
        if (imageURL != null && !imageURL.isEmpty()) {
            try {
                Image image = new Image("file:" + imageURL);
                event_image.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert("Failed to load image", "An error occurred while loading the image.");
            }
        }
        // Mettez à jour le titre de l'événement sur la carte
        event_name.setText(event.getTitre());
    }

    @FXML
    private void handleParticiperButtonAction(ActionEvent actionEvent) {
        if (event != null) {
            int userId = getCurrentUserId();
            try {
                // Participer à l'événement
                eventService.participer(event, userId);

                String to = "nadia.bouaicha@esprit.tn";
                String subject = "Confirmation de participation à l'événement";
                String codeToInsert = "12345";
                String clientName = "John Doe";

                // Envoi de l'e-mail de confirmation
                mailSender.sendMail(to, subject, codeToInsert, clientName);

                // Affichage de la notification de participation
                showParticipationNotification();

            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("Erreur", "Échec de la participation à l'événement : " + e.getMessage());
            }
        }
    }

    private void showParticipationNotification() {
        if (!participationNotificationShown) {
            String message = "Vous avez participé à l'événement : " + event.getTitre();
            String imagePath = "/img/notif.jpg"; // Chemin vers l'image "notif.jpg"
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            showCustomNotification(message, Duration.seconds(3), image, this::showProposedEvents);
            participationNotificationShown = true;
        }
    }



    private List<Event> getProposedEvents() {
        try {
            List<Event> events = eventService.getAllEvents();
            if (events != null && events.size() >= 3) {
                Random random = new Random();
                List<Event> selectedEvents = new ArrayList<>();

                // Sélectionner quatre événements aléatoires distincts
                while (selectedEvents.size() < 4) {
                    int randomIndex = random.nextInt(events.size());
                    Event randomEvent = events.get(randomIndex);
                    if (!selectedEvents.contains(randomEvent)) {
                        selectedEvents.add(randomEvent);
                    }
                }
                return selectedEvents;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur", "Échec de récupération des événements : " + e.getMessage());
        }
        return new ArrayList<>(); // Retourner une liste vide par défaut
    }

    private void showEventProposals(List<Event> events) {
        double initialY = 100; // Position verticale initiale pour la première notification
        double deltaY = 700; // Écart vertical entre les notifications

        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            String title = event.getTitre();
            String lieu = event.getLieu();
            String date = event.getDateDebut().toString(); // Supposons que la date est stockée comme une chaîne dans l'objet Event

            // Calculer la position Y pour chaque notification
            double positionY = initialY + (i * deltaY);

            // Construire le message pour l'événement
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Titre : ").append(title).append("\n");
            messageBuilder.append("Lieu : ").append(lieu).append("\n");
            messageBuilder.append("Date : ").append(date).append("\n"); // Ajouter la date à la notification

            String imageURL = event.getImageURL();

            // Vérifier si l'événement a une URL d'image valide
            if (imageURL != null && !imageURL.isEmpty()) {
                try {
                    // Charger l'image à partir de l'URL de l'événement
                    Image eventImage = new Image("file:" + imageURL);

                    // Afficher la notification avec le titre, le lieu, la date et l'image de l'événement
                    showEventNotification(messageBuilder.toString(), eventImage, Duration.seconds(6), positionY);
                } catch (Exception e) {
                    e.printStackTrace();
                    showErrorAlert("Failed to load image", "An error occurred while loading the event image.");
                }
            } else {
                // Afficher la notification sans image
                showEventNotification(messageBuilder.toString(), null, Duration.seconds(6), positionY);
            }
        }
    }


    private void showCustomNotification(String message, Duration hideAfter, Image image, Runnable onFinished) {
        Notifications notifications = Notifications.create()
                .title("Nouvelle participation !")
                .text(message)
                .hideAfter(hideAfter)
                .position(Pos.BOTTOM_RIGHT);

        if (image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100); // Définir la largeur souhaitée
            imageView.setFitHeight(100); // Définir la hauteur souhaitée
            notifications.graphic(imageView);
        }

        notifications.owner(null);
        notifications.show();

        // Déclencher l'action après un délai
        PauseTransition delay = new PauseTransition(hideAfter);
        delay.setOnFinished(event -> {
            if (onFinished != null) {
                onFinished.run();
            }
        });
        delay.play();
    }

    private void showCustomInvitationNotification() {
        String message = "Explorez notre large gamme d'événements passionnants sur Medizen ! ";
        String logoPath = "/img/logo.jpg"; // Chemin vers le logo de votre application

        // Charger l'image du logo
        Image logoImage = new Image(getClass().getResourceAsStream(logoPath));

        // Créer un ImageView avec l'image du logo
        ImageView imageView = new ImageView(logoImage);

        // Définir la largeur et la hauteur souhaitées de l'image
        double desiredWidth = 100; // Largeur souhaitée en pixels
        double desiredHeight = 100; // Hauteur souhaitée en pixels
        imageView.setFitWidth(desiredWidth);
        imageView.setFitHeight(desiredHeight);

        // Afficher la notification avec le logo ajusté en taille et une durée d'affichage prolongée
        Notifications.create()
                .title("Consultez nos événements !")
                .text(message)
                .graphic(imageView) // Utiliser l'ImageView avec le logo ajusté en taille
                .hideAfter(Duration.seconds(7)) // Afficher la notification pendant 10 secondes
                .position(Pos.TOP_RIGHT) // Position de la notification
                .show();
    }




    private void showEventNotification(String message, Image eventImage, Duration hideAfter, double positionY) {
        Notifications notifications = Notifications.create()
                .title("D'autres propositions !")
                .text(message)
                .hideAfter(hideAfter)
                .darkStyle()
                .position(Pos.BOTTOM_RIGHT);

        if (eventImage != null) {
            ImageView imageView = new ImageView(eventImage);
            imageView.setFitWidth(100); // Définir la largeur souhaitée
            imageView.setFitHeight(100); // Définir la hauteur souhaitée
            notifications.graphic(imageView);
        }

        notifications.owner(null);
        notifications.show();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showProposedEvents() {
        // Attendre la disparition de la notification de participation
        PauseTransition delayBeforeProposals = new PauseTransition(Duration.seconds(3));
        delayBeforeProposals.setOnFinished(event -> {
            // Récupérer les événements proposés
            List<Event> events = getProposedEvents();
            if (events != null && !events.isEmpty()) {
                // Afficher les deux premiers événements
                showEventProposals(events.subList(0, Math.min(2, events.size())));
                // Attendre avant d'afficher les événements suivants
                PauseTransition delayBetweenGroups = new PauseTransition(Duration.seconds(8));
                delayBetweenGroups.setOnFinished(event2 -> {
                    // Afficher les événements suivants s'ils existent
                    if (events.size() > 2) {
                        showEventProposals(events.subList(2, Math.min(4, events.size())));
                    }

                    // Afficher la notification d'invitation personnalisée après la disparition des événements proposés
                    showCustomInvitationNotification();
                });
                delayBetweenGroups.play();
            }
        });
        delayBeforeProposals.play();
    }


    private int getCurrentUserId() {
        return 1; // Exemple de récupération de l'ID de l'utilisateur connecté
    }
}
