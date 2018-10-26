**Coses a preguntar a profe:** 

- **Casos d'ús**

  No es sap si es correcte si no es llegeix la definició de cada cas d'ús, si gestionar assignatura es relaciona a crear, borrar, modificar, etc això ha d'estar descrit a la definició. 

  Es pregunta si implicar significa que es pot fer, és una relació de necessitat o de possiblitat. Necessitat: fletxes contínues, possibilitat discontínues. **ÉS IMPORTANT POSAR LA DIRECCIÓ DE LES FLETXES**

  **Sobre lo de sortir, si surt molt ho poses explícitament en una línia i ale. **

  **DIRIGIR FORA a gestionar assignatures.**

  **Que conformen un programa, ALERTA pq nosaltres estem parlant de pla d'estudi no de programa.**

  **Tot això és molt blabla, s'hauria de dir que l'usuari podrà fer, crea assignatura, modificar-les donar-les de baixa o consultar-les. També es pot dir una cosa més del cas d'us com per exmeple si presenta o no coses a l'usuari de manera que l'usuari pogui triar les coses. La manera de dir-ho que tenim no es gaire. Crear assignatura és un cas d'us. Osigui cada boleta està al mateix nivell.  Els sortir són diferents de manera que hem de fer servir noms diferents. Osea lo que he dicho antes fuera**.  

  **Tot i que accedeixes a crear, modificar blabla mitjançant gestionar assignatura no són extensions. Si crear assignatura fos una extensió, això implicaria que crear assignatura seria una manera de gestionar assignatures i no ho és.  MIRAR LO DE EXTENDS QUE EL PROFE LI MOLA. By TONI: posem sortir general que es refereixi al cas d'us en concret. **

  Podem dir que l'usuari a gestionar assignatura es visualitzaran totes les assignatures, ell podrà consultar-ne una i veure-la, modificar-la o esborrar-la. A més també podrà crear una assignatura. 

- **Què tal diagrama de classes?**

  Info_període_lectiu:  ell diu que no es complicaria

  Número de sessions: per lo de número de sessions per saber quantes hores per setmana, la sessió és entes com n hores. 

  Quadrimestre: si li voleu dir nivell l quadrimestre suda. Que sigui una classe depen de si hem de manipular quadrimestre o no, podria ser un atribut d'assignatura. És convenient tenir instàncies de quadrimestres? Ens hem de preguntar si fa falta o no, si és un singleton o té n instàncies (en aquest cas serien més d'una). Amb aquestes instàncies què és fa d'especial?  Si m'ho justifiqueu bé estarà bé. O classe o atribut? 

- **Què tal lo de les restriccions? como lo hacemos** 

  - **Un mateix quadrimestre no pot tenir assignaturas que es solapin d'un mateix nivell**
  - **No poden haver-hi dues classes a la mateixa hora a la mateixa aula**
  - **Correquisits (dos assignatures que s'han de fer a la vegada de manera que no es poden solapar)**
  - **No ens poden passar del període lectiu** 
  - **S'han de fer totes les hores de cada assignatura ***

  Es poden veure com si fossin paràmetres, sí. L'algorisme a l'hora de veure si .

  Les restriccions estan hardcoded i la solució Aina està bé. La forma és important, si el client vol una altra restricció i no la tenim programada, ha de ser fàcilment incorporable.  CLASSE RESTRICCIONS.

- **Preguntar primera línia que li molesta a en Toni, preguntar molt**

  Et genera un horari, una qüestió és que si soc l'admin m ho miro, macaba de trucar un profe i em diuen que prop no pot ser blabla... miro i tenir la opció de modificar l'horari (i.e. canviar assignatures)

  Si canvio restriccions (en plan checkbox o la restricció pot ser parametritzada, en plan plantilla on ell pot triar les restriccions creades) és generar un horari nou. Basically és activar novament l'opció de generar un horari. 

  L'admin ha demanat un horari sobre unes restriccions tenint en compte tot, si per a la petició no hi ha solució l'admin ha de generar un altre amb menys restriccions. 

- Algorismes

  > Mirar algorismes genètics, simulated nse que, algorisme de cerca local, backtracking cronològic (CSP). **NO FER SERVIR EL BACTRACKING CRONOLÒGIC PURO I DURO S'HAN DE FER HEURÍSTIQUES I TAMBÉ PODES PER A FER-HO MÉS EFICIENT**
  >
  > HEURISTIQUES QUE PODRÍEM MIRAR ES L'ORDENACIO DE VARIABLES I ELS VALORS 
  > AL FER UNA ASSIGNACIÓ PODRÍEM MIRAR QUIN SERIA EL DOMINI DEL FUTUR DE LES ALTRES (PODAR L'ESPAI DE PODA BACKTRACKING CRONOLOGIC AMB FORWARD CHECKING)
  >
  > N-CONSISTENT, ARCH CONSISTENCIA ENTRE DUES VARIABLES ELS DOMINIS SIGUIN CONSISTENS ENTRE ELLS, ENTRE TRES VARIABLES ELS VALORS DELS SEUS DOMINIS... QUAN MÉS GRAN ÉS LA N MÉS INEFICIENT ÉS EL PROBLEMA
  >
  > també existeix el backjumping que en comptes de tirar enrere salta a una variable d'abans 

