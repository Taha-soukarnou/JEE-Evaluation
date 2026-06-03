package ma.projet.services;

import ma.projet.beans.*;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    @Override
    public boolean create(Femme f) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.save(f); t.commit(); return true; }
        catch (Exception e) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override
    public boolean update(Femme f) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.update(f); t.commit(); return true; }
        catch (Exception e) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override
    public boolean delete(Femme f) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.delete(f); t.commit(); return true; }
        catch (Exception e) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override
    public Femme findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Femme f = s.get(Femme.class, id);
        s.close(); return f;
    }
    @Override
    public List<Femme> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Femme> list = s.createQuery("FROM Femme", Femme.class).list();
        s.close(); return list;
    }

    public Femme getFemmePlusAgee() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Femme f = s.createQuery(
                        "FROM Femme ORDER BY dateNaissance ASC", Femme.class)
                .setMaxResults(1).uniqueResult();
        s.close(); return f;
    }

    public int getNbrEnfantsEntreDates(int femmeId, Date debut, Date fin) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Object result = s.createNativeQuery(
                        "SELECT SUM(m.nbrEnfant) FROM Mariage m " +
                                "WHERE m.femme_id = :femmeId " +
                                "AND m.dateDebut >= :debut AND m.dateDebut <= :fin")
                .setParameter("femmeId", femmeId)
                .setParameter("debut", debut)
                .setParameter("fin", fin)
                .uniqueResult();
        s.close();
        return result != null ? ((Number) result).intValue() : 0;
    }

    // Requête nommée: femmes mariées 2 fois ou plus
    public List<Femme> getFemmesMarieesDeuxFois() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Femme> list = s.createNamedQuery(
                "Femme.marieeDeuxFoisOuPlus", Femme.class).list();
        s.close(); return list;
    }
}