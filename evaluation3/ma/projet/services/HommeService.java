package ma.projet.services;

import ma.projet.beans.*;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override
    public boolean create(Homme h) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.save(h); t.commit(); return true; }
        catch (Exception e) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override
    public boolean update(Homme h) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.update(h); t.commit(); return true; }
        catch (Exception e) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override
    public boolean delete(Homme h) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try { s.delete(h); t.commit(); return true; }
        catch (Exception e) { t.rollback(); return false; }
        finally { s.close(); }
    }
    @Override
    public Homme findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Homme h = s.get(Homme.class, id);
        s.close(); return h;
    }
    @Override
    public List<Homme> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Homme> list = s.createQuery("FROM Homme", Homme.class).list();
        s.close(); return list;
    }
    public List<Femme> getEpousesEntreDates(int hommeId, Date debut, Date fin) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Femme> list = s.createQuery(
                        "SELECT m.femme FROM Mariage m " +
                                "WHERE m.homme.id = :id " +
                                "AND m.dateDebut >= :debut AND m.dateDebut <= :fin",
                        Femme.class)
                .setParameter("id", hommeId)
                .setParameter("debut", debut)
                .setParameter("fin", fin).list();
        s.close(); return list;
    }
    public long getNbrHommesMaries4Femmes(Date debut, Date fin) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Mariage> root = cq.from(Mariage.class);

        cq.select(cb.countDistinct(root.get("homme")))
                .where(
                        cb.greaterThanOrEqualTo(root.get("dateDebut"), debut),
                        cb.lessThanOrEqualTo(root.get("dateDebut"), fin)
                )
                .groupBy(root.get("homme"))
                .having(cb.equal(cb.count(root.get("femme")), 4L));

        List<Long> results = s.createQuery(cq).list();
        s.close();
        return results.size();
    }

    public List<Mariage> getMariagesDetails(int hommeId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Mariage> list = s.createQuery(
                        "FROM Mariage m WHERE m.homme.id = :id ORDER BY m.dateDebut",
                        Mariage.class)
                .setParameter("id", hommeId).list();
        s.close(); return list;
    }
}
