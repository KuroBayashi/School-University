from unittest import TestCase, main

from a import load_file, est_anagramme, anagrammes, best

class ATest(TestCase):

    def setUp(self):
        FILES = [('../lexiqueODS.txt', 'UTF-8'), ('../liste_de_mots_francais_frgut.txt', 'ISO-8859-1')]

        choix = 0
        self.m_words_dict = load_file(FILES[choix][0], FILES[choix][1])

    def test_est_anagramme_true(self):
        word_1 = "ésçolïeRe"
        word_2 = "Êcolières"

        self.assertTrue(est_anagramme(word_1, word_2))

    def test_est_anagramme_false(self):
        word_1 = "ésçolïeRe"
        word_2 = "Êcolièree"

        self.assertFalse(est_anagramme(word_1, word_2))

    def test_anagrammes(self):
        word = "ROSE"

        self.assertCountEqual(anagrammes(self.m_words_dict, word), ["EROS", "ORES", "OSER", "SORE", "ROSE"])

    def test_best_2(self):
        list_2 = best(self.m_words_dict, 2)

        self.assertEqual(len(list_2), 15)
        for el in list_2:
            self.assertEqual(len(el), 2)

    def test_best_3(self):
        list_3 = best(self.m_words_dict, 3)

        self.assertEqual(len(list_3), 1)
        for el in list_3:
            self.assertEqual(len(el), 4)

    def test_best_4(self):
        list_4 = best(self.m_words_dict, 4)

        self.assertEqual(len(list_4), 3)
        for el in list_4:
            self.assertEqual(len(el), 6)


if __name__ == '__main__':
    main()