#!/bin/bash
# IFS désigne le séparateur par défaut et permet de gérer les noms de fichiers avec espaces
IFS=$'\n'

echo "----------------"
echo "Les fichiers de $1 sont:"
for ficrep in $(ls $1)
do
	if [ -f "$1/$ficrep" ]
	then
		echo $ficrep
	fi
done

echo "----------------"
echo "Les répertoires de $1 sont:"
for ficrep in $(ls $1)
do
	if [ -d "$1/$ficrep" ]
	then
		echo $ficrep
	fi
done

