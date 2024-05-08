package test;
import tn.esprit.models.*;
import tn.esprit.services.LikeServiceMontaha;


public class Main {
    public static void main(String[] args) {
        // Création d'une instance de LikeService
        LikeServiceMontaha likeService = new LikeServiceMontaha();

        // Création d'un nouvel objet Like avec les identifiants de la publication et de l'utilisateur
        Like newLike = new Like(1, 1, 1); // Remplacez 1 par l'ID de la publication et 2 par l'ID de l'utilisateur appropriés

        // Appel de la méthode add de LikeService pour ajouter le nouvel objet Like
        likeService.add(newLike);


    }
}