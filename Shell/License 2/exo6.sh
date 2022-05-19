#!/bin/bash

for ficrep in $(ls /proc)
do
	if [ $((ficrep)) != 0 ] && [ $((ficrep)) -gt $1 ] && [ $((ficrep)) -lt $2 ]
	then
		nom=$(cat /proc/$ficrep/status 2> /dev/null|head -1|cut -f2 )
		echo pid=$ficrep , nom=$nom
	fi
done
