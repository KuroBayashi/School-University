# -*- coding: utf-8 -*-
import time
import string
import random

# #####################################
# Force brute
# #####################################
#  @return Boolean : Vrai si c'est un palindrome, sinon Faux
def is_palindrome(chaine):
    length = len(chaine) // 2

    for i in range(length):
        if chaine[i] != chaine[len(chaine)-1-i]:
            return False
    return True


def force_brute(chaine):
    position = 0
    longueur = 0
    length = len(chaine)

    for i in range(length):
        for j in range(length):
            if is_palindrome(chaine[i:j]) and (j - i) > longueur:
                position = i
                longueur = j - i
    
    return position, longueur


# #####################################
# Programmation dynamique
# #####################################
# @return Tuple(int, int) : Position du 1er caractère, et Longueur du palindrome
def prog_dynamique(chaine):
    longueur = 0
    position = 0
    length = len(chaine)
    matrice = [[1 if i == j else 0 for j in range(length)] for i in range(length)]
    
    for i in range(length - 1):
        matrice[i][i+1] = chaine[i] == chaine[i+1]
            
    for j in range(2, length):
        for i in range(j):
            matrice[i][j] = matrice[i+1][j-1] == 1 and chaine[i] == chaine[j]

            if matrice[i][j] == 1 and (j - i + 1) > longueur:
                longueur = j - i + 1
                position = i
    
    return position, longueur


# #####################################
# Force brute 2
# #####################################
# @return Tuple(int, int) : Position du 1er caractère, et Longueur du palindrome
def force_brute_2(chaine):
    longueur = 0
    position = 0
    length = len(chaine)

    for c in range(length):
        a, b = 0, 0
        
        while c+a >= 0 and c+b < length and chaine[c+a] == chaine[c+b]:
            if b - a + 1 > longueur:
                longueur = b - a + 1
                position = c + a
            a, b = (a-1, b+1)
            
        a, b = 0, 1
        
        while c+a >= 0 and c+b < length and chaine[c+a] == chaine[c+b]:
            if b - a + 1 > longueur:
                longueur = b - a + 1
                position = c + a
            a, b = (a-1, b+1)
    
    return position, longueur


# #####################################
# Manacher
# #####################################
# @return Tuple(int, int) : -1, et Longueur du palindrome
def manacher(s):
    c = 0
    r = 0
    t = "^#$" if s == "" else "^#" + "#".join(s) + "#$"
    p = [0] * len(t)

    for i in range(1, len(t) - 1):
        mirror = 2 * c - i
        p[i] = max(0, min(r - i, p[mirror]))

        while t[i + 1 + p[i]] == t[i - 1 - p[i]]:
            p[i] += 1
        if i + p[i] > r:
            c = i
            r = i + p[i]
    k, i = max((p[i], i) for i in range(1, len(t) - 1))

    return -1, ((i + k) - (i - k)) // 2

# #####################################
# Test random string
# #####################################
# @param Int n : Longueur de la chaine a générer
# @param Int itr : Nombre de test à effectuer
# @return Bool : Vrai si toutes les fonctions retourne le même résultat sur tous les tests, sinon faux
def test_random(n, itr):
    for i in range(itr):
        chaine = ''.join(random.choices(string.ascii_lowercase, k=n))

        a = force_brute(chaine)
        b = prog_dynamique(chaine)
        c = force_brute_2(chaine)

        if a != b or a != c:
            return False
    return True

# print( test_random(20, 2) )

# #####################################
# Test Files
# #####################################
# plp_(500|1000|2000|4000|8000).txt
FILE_PATH = "plp_500.txt"

def test(func):
    with open(FILE_PATH, 'r') as file:
        text = file.read().replace('\n', '')

    start = time.clock()
    result = func(text)

    print('Palindrome starts at {:d}, it is {:d} characters long and script ran in {:f} sec.'
          .format(result[0], result[1], time.clock() - start))


# print("Force brute : ")
# test(force_brute)
# print("Programmation dynamique : ")
# test(prog_dynamique)
# print("Force brute 2 : ")
# test(force_brute_2)
# print("Manacher : ")
# test(manacher)
