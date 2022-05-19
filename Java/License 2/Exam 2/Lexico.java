import java.util.ArrayList;
import java.util.List;

public class Lexico {
	
	public HashMapLexico premièreLettres;
	
	Lexico() {
		this.premièreLettres = new HashMapLexico();
	}
	
	public void ajouterMot(String word) {
		if (this.premièreLettres.containsKey())
		    this.premièreLettres.ajouter(word);
	}
	
	public HashMapLexico localiser(String prefixe) throws ExceptionPrefixeInconnu {
		HashMapLexico hml = this.premièreLettres;
		
		for (Character c : prefixe.toCharArray()) {
			if (hml.containsKey(c))
				hml = hml.get(c);
			else
				throw new ExceptionPrefixeInconnu();
		}
		
		return hml;
	}
	
	public List<String> compléterPréfixe(String prefixe, HashMapLexico hml) {
		List<String> words = new ArrayList<>();
		
		for (Character c : hml.keySet())
			words.addAll(this.compléterPréfixe(prefixe + c, hml.get(c)));
		
		if (prefixe.charAt(prefixe.length()-1) == '!')
			words.add(prefixe.substring(0, prefixe.length()-1));
		
		return words;
	}
	
	public List<String> suggestions(String prefixe) {
		try {
			return compléterPréfixe(prefixe, localiser(prefixe));
		} catch (ExceptionPrefixeInconnu e) {
			e.printStackTrace();
		}
		return null;
	}
		
	
}
