public class ABR extends ArbreBinaire {

  ABR() {
    super();
  }

  ABR(Object r, ArbreBinaire g, ArbreBinaire d) {
    super(r, g, d);
  }

  ABR inserer(Comparable element) {
    if (this.estVide())
      return new ABR(element, new ABR(), new ABR());
    else if (element.compareTo(this.racine) >= 0)
      return new ABR(this.racine, ((ABR) this.sag()).inserer(element), this.sad());
    else
      return new ABR(this.racine, this.sag(), ((ABR) this.sad()).inserer(element));
  }

  Boolean rechercher(Comparable element){
    if (this.estVide())
      return false;
    else if (element.compareTo(this.racine) == 0)
      return true;
    else
      if(element.compareTo(this.racine) > 0)
        return ((ABR) this.sag()).rechercher(element);
      else
        return ((ABR) this.sad()).rechercher(element);
  }

  ABR supprimer(Comparable element){
    if (this.estVide())
      return this;
    else if (this.racine == element && this.sag().estVide())
      return (ABR) this.sad();
    else if (this.racine == element && this.sad().estVide())
      return (ABR) this.sag();
    else if (this.racine == element)
      return new ABR(this.max(this.sag()), );

  }
}