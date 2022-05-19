public interface File {

	public boolean estVide();
	public Object premier() throws ExceptionFileVide;
	public void enfiler(Object o);
	public void défiler() throws ExceptionFileVide; 
	
}
