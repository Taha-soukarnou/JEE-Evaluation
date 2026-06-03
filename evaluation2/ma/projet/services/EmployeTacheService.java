package ma.projet.services;

import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache> {

    @Override
    public boolean create(EmployeTache et) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.save(et); t.commit(); return true; }
        catch(Exception ex) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override public boolean update(EmployeTache et) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.update(et); t.commit(); return true; }
        catch(Exception ex) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override public boolean delete(EmployeTache et) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.delete(et); t.commit(); return true; }
        catch(Exception ex) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override public EmployeTache findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        EmployeTache et = s.get(EmployeTache.class, id);
        s.close(); return et;
    }
    @Override public List<EmployeTache> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<EmployeTache> list = s.createQuery("FROM EmployeTache", EmployeTache.class).list();
        s.close(); return list;
    }
}