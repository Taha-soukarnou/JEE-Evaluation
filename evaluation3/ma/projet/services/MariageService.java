package ma.projet.services;

import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class MariageService implements IDao<Mariage> {

    @Override
    public boolean create(Mariage m) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.save(m); t.commit(); return true; }
        catch (Exception e) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override
    public boolean update(Mariage m) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.update(m); t.commit(); return true; }
        catch (Exception e) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override
    public boolean delete(Mariage m) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.delete(m); t.commit(); return true; }
        catch (Exception e) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override
    public Mariage findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Mariage m = s.get(Mariage.class, id);
        s.close(); return m;
    }
    @Override
    public List<Mariage> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Mariage> list = s.createQuery("FROM Mariage", Mariage.class).list();
        s.close(); return list;
    }
}