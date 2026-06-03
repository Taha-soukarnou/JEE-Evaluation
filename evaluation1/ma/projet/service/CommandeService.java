package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CommandeService implements IDao<Commande> {

    @Override
    public boolean create(Commande c) {
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try {
            s.persist(c); tx.commit(); return true;
        } catch (Exception e) {
            tx.rollback(); e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public boolean update(Commande c) {
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try {
            s.merge(c); tx.commit(); return true;
        } catch (Exception e) {
            tx.rollback(); e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public boolean delete(Commande c) {
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try {
            s.remove(s.contains(c) ? c : s.merge(c));
            tx.commit(); return true;
        } catch (Exception e) {
            tx.rollback(); e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public Commande findById(int id) {
        Session s = HibernateUtil.getSession();
        Commande c = s.get(Commande.class, id);
        s.close(); return c;
    }

    @Override
    public List<Commande> findAll() {
        Session s = HibernateUtil.getSession();
        List<Commande> list = s.createQuery("FROM Commande", Commande.class).list();
        s.close(); return list;
    }
}