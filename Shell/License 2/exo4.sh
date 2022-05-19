#!/bin/bash
IFS=$'\n'
for ficrep in $(ls $1/*.txt)
do
	if [ -f "$1/$ficrep" ]
	then
		echo "voulez-vous visualiser $ficrep ? (o/n)"
		read rep
		if [ $rep = "o" ]
		then
			more "$1/$ficrep"
		fi
	fi
done
