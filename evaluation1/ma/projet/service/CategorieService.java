package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CategorieService implements IDao<Categorie> {

    @Override
    public boolean create(Categorie c) {
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try {
            s.persist(c);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback(); e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public boolean update(Categorie c) {
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try {
            s.merge(c); tx.commit(); return true;
        } catch (Exception e) {
            tx.rollback(); e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public boolean delete(Categorie c) {
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
    public Categorie findById(int id) {
        Session s = HibernateUtil.getSession();
        Categorie c = s.get(Categorie.class, id);
        s.close(); return c;
    }

    @Override
    public List<Categorie> findAll() {
        Session s = HibernateUtil.getSession();
        List<Categorie> list = s.createQuery("FROM Categorie", Categorie.class).list();
        s.close(); return list;
    }
}