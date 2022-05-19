public class TriSelection {

    public static String sort(String str) {
        int k;
        char tmp;
        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length -1; i++) {
            k = i;

            for (int j = i +1 ; j < chars.length -1; j++) {
                if (chars[j] < chars[k])
                    k = j;
            }

            tmp = chars[i];
            chars[i] = chars[k];
            chars[k] = tmp;
        }

        return String.valueOf(chars);
    }

}
