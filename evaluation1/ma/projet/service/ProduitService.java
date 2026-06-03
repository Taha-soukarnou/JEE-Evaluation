package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit p) {
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try {
            s.persist(p); tx.commit(); return true;
        } catch (Exception e) {
            tx.rollback(); e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public boolean update(Produit p) {
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try {
            s.merge(p); tx.commit(); return true;
        } catch (Exception e) {
            tx.rollback(); e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public boolean delete(Produit p) {
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try {
            s.remove(s.contains(p) ? p : s.merge(p));
            tx.commit(); return true;
        } catch (Exception e) {
            tx.rollback(); e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public Produit findById(int id) {
        Session s = HibernateUtil.getSession();
        Produit p = s.get(Produit.class, id);
        s.close(); return p;
    }

    @Override
    public List<Produit> findAll() {
        Session s = HibernateUtil.getSession();
        List<Produit> list = s.createQuery("FROM Produit", Produit.class).list();
        s.close(); return list;
    }

    public List<Produit> findByCategorie(int categorieId) {
        Session s = HibernateUtil.getSession();
        List<Produit> list = s.createQuery(
                        "FROM Produit p WHERE p.categorie.id = :cid", Produit.class)
                .setParameter("cid", categorieId).list();
        s.close(); return list;
    }

    public List<Produit> findBetweenDates(Date d1, Date d2) {
        Session s = HibernateUtil.getSession();
        List<Produit> list = s.createQuery(
                        "SELECT DISTINCT lcp.produit FROM LigneCommandeProduit lcp " +
                                "WHERE lcp.commande.date BETWEEN :d1 AND :d2", Produit.class)
                .setParameter("d1", d1).setParameter("d2", d2).list();
        s.close(); return list;
    }

    public List<Object[]> findByCommande(int commandeId) {
        Session s = HibernateUtil.getSession();
        List<Object[]> list = s.createQuery(
                        "SELECT lcp.produit.reference, lcp.produit.prix, lcp.quantite " +
                                "FROM LigneCommandeProduit lcp WHERE lcp.commande.id = :cid", Object[].class)
                .setParameter("cid", commandeId).list();
        s.close(); return list;
    }

    public List<Produit> findPrixSup100() {
        Session s = HibernateUtil.getSession();
        List<Produit> list = s.createNamedQuery(
                "Produit.prixSup100", Produit.class).list();
        s.close(); return list;
    }
}