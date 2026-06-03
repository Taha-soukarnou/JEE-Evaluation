package ma.projet.services;

import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache t) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        try { s.save(t); tx.commit(); return true; }
        catch(Exception ex) { tx.rollback(); return false; }
        finally { s.close(); }
    }

    @Override
    public boolean update(Tache t) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        try { s.update(t); tx.commit(); return true; }
        catch(Exception ex) { tx.rollback(); return false; }
        finally { s.close(); }
    }

    @Override
    public boolean delete(Tache t) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        try { s.delete(t); tx.commit(); return true; }
        catch(Exception ex) { tx.rollback(); return false; }
        finally { s.close(); }
    }

    @Override
    public Tache findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Tache t = s.get(Tache.class, id);
        s.close(); return t;
    }

    @Override
    public List<Tache> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Tache> list = s.createQuery("FROM Tache", Tache.class).list();
        s.close(); return list;
    }

    public List<Tache> getTachesPrixSuperieur(double prix) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Tache> list = s.createNamedQuery("Tache.prixSuperieurA", Tache.class)
                .setParameter("prix", prix).list();
        s.close(); return list;
    }

    public List<Tache> getTachesEntreDates(Date debut, Date fin) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Tache> list = s.createQuery(
                        "SELECT et.tache FROM EmployeTache et " +
                                "WHERE et.dateDebutReelle >= :debut AND et.dateFinReelle <= :fin",
                        Tache.class)
                .setParameter("debut", debut)
                .setParameter("fin", fin).list();
        s.close(); return list;
    }
}