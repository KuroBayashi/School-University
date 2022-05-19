from unicodedata import normalize
from collections import defaultdict, OrderedDict


def format_word(word):
    """
    Formate le mot en minuscule et tri les lettres par ordre alphabetique

    :param word: Mot a formater

    :type word: String

    :return: String
    """
    return ''.join(sorted(normalize('NFD', word).encode('ascii', 'ignore').lower().decode('ascii')))


def load_file(fname, encoding):
    """
    Constuit le dictionnaire a partir d'un fichier de mots

    :param fname: Emplacement du fichier
    :param encoding: Encodage du fichier

    :type fname: string
    :type encoding: string

    :return: DefaultDict
    """
    words_dict = defaultdict(lambda: defaultdict(list))

    with open(fname, 'r', encoding=encoding) as f:
        for word in f:
            word = word.rstrip()
            words_dict[len(word)][format_word(word)].append(word)

    return OrderedDict(
        sorted(
            {
                n: OrderedDict(
                    sorted(
                        d.items(),
                        key=lambda t: t[0]
                    )
                ) for n, d in words_dict.items()
            }.items(),
            key=lambda t: t[0]
        )
    )


def est_presque_anagramme(word_1, word_2, gap):
    """
    Verifie si 2 mots sont presques anagrammes

    :param word_1: Premier mot (court)
    :param word_2: Deuxieme mot (long)
    :param gap: Nombre de lettre d'ecart

    :type word_1: String
    :type word_2: String
    :type gap: Integer

    :return: Boolean
    """
    if len(word_1) + gap != len(word_2):
        return False

    word_1 = format_word(word_1)
    word_2 = format_word(word_2)

    for letter in word_1:
        if letter in word_2:
            word_2 = word_2.replace(letter, "", 1)
        else:
            return False

    return True


def presque_anagrammes(words_dict, word, gap):
    """
    Construit la liste des presques anagrammes d'un mot contenu dans un dictionnaire

    :param words_dict: Dictionnaire de mots
    :param word: Mot pour lequel on recherche les presques anagrammes
    :param gap: Nombre de lettre d'ecart

    :type words_dict: OrderedDict
    :type word: String
    :type gap: Integer

    :return: List
    """
    presque_words = []

    n = len(word) + gap
    if n in words_dict.keys():
        for key, words_list in words_dict[n].items():
            if est_presque_anagramme(word, key, gap):
                presque_words += words_list

    return presque_words


if __name__ == "__main__":
    # Chargement du dictionnaire
    FILES = [('../lexiqueODS.txt', 'UTF-8'), ('../liste_de_mots_francais_frgut.txt', 'ISO-8859-1')]

    choix = 0
    words_dict = load_file(FILES[choix][0], FILES[choix][1])

    # Questions
    words = [] # Liste des mots (court) qui ont le plus de presque-anagramme
    sol = [] # Liste des presque-anagrammes
    gap = 1 # Nombre de lettres d'ecart

    for d in words_dict.values():
        for word, l in d.items():
            presque_words = []

            n = len(word) + gap
            if n in words_dict.keys():
                for key, words_list in words_dict[n].items():
                    if est_presque_anagramme(key, word, gap):
                        presque_words += words_list

            if presque_words:
                if sol:
                    if len(sol[0]) < len(presque_words[0]):
                        sol.clear()
                        sol += presque_words
                        words.clear()
                        words += l
                    elif len(sol[0]) == len(presque_words[0]):
                        sol += presque_words
                        words += l
                else:
                    sol += presque_words
                    words += l

    # Reponse 1
    print("Mots ayant le nombre maximal de presque-anagramme :", len(words))

    # Reponse 2
    print("Tous les mots ont la même longueur ? ", len(set([len(w) for w in sol])) == 1)