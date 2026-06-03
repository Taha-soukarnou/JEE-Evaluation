package ma.projet.services;

import ma.projet.classes.*;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.save(e); t.commit(); return true; }
        catch(Exception ex) { t.rollback(); return false; }
        finally { s.close(); }
    }

    @Override
    public boolean update(Employe e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.update(e); t.commit(); return true; }
        catch(Exception ex) { t.rollback(); return false; }
        finally { s.close(); }
    }

    @Override
    public boolean delete(Employe e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.delete(e); t.commit(); return true; }
        catch(Exception ex) { t.rollback(); return false; }
        finally { s.close(); }
    }

    @Override
    public Employe findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Employe e = s.get(Employe.class, id);
        s.close(); return e;
    }

    @Override
    public List<Employe> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Employe> list = s.createQuery("FROM Employe", Employe.class).list();
        s.close(); return list;
    }

    public List<Tache> getTachesParEmploye(int employeId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Tache> list = s.createQuery(
                        "SELECT et.tache FROM EmployeTache et WHERE et.employe.id = :id",
                        Tache.class)
                .setParameter("id", employeId).list();
        s.close(); return list;
    }

    public List<Projet> getProjetsGeres(int employeId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Projet> list = s.createQuery(
                        "FROM Projet p WHERE p.chefDeProjet.id = :id",
                        Projet.class)
                .setParameter("id", employeId).list();
        s.close(); return list;
    }
}