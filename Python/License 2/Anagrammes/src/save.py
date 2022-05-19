from collections import defaultdict, Counter
from unicodedata import normalize
from codecs import open
from itertools import product, groupby, permutations, combinations
from timeit import default_timer

FILES = [('../lexiqueODS.txt', 'UTF-8'), ('../liste_de_mots_francais_frgut.txt', 'ISO-8859-1')]

words_dict = defaultdict(list)


def format_word(word):
    return ''.join(sorted(normalize('NFD', word).encode('ascii', 'ignore').lower().decode('ascii')))


def load_file(fname, encoding):
    global words_dict

    with open(fname, 'r', encoding=encoding) as f:
        for word in f:
            word = word.rstrip()
            words_dict[format_word(word)].append(word)


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


# def est_presque_anagramme(word1, word2, ecart):
#     lc = Counter(format_word(word1))
#     lc.subtract(Counter(format_word(word2)))
#
#     return sum([abs(x) for _, x in lc.most_common()]) <= ecart
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

    return len(word2) <= ecart


def presque_anagrammes(word, ecart):
    presque_words = []

    for key, words_list in words_dict.items():
        if est_presque_anagramme(word, key, ecart):
            presque_words.append(words_list)

    return presque_words


def A1(word):
    counter_word = Counter(format_word(word))
    sol = []
    sol_list = []

    def build_solution(s_list):
        solutions = []
        w_list = []

        for comb in s_list:
            w_list.append( [words_dict[key] for key in comb] )

        print(w_list)
        for comb in w_list:
            solutions += list(product(*comb))

        return solutions

    def ajout_possible(s):
        c = Counter(''.join(s))
        c.subtract(counter_word)

        for _, n in c.items():
            if n > 0:
                return False

        return True

    def est_solution(s):
        c = Counter(''.join(s))
        c.subtract(counter_word)

        return not sum([abs(n) for _, n in c.items()])

    def placer(s, s_list):
        if est_solution(s):
            s_s = sorted(s)
            if s_s not in s_list:
                s_list.append(s_s[:])
        else:
            for key in words_dict.keys():
                s.append(key)
                if ajout_possible(s):
                    placer(s, s_list)
                s.pop()

    placer(sol, sol_list)

    return build_solution(sol_list)


if __name__ == "__main__":
    # Files
    load_file(FILES[0][0], FILES[0][1])

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

    # A1
    a = "Rose"
    print("Les anagrammes par combinaison de '", a, "' sont :")
    comb = A1(a)
    print(len(comb), "solutions trouvées.")
    for i in range(min(15, len(comb))):
        print(comb[i])