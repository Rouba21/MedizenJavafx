package Medizen.Utils;


    public class Generator {

        public static String generateCode(int length) {
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int index = (int) (chars.length() * Math.random());
                sb.append(chars.charAt(index));
            }
            return sb.toString();
        }

}
