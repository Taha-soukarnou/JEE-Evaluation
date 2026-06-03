package ma.projet.services;

import ma.projet.classes.*;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.save(p); t.commit(); return true; }
        catch(Exception ex) { t.rollback(); return false; }
        finally { s.close(); }
    }

    @Override
    public boolean update(Projet p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.update(p); t.commit(); return true; }
        catch(Exception ex) { t.rollback(); return false; }
        finally { s.close(); }
    }

    @Override
    public boolean delete(Projet p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.delete(p); t.commit(); return true; }
        catch(Exception ex) { t.rollback(); return false; }
        finally { s.close(); }
    }

    @Override
    public Projet findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Projet p = s.get(Projet.class, id);
        s.close(); return p;
    }

    @Override
    public List<Projet> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Projet> list = s.createQuery("FROM Projet", Projet.class).list();
        s.close(); return list;
    }

    public List<Tache> getTachesPlanifiees(int projetId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Tache> list = s.createQuery(
                        "FROM Tache t WHERE t.projet.id = :id", Tache.class)
                .setParameter("id", projetId).list();
        s.close(); return list;
    }

    public List<EmployeTache> getTachesRealisees(int projetId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<EmployeTache> list = s.createQuery(
                        "FROM EmployeTache et WHERE et.tache.projet.id = :id",
                        EmployeTache.class)
                .setParameter("id", projetId).list();
        s.close(); return list;
    }
}