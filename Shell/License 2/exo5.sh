#!/bin/bash

if [ $1 = "-c" ]
then
	compteur=1
	while [ $compteur -le $2 ]
	do
		nomfic=$(($RANDOM % 100))
		if [ ! -e "$nomfic.txt" ]
		then
			touch $nomfic.txt
			compteur=$(( $compteur + 1 ))
	
		fi
	done
fi

if [ $1 = "-s" ]
then
	rm *.txt
fi

