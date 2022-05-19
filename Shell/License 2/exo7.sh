#!/bin/bash

for ficrep in $(ls /proc)
do
	if [ $((ficrep)) != 0 ] 
	then
		nom=$(cat /proc/$ficrep/status 2> /dev/null|head -1|cut -f2 )
		if [ a$nom = "axclock" ]
		then
			kill -9 $ficrep
		fi
	fi
done
