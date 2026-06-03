package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EmployeTache {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;
    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne
    @JoinColumn(name = "tache_id")
    private Tache tache;

    public EmployeTache() {}
    public EmployeTache(Employe e, Tache t, Date debut, Date fin) {
        this.employe = e; this.tache = t;
        this.dateDebutReelle = debut; this.dateFinReelle = fin;
    }
    public int getId() { return id; }
    public Date getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(Date d) { this.dateDebutReelle = d; }
    public Date getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(Date d) { this.dateFinReelle = d; }
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe e) { this.employe = e; }
    public Tache getTache() { return tache; }
    public void setTache(Tache t) { this.tache = t; }
}