#!/bin/bash
echo -n "les utilisateurs dont l'id est superieur à 500 sont : " 
for ligne in $(cut -d: -f1,3 /etc/passwd)
do
	id=${ligne##*:}
	user=${ligne%:*}
	if [ $((id)) -gt 500 ]
	then	
		echo -n $user " "
	fi
done
echo
