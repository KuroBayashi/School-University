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

    :return: OrderedDict
    """
    words_dict = defaultdict(lambda: defaultdict(list))

    with open(fname, 'r', encoding=encoding) as f:
        for word in f:
            word = word.rstrip()
            words_dict[len(word)][format_word(word)].append(word)

    # return OrderedDict(sorted(words_dict.items(), key=lambda t: t[0]))
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

def est_anagramme(word_1, word_2):
    """
    Verifie si les 2 mots sont des anagrammes

    :param word_1: Premier mot
    :param word_2: Deuxieme mot

    :type word_1: String
    :type word_2: String

    :return: Boolean
    """
    return format_word(word_1) == format_word(word_2)


def anagrammes(words_dict, word):
    """
    Retourne la liste des anagrammes du mot contenu dans le dictionnaire

    :param words_dict: Dictionnaire de mots
    :param word: Mot pour lequel on recherche les anagrammes

    :type words_dict: OrderedDict
    :type word: String

    :return: List
    """
    return words_dict[len(word)][format_word(word)]


def best(words_dict, longueur):
    """
    Construit la liste des mots de la longueur choisi ayant le plus d'anagrammes dans le dictionnaire

    :param words_dict: Dictionnaire de mots
    :param longueur: Longueur des mots

    :type words_dict: OrderedDict
    :type longueur: Integer

    :return: List
    """
    best_words = []
    maxi = 0

    if longueur in words_dict.keys():
        for key, words_list in words_dict[longueur].items():
            wl_len = len(words_list)

            if best_words:
                if wl_len > maxi:
                    best_words.clear()
                    best_words.append(words_list)
                    maxi = wl_len
                elif wl_len == maxi:
                    best_words.append(words_list)
            else:
                best_words.append(words_list)
                maxi = wl_len

    return best_words


if __name__ == "__main__":
    # Chargement du dictionnaire
    FILES = [('../lexiqueODS.txt', 'UTF-8'), ('../liste_de_mots_francais_frgut.txt', 'ISO-8859-1')]

    choix = 0
    words_dict = load_file(FILES[choix][0], FILES[choix][1])

    # Questions
    sol = []

    for n, d in words_dict.items():
        best_words = best(words_dict, n)

        if best_words:
            if sol:
                if len(sol[0]) < len(best_words[0]):
                    sol.clear()
                    sol += best_words
                elif len(sol[0]) == len(best_words[0]):
                    sol += best_words
            else:
                sol += best_words

    # Reponse 1
    print("Mots ayant le nombre maximal d'anagramme : ", sum(len(w) for w in sol))

    # Reponse 2
    print("Tous les mots ont la même longueur ? ", len(set([len(w) for w in sol])) <= 1)
