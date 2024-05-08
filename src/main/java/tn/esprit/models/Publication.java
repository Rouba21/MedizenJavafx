package tn.esprit.models;

import java.util.Date;

public class Publication {
    //attributs
    private int id ;
    private String image ,contenu;
    private Date datedecreation ;
    private Topic topic;
    private int topicId;
    private String imagePath;
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    //constructors

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public Publication(String image, String contenu, Date datedecreation, Topic topic) {
        this.image = image;
        this.contenu = contenu;
        this.datedecreation = datedecreation;
        this.topic = topic;
    }

    public Publication(int id, String image, String contenu, Date datedecreation,Topic topic) {
        this.id = id;
        this.image = image;
        this.contenu = contenu;
        this.datedecreation = datedecreation;
        this.topic = topic;
    }

    public Publication() {

    }

    public Publication(int i) {
    }
    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDatedecreation() {
        return datedecreation;
    }

    public void setDatedecreation(Date datedecreation) {
        this.datedecreation = datedecreation;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", contenu='" + contenu + '\'' +
                ", datedecreation=" + datedecreation +
                ", topic=" + topic +
                '}';
    }


}
