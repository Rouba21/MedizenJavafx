package tn.esprit.models;
import javafx.util.StringConverter;

public class TopicStringConverter extends StringConverter<Topic> {

    @Override
    public String toString(Topic topic) {
        return topic != null ? topic.getTitre() : "";
    }

    @Override
    public Topic fromString(String title) {
        // Si nécessaire, implémentez la logique pour convertir une chaîne en un objet Topic
        // Par exemple, vous pouvez parcourir la liste des sujets pour trouver celui qui correspond au titre
        return null;
    }
}