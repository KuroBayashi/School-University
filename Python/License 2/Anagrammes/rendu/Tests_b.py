from unittest import TestCase, main

from b import load_file, est_presque_anagramme, presque_anagrammes

class BTest(TestCase):

    def setUp(self):
        FILES = [('../lexiqueODS.txt', 'UTF-8'), ('../liste_de_mots_francais_frgut.txt', 'ISO-8859-1')]

        choix = 0
        self.m_words_dict = load_file(FILES[choix][0], FILES[choix][1])

    def test_est_presque_anagramme_true(self):
        word_1 = "Air"
        word_2 = "Rilla"
        gap = 2
        self.assertTrue(est_presque_anagramme(word_1, word_2, gap))

    def test_est_presque_anagramme_false(self):
        word_1 = "Air"
        word_2 = "Airia"
        gap = 5
        self.assertFalse(est_presque_anagramme(word_1, word_2, gap))

    def test_presqu_anagrammes(self):
        word = "PÄr"
        gap = 1

        self.assertCountEqual(
            presque_anagrammes(self.m_words_dict, word, gap),
            [
                "PARA", "RAPA", "PARC", "DRAP", "APRE",
                "EPAR", "PARE", "RAPE", "PAIR", "PARI",
                "PRIA", "RIPA", "PRAO", "PARS", "RAPS",
                "PART", "RAPT", "PARU", "RUPA"
            ]
        )

if __name__ == "__main__":
    main()
