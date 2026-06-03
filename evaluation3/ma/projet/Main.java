package ma.projet;

import ma.projet.beans.*;
import ma.projet.services.*;
import ma.projet.util.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        FemmeService   femmeSrv   = new FemmeService();
        HommeService   hommeSrv   = new HommeService();
        MariageService mariageSrv = new MariageService();


        Femme f1  = new Femme("RAMI",   "SALIMA",  "0611111111", "Rabat",  sdf.parse("03/05/1970"));
        Femme f2  = new Femme("ALI",    "AMAL",    "0622222222", "Casablanca", sdf.parse("12/08/1975"));
        Femme f3  = new Femme("ALAOUI", "WAFA",    "0633333333", "Fes",    sdf.parse("04/11/1978"));
        Femme f4  = new Femme("ALAMI",  "KARIMA",  "0644444444", "Sale",   sdf.parse("03/09/1968"));
        Femme f5  = new Femme("IDRISSI","NADIA",   "0655555555", "Rabat",  sdf.parse("20/01/1980"));
        Femme f6  = new Femme("TAHIRI", "LOUBNA",  "0666666666", "Meknes", sdf.parse("15/06/1972"));
        Femme f7  = new Femme("BENALI", "SIHAM",   "0677777777", "Kenitra",sdf.parse("09/03/1985"));
        Femme f8  = new Femme("FILALI", "HAJAR",   "0688888888", "Agadir", sdf.parse("22/11/1990"));
        Femme f9  = new Femme("CHRAIBI","SARA",    "0699999999", "Tanger", sdf.parse("17/07/1983"));
        Femme f10 = new Femme("MANSOURI","HIND",   "0610101010", "Oujda",  sdf.parse("05/02/1977"));

        femmeSrv.create(f1);  femmeSrv.create(f2);  femmeSrv.create(f3);
        femmeSrv.create(f4);  femmeSrv.create(f5);  femmeSrv.create(f6);
        femmeSrv.create(f7);  femmeSrv.create(f8);  femmeSrv.create(f9);
        femmeSrv.create(f10);


        Homme h1 = new Homme("SAID",  "SAFI",   "0621000001", "Rabat",      sdf.parse("01/01/1960"));
        Homme h2 = new Homme("BAHI",  "OMAR",   "0621000002", "Casablanca", sdf.parse("15/05/1958"));
        Homme h3 = new Homme("TAHIR", "YOUSSEF","0621000003", "Fes",        sdf.parse("20/08/1965"));
        Homme h4 = new Homme("RIFAI", "HASSAN", "0621000004", "Sale",       sdf.parse("10/03/1970"));
        Homme h5 = new Homme("AMRANI","KHALID", "0621000005", "Agadir",     sdf.parse("25/12/1963"));

        hommeSrv.create(h1); hommeSrv.create(h2); hommeSrv.create(h3);
        hommeSrv.create(h4); hommeSrv.create(h5);


        mariageSrv.create(new Mariage(h1, f4, sdf.parse("03/09/1989"),
                sdf.parse("03/09/1990"), 0));                          // échoué
        mariageSrv.create(new Mariage(h1, f1, sdf.parse("03/09/1990"),
                null, 4));                                              // en cours
        mariageSrv.create(new Mariage(h1, f2, sdf.parse("03/09/1995"),
                null, 2));                                              // en cours
        mariageSrv.create(new Mariage(h1, f3, sdf.parse("04/11/2000"),
                null, 3));                                              // en cours


        mariageSrv.create(new Mariage(h2, f5, sdf.parse("10/06/1990"), null, 3));
        mariageSrv.create(new Mariage(h2, f6, sdf.parse("15/08/1995"), null, 2));


        mariageSrv.create(new Mariage(h3, f7, sdf.parse("01/01/2000"), null, 1));
        mariageSrv.create(new Mariage(h3, f5, sdf.parse("20/03/2005"),
                sdf.parse("01/01/2010"), 0));


        mariageSrv.create(new Mariage(h4, f8, sdf.parse("05/05/2005"), null, 2));


        mariageSrv.create(new Mariage(h5, f9, sdf.parse("12/12/2003"), null, 4));


        System.out.println("========== Liste des femmes ==========");
        for (Femme f : femmeSrv.findAll()) {
            System.out.println("- " + f.getPrenom() + " " + f.getNom()
                    + " | Née le : " + sdf.format(f.getDateNaissance()));
        }


        Femme plusAgee = femmeSrv.getFemmePlusAgee();
        System.out.println(" ========== Femme la plus âgée ==========");
                System.out.println(plusAgee.getPrenom() + " " + plusAgee.getNom()
                        + " — née le " + sdf.format(plusAgee.getDateNaissance()));


        System.out.println(" ========== Épouses de SAFI SAID (1989-2001) ==========");
                List<Femme> epouses = hommeSrv.getEpousesEntreDates(
                        h1.getId(), sdf.parse("01/01/1989"), sdf.parse("31/12/2001"));
        for (Femme f : epouses) {
            System.out.println("- " + f.getPrenom() + " " + f.getNom());
        }

        System.out.println(" ========== Enfants de SALIMA RAMI (1990-2000) ==========");
        int nbEnf = femmeSrv.getNbrEnfantsEntreDates(
                f1.getId(), sdf.parse("01/01/1990"), sdf.parse("31/12/2000"));
        System.out.println("Nombre d'enfants : " + nbEnf);


        System.out.println(" ========== Femmes mariées 2 fois ou plus ==========");
        for (Femme f : femmeSrv.getFemmesMarieesDeuxFois()) {
            System.out.println("- " + f.getPrenom() + " " + f.getNom());
        }


        long nbHommes = hommeSrv.getNbrHommesMaries4Femmes(
                sdf.parse("01/01/1980"), sdf.parse("31/12/2010"));
        System.out.println(" ========== Hommes mariés à 4 femmes (1980-2010) ==========");
                System.out.println("Nombre : " + nbHommes);


        System.out.println(" ========== Mariages de SAFI SAID ==========");
                System.out.println("Nom : " + h1.getPrenom() + " " + h1.getNom());

        List<Mariage> mariages = hommeSrv.getMariagesDetails(h1.getId());
        System.out.println(" Mariages En Cours :");
        int i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() == null) {
                System.out.printf("%d. Femme : %-15s  Date Début : %s    Nbr Enfants : %d%n",
                        i++,
                        m.getFemme().getPrenom() + " " + m.getFemme().getNom(),
                        sdf.format(m.getDateDebut()),
                        m.getNbrEnfant());
            }
        }
        System.out.println(" Mariages échoués :");
        i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() != null) {
                System.out.printf("%d. Femme : %-15s  Date Début : %s%n",
                        i,
                        m.getFemme().getPrenom() + " " + m.getFemme().getNom(),
                        sdf.format(m.getDateDebut()));
                System.out.printf("   Date Fin : %s    Nbr Enfants : %d%n",
                        sdf.format(m.getDateFin()), m.getNbrEnfant());
                i++;
            }
        }

        HibernateUtil.shutdown();
    }
}