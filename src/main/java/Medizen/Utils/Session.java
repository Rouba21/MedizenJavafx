package Medizen.Utils;
import Medizen.Models.User;

    public class Session {
        private User user ;
        private static Session instance ;
        private Session (User user)
        {
            this.user=user;
        }
        public static Session start_session(User user)
        {
            if (instance == null){
                instance= new Session (user);}
            return instance;
        }

        public User getUser() {
            return user;
        }

        public static Session getInstance() {
            return instance;
        }
        public static void ClearSession(){
            instance=null;
        }


}
