package tn.esprit.models;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTableCell<S, T> extends TableCell<S, String> {
    private final ImageView imageView;

    public ImageTableCell() {
        this.imageView = new ImageView();
        imageView.setFitWidth(30); // Largeur de l'image
        imageView.setFitHeight(20); // Hauteur de l'image
        setGraphic(imageView);
    }

    @Override
    protected void updateItem(String imagePath, boolean empty) {
        super.updateItem(imagePath, empty);

        if (empty || imagePath == null || imagePath.isEmpty()) {
            imageView.setImage(null);
            setText(null);
            setGraphic(null);
        } else {
            Image image = new Image("file:" + imagePath); // Charger l'image depuis le chemin
            imageView.setImage(image);
            setText(null);
            setGraphic(imageView);
        }
    }
}
