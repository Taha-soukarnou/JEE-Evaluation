package ma.projet;

import ma.projet.classes.*;
import ma.projet.service.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws Exception {

        CategorieService     cs  = new CategorieService();
        ProduitService       ps  = new ProduitService();
        CommandeService      cds = new CommandeService();
        LigneCommandeService ls  = new LigneCommandeService();

        Categorie cat1 = new Categorie("INFO", "Informatique");
        Categorie cat2 = new Categorie("PERIPHS", "Périphériques");
        cs.create(cat1);
        cs.create(cat2);

        Produit p1 = new Produit("ES12", 120f, cat1);
        Produit p2 = new Produit("ZR85", 100f, cat1);
        Produit p3 = new Produit("EE85", 200f, cat2);
        Produit p4 = new Produit("KB01",  80f, cat2);
        ps.create(p1); ps.create(p2);
        ps.create(p3); ps.create(p4);

        Commande cmd = new Commande(new Date());
        cds.create(cmd);

        ls.create(new LigneCommandeProduit(cmd, p1, 7));
        ls.create(new LigneCommandeProduit(cmd, p2, 14));
        ls.create(new LigneCommandeProduit(cmd, p3, 5));

        System.out.println("\n=== Produits de la catégorie Informatique ===");
        for (Produit p : ps.findByCategorie(cat1.getId()))
            System.out.println("  " + p.getReference() + " - " + (int) p.getPrix() + " DH");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdf.parse("2024-01-01");
        Date d2 = sdf.parse("2099-12-31");
        System.out.println("\n=== Produits commandés entre 2024 et 2099 ===");
        for (Produit p : ps.findBetweenDates(d1, d2))
            System.out.println("  " + p.getReference());

        String dateStr = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH)
                .format(cmd.getDate());

        System.out.println();
        System.out.println("Commande : " + cmd.getId() + "     Date : " + dateStr);
        System.out.println("Liste des produits :");
        System.out.printf("%-12s %-8s %s%n", "Référence", "Prix", "Quantité");
        System.out.println();
        for (Object[] row : ps.findByCommande(cmd.getId())) {
            int prix = (row[1] instanceof Float)
                    ? (int)(float)(Float) row[1]
                    : Integer.parseInt(row[1].toString());
            System.out.printf("%-12s %d DH  %s%n", row[0], prix, row[2]);
        }

        System.out.println("\n=== Produits dont le prix > 100 DH ===");
        for (Produit p : ps.findPrixSup100())
            System.out.println("  " + p.getReference() + " - " + (int) p.getPrix() + " DH");
    }
}
