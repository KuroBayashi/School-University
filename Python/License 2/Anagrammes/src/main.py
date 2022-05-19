from collections import defaultdict, Counter, OrderedDict
from unicodedata import normalize
from codecs import open
from itertools import product


def format_word(word):
    return ''.join(sorted(normalize('NFD', word).encode('ascii', 'ignore').lower().decode('ascii')))


def load_file(fname, encoding):
    global words_dict

    with open(fname, 'r', encoding=encoding) as f:
        for word in f:
            word = word.rstrip()
            words_dict[format_word(word)].append(word)

    words_dict = OrderedDict(sorted(words_dict.items(), key=lambda k: len(k[0])))


def is_anagrams(word_1, word_2):
    return format_word(word_1) == format_word(word_2)


def anagrammes(word):
    return words_dict[format_word(word)]


def best(longueur):
    best_words = []
    maxi = 0

    for key, words_list in words_dict.items():
        if len(key) < longueur or len(key) > longueur:
            continue

        if best_words:
            if len(words_list) > maxi:
                best_words.clear()
                best_words.append(words_list)
                maxi = len(words_list)
            elif len(words_list) == maxi:
                best_words.append(words_list)
        else:
            best_words.append(words_list)
            maxi = len(words_list)

    return best_words


def est_presque_anagramme(word1, word2, ecart):
    if len(word1) + ecart < len(word2):
        return False

    word1 = format_word(word1)
    word2 = format_word(word2)

    for letter in word1:
        if letter in word2:
            word2 = word2.replace(letter, "", 1)
        else:
            return False

    return True


def presque_anagrammes(word, ecart):
    presque_words = []

    for key, words_list in words_dict.items():
        if est_presque_anagramme(word, key, ecart):
            presque_words.append(words_list)

    return presque_words

if __name__ == "__main__":
    FILES = [('../lexiqueODS.txt', 'UTF-8'), ('../liste_de_mots_francais_frgut.txt', 'ISO-8859-1')]
    words_dict = defaultdict(list)

    # Files
    print("Chargement du dictionnaire.")
    load_file(FILES[0][0], FILES[0][1])
    print("Dictonnaire chargé.")

    # est_anagrammes
    # a = "éscolieRe"
    # b = "Ecolières"
    # print("'", a, "'", "est une anagramme de '", b, "' ?")
    # print(is_anagrams(a, b))

    # anagrammes
    # c = "Aïr"
    # print("Les anagrammes de ", c, "sont :")
    # print(anagrammes(c))

    # best
    # l = 4
    # print("Les mots de longueur ", l, "ayant le plus d'anagrammes sont :")
    # wl = best(l)
    # for x in wl:
    #     print(x)

    # est_presque_anagramme
    # a = "Air"
    # b = "Rilla"
    # c = 2
    # print("'", a, "'", "est n-presque-anagramme de '", b, "' ( diff =", c, ")?")
    # print(est_presque_anagramme(a, b, c))

    # presque_anagrammes
    # a = "PÄr"
    # b = 1
    # print("Les presques anagrammes de '", a, "' (diff = ", b, ") sont :")
    # p = presque_anagrammes(a, b)
    # for x in p:
    #     print(x)
