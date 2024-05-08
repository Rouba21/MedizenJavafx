package tn.esprit.Controllers;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.models.Topic;

public class TopicListCell extends ListCell<Topic> {

    @Override
    protected void updateItem(Topic topic, boolean empty) {
        super.updateItem(topic, empty);

        if (empty || topic == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Créer un conteneur HBox pour afficher les informations du sujet
            HBox container = new HBox();
            container.setSpacing(10);


            // Afficher le titre du sujet
            Text titleText = new Text(topic.getTitre());

            // Vous pouvez ajouter plus d'informations ici, comme la date, l'auteur, etc.



            // Appliquer le conteneur personnalisé à la cellule
            setGraphic(container);
        }
    }
}
