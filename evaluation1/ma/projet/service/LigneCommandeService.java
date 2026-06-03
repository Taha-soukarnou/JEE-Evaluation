package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @Override
    public boolean create(LigneCommandeProduit l) {
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try {
            s.persist(l); tx.commit(); return true;
        } catch (Exception e) {
            tx.rollback(); e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public boolean update(LigneCommandeProduit l) {
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try {
            s.merge(l); tx.commit(); return true;
        } catch (Exception e) {
            tx.rollback(); e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public boolean delete(LigneCommandeProduit l) {
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try {
            s.remove(s.contains(l) ? l : s.merge(l));
            tx.commit(); return true;
        } catch (Exception e) {
            tx.rollback(); e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public LigneCommandeProduit findById(int id) {
        Session s = HibernateUtil.getSession();
        LigneCommandeProduit l = s.get(LigneCommandeProduit.class, id);
        s.close(); return l;
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        Session s = HibernateUtil.getSession();
        List<LigneCommandeProduit> list =
                s.createQuery("FROM LigneCommandeProduit", LigneCommandeProduit.class).list();
        s.close(); return list;
    }
}