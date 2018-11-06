#!/bin/bash

#USAGE: 
# cd PROP/build/production/PROP
# bash drivers/DriverInfoSessionsTest.sh | java -classpath . drivers.DriverInfoSessions


############## Teoria ##############
#-------- Teoria no creada --------# 

# "USE: consultar info Teoria no creada"
# "OUTPUT: ERROR perque no es pot consultar informació de una Teoria inexistent"
echo "1"

# "USE: modificar numero sessions Teoria no creada"
# "OUTPUT: ERROR perque no es pot modificar informació de una Teoria inexistent"
echo "3 2 T"

# "USE: modificar duracio sessions Teoria no creada"
# "OUTPUT: ERROR perque no es pot modificar informació de una Teoria inexistent"
echo "4 3 T"

# "USE: modificar tipus aula Teoria no creada (tAula incorrecte)"
# "OUTPUT: ERROR perque s'ha introduit un tipus d'aula no acceptat"
echo "5 sample T"

# "USE: modificar tipus aula Teoria no creada (tAula correcte)""
# "OUTPUT: ERROR perque no es pot modificar informació de una Teoria inexistent"
echo "5 normal T"

# "USE: crear sessio Teoria"
# "OUTPUT: - "
echo "6 1 2 normal"

#--------- Teoria creada ---------# 

# "USE: consultar info Teoria creada"
# "OUTPUT: Informacio de la Teoria"
echo "1"

# "USE: modificar numero sessions Teoria creada"
# "OUTPUT: - "
echo "3 2 T"

# "USE: modificar duracio sessions Teoria creada"
# "OUTPUT: - "
echo "4 3 T"

# "USE: modificar tipus aula Teoria creada (tAula correcte)"
# "OUTPUT: - "
echo "5 pcs T"

# "USE: crear sessio Teoria amb una existent"
# "OUTPUT: ERROR perque no es pot crear dos Teories diferents"
echo "6 1 2 pcs"

# "USE: comprobar que estan ben fetes les modificacions"
# "OUTPUT: Informacio de la Teoria Modificada"
echo "1"

############ Laboratori ############
#------ Laboratori no creat -------# 

# "USE: consultar info Laboratori no creat"
# "OUTPUT: ERROR perque no es pot consultar informació d'un Laboratori inexistent"
echo "2"

# "USE: modificar numero sessions Laboratori no creat"
# "OUTPUT: ERROR perque no es pot modificar informació d'un Laboratori inexistent"
echo "3 2 L"

# "USE: modificar duracio sessions Laboratori no creat"
# "OUTPUT: ERROR perque no es pot modificar informació d'un Laboratori inexistent"
echo "4 3 L"

# "USE: modificar tipus aula Laboratori no creat"
# "OUTPUT: ERROR perque no es pot modificar informació d'un Laboratori inexistent"
echo "5 normal L"

# "USE: crear sessio Laboratori"
# "OUTPUT: - "
echo "7 1 2 normal"

#------- Laboratori creat ---------# 

# "USE: consultar info Laboratori creat"
# "OUTPUT: Informacio del Laboratori"
echo "2"

# "USE: modificar numero sessions Laboratori creat"
# "OUTPUT: - "
echo "3 2 L"

# "USE: modificar duracio sessions Laboratori creat"
# "OUTPUT: - "
echo "4 3 L"

# "USE: modificar tipus aula Laboratori creat (tAula correcte)""
# "OUTPUT: - "
echo "5 pcs L"

# "USE: crear sessio Laboratori amb una existent"
# "OUTPUT: ERROR perque no es pot crear dos Laboratoris diferents"
echo "7 1 2 pcs"

# "USE: consultar que s'ha modificat correctament tota la info de laboratori"
# "OUTPUT: Informacio del Laboratori Modificat"
echo "2"

# "USE: Sortir del programa"
# "OUTPUT: - "
echo "8"