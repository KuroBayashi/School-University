#!/bin/bash

if [ -f $1 ]
then
	echo -n $1 "est un fichier "
fi

if [ -d $1 ]
then
	echo -n $1 "est un répertoire "
fi

if [ -r $1 ]
then
	echo -n "lisible "
fi

if [ -w $1 ]
then
	echo -n "modifiable "
fi

if [ -x $1 ]
then
	echo -n "exécutable "
fi

echo "par " $USER
