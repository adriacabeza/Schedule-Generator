package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioAulaDia extends Restriccions {

        private int id;
        private boolean active;
        private int hora;
        private Aula aula;

        public RestriccioAulaDia() {
            super(4,true);
        }

        public boolean isable(Assignacio[][][] horari, int hora, int dia, Assignatura assig,Aula aula3) throws NotFoundException {
            if(hora == hora && aula3 == aula) return false;
            return true;
        }

        @Override
        public boolean isable() {
            return false;
        }
}

