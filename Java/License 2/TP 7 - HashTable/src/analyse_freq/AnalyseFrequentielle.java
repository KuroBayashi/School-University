package analyse_freq;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyseFrequentielle {

  static String texteFR = "la cryptographie est une des disciplines de la cryptologie s'attachant a"
      + "proteger des messages, assurant confidentialite, authenticite et integrite, en s'aidant souvent"
      + "de secrets ou cles. elle se distingue de la steganographie qui fait passer inapercu un message"
      + "dans un autre message alors que la cryptographie rend un message inintelligible a autre que"
      + "qui de droit. elle est utilisee depuis l'antiquite, mais certaines de ses methodes les plus"
      + "importantes comme la cryptographie asymetrique, datent de la fin du vingtieme siecle.";

  static String texteEN = "cryptography prior to the modern age was effectively synonymous with encryption,"
      + "the conversion of information from a readable state to apparent nonsense. the originator of an"
      + "encrypted message shared the decoding technique needed to recover the original information"
      + "only with intended recipients, thereby precluding unwanted persons from doing the same."
      + "the cryptography literature often uses alice for the sender, bob for the intended recipient, and eve"
      + "for the adversary.";

  static String texteCS = "kryptografie neboli sifrovani je nauka o metodach utajovani smyslu zprav prevodem"
      + "do podoby, ktera je citelná jen se specialni znalosti. slovo kryptografie pochazi z rectiny kryptos"
      + "je skryty a graphein znamena psat. Nekdy je pojem obecneji pouzivan pro vedu o cemkoli spojenem se "
      + "siframi jako alternativa k pojmu kryptologie. kryptologie zahrnuje kryptografii a kryptoanalyzu, "
      + "neboli lusteni zasifrovanych zprav.";

  public List<Character> analyser(String text) {
    HashMap<Character, Integer> letters = new HashMap<>();

    text.chars().forEach(
        i -> {
          if (Character.isLetter((char) i)) {
            if (letters.containsKey((char) i))
              letters.replace((char) i, letters.get((char) i) + 1);
            else
              letters.put((char) i, 1);
          }
        }
    );

    List<Character> list = new ArrayList<>();

    letters.entrySet().stream()
        .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
        .limit(6)
        .forEach(e -> list.add(e.getKey()));

    return list;
  }

  public List<String> analyserDigrammes(String text) {
    HashMap<String, Integer> digrams = new HashMap<>();

    Matcher m = Pattern.compile("(\\w\\w)").matcher(text);
    while (m.find()) {
      if (digrams.containsKey(m.group(1)))
        digrams.replace(m.group(1), digrams.get(m.group(1)) + 1);
      else
        digrams.put(m.group(1), 1);
    }

    List<String> list = new ArrayList<>();

    digrams.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(6)
        .forEach(e -> list.add(e.getKey()));

    return list;
  }

  public static void main(String[] args) {
    AnalyseFrequentielle t = new AnalyseFrequentielle();
    List<Character> lettersFR = t.analyser(texteFR);
    List<Character> lettersEN = t.analyser(texteEN);
    List<Character> lettersCS = t.analyser(texteCS);

    List<String> digramsFR = t.analyserDigrammes(texteFR);
    List<String> digramsEN = t.analyserDigrammes(texteEN);
    List<String> digramsCS = t.analyserDigrammes(texteCS);

    System.out.println("#############\n## Letters ##\n#############");
    System.out.print("FR :");
    lettersFR.forEach(l -> System.out.print(l + " "));
    System.out.print("\nEN :");
    lettersEN.forEach(l -> System.out.print(l + " "));
    System.out.print("\nCS :");
    lettersCS.forEach(l -> System.out.print(l + " "));

    System.out.println("\n\n#############\n## Digrams ##\n#############");
    System.out.print("FR : ");
    digramsFR.forEach(l -> System.out.print(l + " "));
    System.out.print("\nEN : ");
    digramsEN.forEach(l -> System.out.print(l + " "));
    System.out.print("\nCS : ");
    digramsCS.forEach(l -> System.out.print(l + " "));
  }
}
