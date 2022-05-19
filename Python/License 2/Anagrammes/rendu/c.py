from collections import defaultdict, OrderedDict
from unicodedata import normalize
from codecs import open
from itertools import product


def format_word(word):
    """
    Formate le mot en minuscules sans accents puis ordonne des lettres alphabetiquement

    :param word: Mot a formater

    :type word: string

    :return: String
    """
    return ''.join(sorted(normalize('NFD', word).encode('ascii', 'ignore').lower().decode('ascii')))


def load_file(fname, encoding):
    """
    Constuit le dictionnaire a partir d'un fichier

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

    return words_dict


def reduce_dict(words_dict, word):
    """
    Construit le dictionnaire reduit aux mots dont les lettres sont contenues dans le mot d'origine.

    :param words_dict: Dictionnaire contenant tous les mots
    :param word: Mot d'origine

    :type words_dict: OrderecDict
    :type word: String

    :return: OrderedDict
    """
    def contains(origin, letters):
        """
        Verifie que toutes les lettres sont contenues dans le mot d'origine.

        :param origin: Mot d'origine
        :param letters: Lettres recherche

        :type origin: string
        :type letters: string

        :return: Boolean
        """
        if len(letters) > len(origin):
            return False

        for letter in letters:
            if letter in origin:
                origin = origin.replace(letter, "", 1)
            else:
                return False
        return True

    # On supprime du dictionnaire tous les mots dont les lettres ne sont pas contenu dans le mot d'origine
    return OrderedDict(sorted(
        { n: { k: v for k, v in d.items() if contains(word, k)} for n, d in words_dict.items()}.items(),
        key=lambda t: t[0]
    ))


def A1(words_dict, word):
    """
    Recherche toutes les combinaisons de mot formant une anagramme d'un mot choisi.

    :param words_dict: Dictionnaire complet
    :param word: Mot pour lequel on recherche des anagrammes

    :type words_dict: DefaultDict
    :type word: String

    :return: List
    """
    f_word = format_word(word)
    f_word_len = len(f_word)
    words_dict = reduce_dict(words_dict, f_word)
    sol = []
    sol_list = []

    def build_solutions(s_list):
        """
        Construit la liste de toutes les solutions possibles

        :param s_list: Liste des combinaisons de cle qui sont solutions

        :type s_list: List

        :return:
        """
        solutions = []

        for comb_keys in s_list:
            for comb_lists in [[words_dict[len(key)][key] for key in comb_keys]]:
                solutions += list(product(*comb_lists))

        return solutions

    def ajout_possible(word, str_s):
        """
        Verifie que les lettres de la combinaison sont contenues dans le mot d'origine.

        :param word: Mot d'origine
        :param str_s: Lettres de la combinaison

        :type word: String
        :type str_s: String

        :return: Boolean
        """
        for c in str_s:
            if c in word:
                word = word.replace(c, '', 1)
            else:
                return False
        return True

    def est_solution(str_s):
        """
        Verifie que la combinaison est solution

        :param str_s: Combinaison sous forme de chaine de caractere

        :type str_s: String

        :return: Boolean
        """
        return f_word == ''.join(sorted(str_s))

    def placer(p, s, s_list):
        """
        Essai de placer un nouveau mot dans la combinaison

        :param p: Nombre de mot dans la combinaison
        :param s: Combinaison actuelle
        :param s_list: Liste de toutes les solutions trouvees

        :type p: Integer
        :type s: List
        :type s_list: List
        """
        if est_solution(''.join(s)):
            s_sort = sorted(s)
            if s_sort not in s_list:
                s_list.append(s_sort[:])
        else:
            r = f_word_len - len(''.join(s))
            for n in words_dict.keys():
                if n <= r:
                    for f_key in words_dict[n].keys():
                        s.append(f_key)
                        if ajout_possible(f_word, ''.join(s)):
                            placer(p+1, s, s_list)
                        s.pop()

    placer(0, sol, sol_list)

    return build_solutions(sol_list)


def A2(words_dict, word, N):
    """
    Recherche toutes les combinaisons de mot formant une anagramme d'un mot choisi ayant au plus N mots.

    :param words_dict: Dictionnaire complet
    :param word: Mot pour lequel on recherche des anagrammes
    :param N: Nombre maximum de mots par combinaison

    :type words_dict: DefaultDict
    :type word: String
    :type N: Integer

    :return: List
    """
    f_word = format_word(word)
    f_word_len = len(f_word)
    words_dict = reduce_dict(words_dict, f_word)
    sol = []
    sol_list = []

    def build_solutions(s_list):
        """
        Construit la liste de toutes les solutions possibles

        :param s_list: Liste des combinaisons de cle qui sont solutions

        :type s_list: List

        :return:
        """
        solutions = []

        for comb_keys in s_list:
            for comb_lists in [[words_dict[len(key)][key] for key in comb_keys]]:
                solutions += list(product(*comb_lists))

        return solutions

    def ajout_possible(word, str_s):
        """
        Verifie que les lettres de la combinaison sont contenues dans le mot d'origine.

        :param word: Mot d'origine
        :param str_s: Lettres de la combinaison

        :type word: String
        :type str_s: String

        :return: Boolean
        """
        for c in str_s:
            if c in word:
                word = word.replace(c, '', 1)
            else:
                return False
        return True

    def est_solution(str_s):
        """
        Verifie que la combinaison est solution

        :param str_s: Combinaison sous forme de chaine de caractere

        :type str_s: String

        :return: Boolean
        """
        return f_word == ''.join(sorted(str_s))

    def placer(p, N, s, s_list):
        """
        Essai de placer un nouveau mot dans la combinaison

        :param p: Nombre de mot dans la combinaison
        :param N: Nombre maximum par combinaison
        :param s: Combinaison actuelle
        :param s_list: Liste de toutes les solutions trouvees

        :type p: Integer
        :type N: Integer
        :type s: List
        :type s_list: List
        """
        is_sol = est_solution(''.join(s))

        if is_sol or p == N:
            if is_sol:
                s_sort = sorted(s)
                if s_sort not in s_list:
                    s_list.append(s_sort[:])
        else:
            r = f_word_len - len(''.join(s))
            if p == N - 1:
                if r in words_dict.keys():
                    for f_key in words_dict[r].keys():
                        s.append(f_key)
                        if ajout_possible(f_word, ''.join(s)):
                            placer(p + 1, N, s, s_list)
                        s.pop()
            else:
                for n in words_dict.keys():
                    if n <= r:
                        for f_key in words_dict[n].keys():
                            s.append(f_key)
                            if ajout_possible(f_word, ''.join(s)):
                                placer(p+1, N, s, s_list)
                            s.pop()

    placer(0, N, sol, sol_list)

    return build_solutions(sol_list)
