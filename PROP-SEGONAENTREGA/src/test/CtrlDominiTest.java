/**
 * @author Aina Garcia
 */

package test;

import controllers.CtrlDomini;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.Aula;
import model.Horari;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CtrlDominiTest {
    private static CtrlDomini c;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        c = CtrlDomini.getInstance();
    }

    @Before
    public void setUp() throws Exception {
        c.reload();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstance() {
        assertNotNull(CtrlDomini.getInstance());
    }

    @Test
    public void crearPlaEstudis() {
        boolean fail;
        try {
            c.crearPlaEstudis("PlaEstudisTest", 2018);
            fail = false;
        } catch (RestriccioIntegritatException e) {
            fail = true;
        }
        assertFalse(fail); //probem que es pugui crear be el primer cop
        try {
            c.crearPlaEstudis("PlaEstudisTest", 2018);
            fail = false;
        } catch (RestriccioIntegritatException e) {
            fail = true;
        }
        assertTrue(fail); //segon cop no t'ha de deixar
    }

    @Test
    public void esborrarPlaEstudis() {
        boolean failNotFound;
        try {
            c.esborrarPlaEstudis("PlaEstudisTest");
            failNotFound = true;
        } catch (NotFoundException e) {
            failNotFound = false;
        } catch (RestriccioIntegritatException e) {
            failNotFound = true;
        }
        assertFalse(failNotFound);
        boolean failNoObsolet;
        try {
            c.crearPlaEstudis("PlaEstudisTest", 2018);
            c.esborrarPlaEstudis("PlaEstudisTest");
            failNoObsolet = true;
        } catch (RestriccioIntegritatException e) {
            failNoObsolet = false;
        } catch (NotFoundException e) {
            failNoObsolet = true;
        }
        assertFalse(failNoObsolet);
        boolean failObsolet;
        try {
            c.consultarPlaEsudis("PlaEstudisTest").setObsolet(true);
            c.esborrarPlaEstudis("PlaEstudisTest");
            failObsolet = false;
        } catch (NotFoundException | RestriccioIntegritatException e) {
            failObsolet = true;
        }
        assertFalse(failObsolet);
    }

    @Test
    public void consultarPlaEsudis() {
        boolean failNotFound;
        try {
            c.consultarPlaEsudis("PlaEstudisTest");
            failNotFound = true;
        } catch (NotFoundException e) {
            failNotFound = false;
        }
        assertFalse(failNotFound);
        boolean failNotConsultat;
        try {
            c.crearPlaEstudis("PlaEstudisTest", 2018);
            c.consultarPlaEsudis("PlaEstudisTest");
            failNotConsultat = false;
        } catch (NotFoundException | RestriccioIntegritatException e) {
            failNotConsultat = true;
        }
        assertFalse(failNotConsultat);
    }

    @Test
    public void afegirAssignaturaPla() {
        try {
            c.crearPlaEstudis("PlaEstudisTest", 2018);
            c.afegirAssignaturaPla("PlaEstudisTest", "AssignaturaTest");
            fail();
        } catch (NotFoundException | RestriccioIntegritatException ignored) {
        }

        try {
            c.consultarPlaEsudis("PlaEstudisTest").setObsolet(true);
            c.esborrarPlaEstudis("PlaEstudisTest");
            c.crearAssignatura("AssignaturaTest", 1);
            c.afegirAssignaturaPla("PlaEstudisTest", "AssignaturaTest");
            fail();
        } catch (NotFoundException | RestriccioIntegritatException ignored) {
        }

        try {
            c.crearPlaEstudis("PlaEstudisTest", 2018);
            c.afegirAssignaturaPla("PlaEstudisTest", "AssignaturaTest");
        } catch (NotFoundException | RestriccioIntegritatException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void esborrarAssignaturaPla() {
        try {
            c.crearPlaEstudis("PlaEstudisTest", 2018);
            c.esborrarAssignaturaPla("PlaEstudisTest", "AssignaturaTest");
            fail();
        } catch (NotFoundException | RestriccioIntegritatException ignored) {
        }

        try {
            c.consultarPlaEsudis("PlaEstudisTest").setObsolet(true);
            c.esborrarPlaEstudis("PlaEstudisTest");
            c.crearAssignatura("AssignaturaTest", 1);
            c.esborrarAssignaturaPla("PlaEstudisTest", "AssignaturaTest");
            fail();
        } catch (NotFoundException | RestriccioIntegritatException ignored) {
        }

        try {
            c.crearPlaEstudis("PlaEstudisTest", 2018);
            c.esborrarAssignaturaPla("PlaEstudisTest", "AssignaturaTest");
        } catch (NotFoundException | RestriccioIntegritatException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void consultarAssignaturesPlaEstudis() {
        ArrayList<String> ass = new ArrayList<>();
        boolean failNoExisteixPla;
        try {
            ass = c.consultarAssignaturesPlaEstudis("PlaEstudisTest");
            failNoExisteixPla = true;
        } catch (NotFoundException e) {
            failNoExisteixPla = false;
        }
        assertFalse(failNoExisteixPla);

        boolean allOk;
        try {
            c.crearPlaEstudis("PlaEstudisTest", 2018);
            ass = c.consultarAssignaturesPlaEstudis("PlaEstudisTest");
            allOk = true;
        } catch (NotFoundException | RestriccioIntegritatException e) {
            allOk = false;
        }
        assertTrue(allOk);
    }

    @Test
    public void crearAssignatura() {
        boolean failCrear;
        try {
            c.crearAssignatura("AssignaturaTest", 1);
            failCrear = false;
        } catch (RestriccioIntegritatException e) {
            failCrear = true;
        }
        assertFalse(failCrear);
        try {
            c.crearAssignatura("AssignaturaTest", 1);
            failCrear = false;
        } catch (RestriccioIntegritatException e) {
            failCrear = true;
        }
        assertTrue(failCrear);
    }

    @Test
    public void consultarAssignatura() {
        try {
            c.consultarAssignatura("AssignaturaTest");
            fail();
        } catch (NotFoundException ignored) {
        }

        try {
            c.crearAssignatura("AssignaturaTest", 1);
            c.consultarAssignatura("AssignaturaTest");
        } catch (NotFoundException | RestriccioIntegritatException e) {
            fail();
        }
    }

    @Test
    public void esborrarAssignatura() {
        try {
            c.esborrarAssignatura("AssignaturaTest");
            fail();
        } catch (NotFoundException ignored) {
        }

        try {
            c.crearAssignatura("AssignaturaTest", 1);
            c.esborrarAssignatura("AssignaturaTest");
        } catch (NotFoundException | RestriccioIntegritatException e) {
            fail();
        }
    }

    @Test
    public void modificaInformacioTeoria() {
        try {
            c.modificaInformacioTeoria("AssignaturaTest", 2, 2, Aula.TipusAula.NORMAL);
            fail();
        } catch (NotFoundException ignored) {
        }

        try {
            c.crearAssignatura("AssignaturaTest", 1);
            c.modificaInformacioTeoria("AssignaturaTest", 2, 2, Aula.TipusAula.NORMAL);
        } catch (NotFoundException | RestriccioIntegritatException e) {
            fail();
        }
    }

    @Test
    public void modificaInformacioLaboratori() {
        try {
            c.modificaInformacioLaboratori("AssignaturaTest", 2, 2, Aula.TipusAula.NORMAL);
            fail();
        } catch (NotFoundException ignored) {
        }

        try {
            c.crearAssignatura("AssignaturaTest", 1);
            c.modificaInformacioLaboratori("AssignaturaTest", 2, 2, Aula.TipusAula.NORMAL);
        } catch (NotFoundException | RestriccioIntegritatException e) {
            fail();
        }
    }

    @Test
    public void modificarGrups() {
        try {
            c.modificarGrups("AssignaturaTest", 10, 2, 2);
            fail();
        } catch (NotFoundException ignored) {
        }

        try {
            c.crearAssignatura("AssignaturaTest", 1);
            c.modificarGrups("AssignaturaTest", 10, 2, 2);
        } catch (NotFoundException | RestriccioIntegritatException e) {
            fail();
        }
    }

    @Test
    public void afegeixCorrequisit() {
        try {
            c.afegeixCorrequisit("AssignaturaTestA", "AssignaturaTestB");
            fail();
        } catch (NotFoundException | RestriccioIntegritatException ignored) {
        }

        try {
            c.crearAssignatura("AssignaturaTestA", 1);
            c.crearAssignatura("AssignaturaTestB", 2);
            c.afegeixCorrequisit("AssignaturaTestA", "AssignaturaTestB");
            fail();
        } catch (NotFoundException e) {
            fail();
        } catch (RestriccioIntegritatException ignored) {
        }

        try {
            c.crearAssignatura("AssignaturaTestC", 1);
            c.afegeixCorrequisit("AssignaturaTestA", "AssignaturaTestC");
        } catch (NotFoundException | RestriccioIntegritatException e) {
            fail();
        }
    }

    @Test
    public void esborraCorrequisit() {
        try {
            c.esborraCorrequisit("AssignaturaTestA", "AssignaturaTestB");
            fail();
        } catch (NotFoundException ignored) {
        }

        try {
            c.crearAssignatura("AssignaturaTestA", 1);
            c.crearAssignatura("AssignaturaTestB", 1);
            c.afegeixCorrequisit("AssignaturaTestA", "AssignaturaTestB");
            c.esborraCorrequisit("AssignaturaTestA", "AssignaturaTestB");
        } catch (NotFoundException | RestriccioIntegritatException e) {
            fail();
        }
    }

    @Test
    public void creaAula() {
        try {
            c.creaAula("a5", 1, 2, 60, Aula.TipusAula.LABORATORI);
        } catch (RestriccioIntegritatException e) {
            fail();
        }
        try {
            c.creaAula("a5", 1, 2, 60, Aula.TipusAula.LABORATORI);
            fail();
        } catch (RestriccioIntegritatException ignored) {
        }
    }

    @Test
    public void esborrarAula() {
        try {
            c.esborrarAula(Aula.crearkey("a5", 1, 2));
            fail();
        } catch (NotFoundException ignored) {
        }

        try {
            c.creaAula("a5", 1, 2, 60, Aula.TipusAula.LABORATORI);
            c.esborrarAula(Aula.crearkey("a5", 1, 2));
        } catch (NotFoundException | RestriccioIntegritatException e) {
            fail();
        }
    }

    @Test
    public void modificarAula() {
        try {
            c.modificarAula(Aula.crearkey("a5", 1, 2), 60, Aula.TipusAula.LABORATORI);
            fail();
        } catch (NotFoundException ignored) {
        }

        try {
            c.creaAula("a5", 1, 2, 60, Aula.TipusAula.LABORATORI);
            c.modificarAula(Aula.crearkey("a5", 1, 2), 60, Aula.TipusAula.LABORATORI);
        } catch (NotFoundException | RestriccioIntegritatException e) {
            fail();
        }
    }

    @Test
    public void consultarAula() {
        try {
            c.consultarAula(Aula.crearkey("a5", 1, 2));
            fail();
        } catch (NotFoundException ignored) {
        }

        try {
            c.creaAula("a5", 1, 2, 60, Aula.TipusAula.LABORATORI);
            c.consultarAula(Aula.crearkey("a5", 1, 2));
        } catch (NotFoundException | RestriccioIntegritatException e) {
            fail();
        }
    }

    @Test
    public void crearHorari() {
        try {
            c.crearAssignatura("AC", 1);
            c.crearPlaEstudis("NouPla", 2010);
            c.afegirAssignaturaPla("NouPla", "AC");
            c.modificarGrups("AC", 2, 50, 2);
            c.modificaInformacioTeoria("AC", 2, 2, Aula.TipusAula.NORMAL);
            c.modificaInformacioLaboratori("AC", 2, 2, Aula.TipusAula.NORMAL);
            c.creaAula("A5", 1, 2, 60, Aula.TipusAula.NORMAL);
            Horari h = c.crearHorari();
            assertNotNull(h);
        } catch (NotFoundException | RestriccioIntegritatException ignored) {
            fail();
        }
    }

    @Test
    public void crearHorari2() {
        try {
            c.crearAssignatura("AC", 1);
            c.crearPlaEstudis("NouPla", 2010);
            c.afegirAssignaturaPla("NouPla", "AC");
            c.modificarGrups("AC", 2, 50, 2);
            c.modificaInformacioTeoria("AC", 2, 2, Aula.TipusAula.NORMAL);
            c.modificaInformacioLaboratori("AC", 2, 2, Aula.TipusAula.NORMAL);
            c.creaAula("A5", 1, 2, 60, Aula.TipusAula.NORMAL);
            Horari h = c.crearHorari2();
            assertNotNull(h);
        } catch (NotFoundException | RestriccioIntegritatException ignored) {
            fail();
        }
    }
}