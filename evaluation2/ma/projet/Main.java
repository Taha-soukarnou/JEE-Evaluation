package ma.projet;

import ma.projet.classes.*;
import ma.projet.services.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String[] mois = {"Janvier","Février","Mars","Avril","Mai","Juin",
                "Juillet","Août","Septembre","Octobre","Novembre","Décembre"};

        EmployeService      empSrv  = new EmployeService();
        ProjetService       projSrv = new ProjetService();
        TacheService        tachSrv = new TacheService();
        EmployeTacheService etSrv   = new EmployeTacheService();


        Employe chef = new Employe("Alami", "Youssef", "0661234567");
        empSrv.create(chef);


        Projet p = new Projet("Gestion de stock",
                sdf.parse("14/01/2013"), sdf.parse("30/06/2013"));
        p.setChefDeProjet(chef);
        projSrv.create(p);


        Tache t1 = new Tache("Analyse", sdf.parse("01/02/2013"), sdf.parse("28/02/2013"), 1500.0);
        t1.setProjet(p); tachSrv.create(t1);

        Tache t2 = new Tache("Conception", sdf.parse("01/03/2013"), sdf.parse("31/03/2013"), 2000.0);
        t2.setProjet(p); tachSrv.create(t2);

        Tache t3 = new Tache("Développement", sdf.parse("01/04/2013"), sdf.parse("30/04/2013"), 800.0);
        t3.setProjet(p); tachSrv.create(t3);


        EmployeTache et1 = new EmployeTache(chef, t1,
                sdf.parse("10/02/2013"), sdf.parse("20/02/2013"));
        etSrv.create(et1);

        EmployeTache et2 = new EmployeTache(chef, t2,
                sdf.parse("10/03/2013"), sdf.parse("15/03/2013"));
        etSrv.create(et2);

        EmployeTache et3 = new EmployeTache(chef, t3,
                sdf.parse("10/04/2013"), sdf.parse("25/04/2013"));
        etSrv.create(et3);


        Calendar cal = Calendar.getInstance();
        cal.setTime(p.getDateDebut());
        String dateLisible = cal.get(Calendar.DAY_OF_MONTH) + " "
                + mois[cal.get(Calendar.MONTH)] + " "
                + cal.get(Calendar.YEAR);

        System.out.printf("%nProjet : %d      Nom : %s     Date début : %s%n",
                p.getId(), p.getNom(), dateLisible);
        System.out.println("Liste des tâches:");
        System.out.printf("%-4s %-20s %-20s %-20s%n",
                "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");

        List<EmployeTache> realises = projSrv.getTachesRealisees(p.getId());
        for (EmployeTache et : realises) {
            System.out.printf("%-4d %-20s %-20s %-20s%n",
                    et.getTache().getId(),
                    et.getTache().getNom(),
                    sdf.format(et.getDateDebutReelle()),
                    sdf.format(et.getDateFinReelle()));
        }


        System.out.println("\n=== Tâches dont le prix > 1000 DH ===");
        for (Tache t : tachSrv.getTachesPrixSuperieur(1000)) {
            System.out.println(t.getNom() + " - " + t.getPrix() + " DH");
        }


        System.out.println("\n=== Tâches réalisées entre 01/02/2013 et 30/04/2013 ===");
        List<Tache> tachesEntreDates = tachSrv.getTachesEntreDates(
                sdf.parse("01/02/2013"), sdf.parse("30/04/2013"));
        for (Tache t : tachesEntreDates) {
            System.out.println(t.getNom() + " - " + t.getPrix() + " DH");
        }


        System.out.println("\n=== Projets gérés par " + chef.getNom() + " ===");
        for (Projet proj : empSrv.getProjetsGeres(chef.getId())) {
            System.out.println("- " + proj.getNom());
        }


        System.out.println("\n=== Tâches réalisées par " + chef.getNom() + " ===");
        for (Tache t : empSrv.getTachesParEmploye(chef.getId())) {
            System.out.println("- " + t.getNom() + " (" + t.getPrix() + " DH)");
        }


        System.out.println("\n=== Tâches planifiées pour " + p.getNom() + " ===");
        for (Tache t : projSrv.getTachesPlanifiees(p.getId())) {
            System.out.printf("- %-20s du %s au %s%n",
                    t.getNom(),
                    sdf.format(t.getDateDebut()),
                    sdf.format(t.getDateFin()));
        }

        ma.projet.util.HibernateUtil.shutdown();
    }
}