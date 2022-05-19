public class FileChaînée implements File {

	class Maillon {
		Object valeur;
		Maillon suivant;
	}
	
	private Maillon tête, queue; //Le premier est en tête de file
	
	public FileChaînée() {
		this.tête = null;
	}

	@Override
	public boolean estVide() {
		return this.tête == null;
	}

	@Override
	public Object premier() throws ExceptionFileVide {
		if (this.tête == null) throw new ExceptionFileVide();
		return this.tête.valeur;
	}

	@Override
	public void enfiler(Object o) {
		Maillon m = new Maillon();
		m.valeur = o;
		if (this.queue == null) {
			//première insertion
			this.tête = m;
		} else {
			//cas général
			this.queue.suivant= m;
		}
		this.queue = m;
	}

	@Override
	public void défiler() throws ExceptionFileVide {
		if (this.tête == null) throw new ExceptionFileVide();
		this.tête = this.tête.suivant;
	}
	
}
