#!/bin/bash

#USAGE: 
# cd PROP/build/production/PROP
# bash drivers/DriverAssignaturaTest.sh | java -classpath . drivers.DriverInfoSessions


# "USE: Crea una nova assignatura"
# "OUTPUT: -"
echo "1 PROP 5"

# "USE: Crea una segona assignatura del mateix quadri"
# "OUTPUT: -"
echo "1 AS 5"

# "USE: Crea una tercera assignatura de quadri diferent"
# "OUTPUT: -"
echo "1 IC 1"

# "USE: Imprimeix informacio de l'assignatura"
# "OUTPUT: Nom assignatura"
echo "3 PROP"

# "USE: Imprimeix informacio de l'assignatura que no existeix"
# "OUTPUT: Error, no existeix l'assignatura"
echo "3 SAMPLE"

# "USE: Modifica grups assignatura"
# "OUTPUT: -"
echo "2 PROP 3 60 3"

# "USE: Imprimeix informacio de l'assignatura"
# "OUTPUT: Nom assignatura + grups"
echo "3 PROP"

# "USE: Assigna informacio de Teoria"
# "OUTPUT: -"
echo "8 PROP 2 3 pcs"

# "USE: Imprimeix informacio de l'assignatura"
# "OUTPUT: Nom assignatura + grups + info Teoria"
echo "3 PROP"

# "USE: Assigna informacio de Laboratori"
# "OUTPUT: -"
echo "9 PROP 2 3 normal"

# "USE: Imprimeix informacio de l'assignatura"
# "OUTPUT: Nom assignatura + grups + info Teoria + info Lab"
echo "3 PROP"

# "USE: Afegeix correquisit correctament"
# "OUTPUT: -"
echo "5 AS PROP"

# "USE: Mira si es correquisit"
# "OUTPUT: cert"
echo "7 AS PROP"

# "USE: Consulta correquisits quan n'hi ha"
# "OUTPUT: Llista dels correquisits"
echo "4 PROP"

# "USE: Esborra correquisit"
# "OUTPUT: -"
echo "6 AS PROP"

# "USE: Consulta correquisits assignatura quan no n'hi ha"
# "OUTPUT: Warning, no hi ha correquisits"
echo "4 PROP"

# "USE: Afegeix correquisit diferent quadrimestre"
# "OUTPUT: Error, han de ser del mateix quadrimestre"
echo "5 PROP IC"

# "USE: Afegeix a ell mateix com a correquisit"
# "OUTPUT: Error, no pots afegirte a un mateix com a correquisit"
echo "5 PROP PROP"

# "USE: Surt del programa"
# "OUTPUT: -"
echo "10"