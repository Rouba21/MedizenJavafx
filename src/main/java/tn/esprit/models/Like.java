package tn.esprit.models;

public class Like {
    private int id;
    private int publicationId;
    private int userId;

    public Like(int id, int publicationId, int userId) {
        this.id = id;
        this.publicationId = publicationId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", publicationId=" + publicationId +
                ", userId=" + userId +
                '}';
    }
}
