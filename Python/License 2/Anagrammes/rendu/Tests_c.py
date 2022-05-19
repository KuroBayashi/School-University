from unittest import TestCase, main

from c import load_file, A1, A2

class CTest(TestCase):

    def setUp(self):
        FILES = [('../lexiqueODS.txt', 'UTF-8'), ('../liste_de_mots_francais_frgut.txt', 'ISO-8859-1')]

        choix = 0
        self.m_words_dict = load_file(FILES[choix][0], FILES[choix][1])

    def test_a1_rose(self):
        self.assertCountEqual(
            A1(self.m_words_dict, "RosE"),
            [
                ("EROS",), ("ORES",), ("OSER",), ("SORE",), ("ROSE",),
                ("ES", "OR"), ("SE", "OR"), ("RE", "OS")
            ]
        )

    def test_a1_prose(self):
        self.maxDiff = None
        self.assertCountEqual(
            A1(self.m_words_dict, "Prösé"),
            [
                ("PERSO",), ("PORES",), ("POSER",), ("PROSE",), ("PSORE",), ("REPOS",), ("SPORE",),
                ("ES", "PRO"), ("SEP", "OR"), ("PRE", "OS"), ("SE", "PRO")
            ]
        )

    def test_a1_champollion(self):
        self.assertEqual(
            len(A1(self.m_words_dict, "chàmpôllïon")),
            599
        )


    def test_a2_champollion_1(self):
        self.assertEqual(
            len(A2(self.m_words_dict, "chàmpôllïon", 1)),
            0
        )

    def test_a2_champollion_2(self):
        self.assertEqual(
            len(A2(self.m_words_dict, "chàmpôllïon", 2)),
            11
        )

    def test_a2_champollion_3(self):
        self.assertEqual(
            len(A2(self.m_words_dict, "chàmpôllïon", 3)),
            261
        )

    def test_a2_champollion_4(self):
        self.assertEqual(
            len(A2(self.m_words_dict, "chàmpôllïon", 4)),
            599
        )

    def test_a2_carolineetflorian_2(self):
        self.assertEqual(
            len(A2(self.m_words_dict, "çÄrôlinèétflorïan", 2)),
            445
        )

if __name__ == "__main__":
    main()
