class FileChaine {

  protected Maillon head;
  protected Maillon tail;

  boolean est_vide() {
    return (this.head == null);
  }

  Object premier() throws ExceptionFileVide {
    if (this.est_vide())
      throw new ExceptionFileVide();

    return this.head.value;
  }

  FileChaine enfiler(Object e) {
    Maillon m = new Maillon();
    m.value = e;

    if (this.est_vide())
      this.head = m;
    else if (this.tail == null) {
      this.head.next = m;
      this.tail = m;
    }
    else {
      this.tail.next = m;
      this.tail = m;
    }

    return this;
  }

  FileChaine defiler() throws ExceptionFileVide {
    if (this.est_vide())
      throw new ExceptionFileVide();

    this.head = this.head.next;
    return this;
  }
}