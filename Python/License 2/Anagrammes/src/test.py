from timeit import default_timer
from random import choice
from string import ascii_lowercase
from collections import Counter

import sys


def est_presque_anagramme(a, b, n):
    return sum([x for x in list(a & b)]) == n

def compare_str(word_1, word_2):
    if len(word_1) + 1 != len(word_2):
        return False

    for letter in word_1:
        if letter in word_2:
            word_2 = word_2.replace(letter, "", 1)
        else:
            return False

    return True

def compare_ctr(a, b):
    return not (a - b) and sum((b - a).values()) == 1

if __name__ == "__main__":
    word1 = "aaabbbcdd"
    word2 = "aaaabbbcdd"

    cter1 = Counter(word1)
    cter2 = Counter(word2)

    rng1 = [ ''.join(sorted(''.join(choice(ascii_lowercase) for _ in range(10)))) for _ in range (2000000) ]
    rng2 = [ Counter(w) for w in rng1 ]

    print("string start")
    sys.stdout.flush()
    start = default_timer()
    for w in rng1:
        x = compare_str(word1, w)
    print("string:", default_timer() - start)

    print("counter start")
    sys.stdout.flush()
    start = default_timer()
    for c in rng2:
        x = compare_ctr(cter1, c)
    print("counter:", default_timer() - start)