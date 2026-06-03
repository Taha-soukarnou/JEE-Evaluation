package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NamedQuery(
        name = "Tache.prixSuperieurA",
        query = "SELECT t FROM Tache t WHERE t.prix > :prix"
)
@Entity
public class Tache {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private double prix;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @OneToMany(mappedBy = "tache")
    private List<EmployeTache> employeTaches;

    public Tache() {}
    public Tache(String nom, Date dateDebut, Date dateFin, double prix) {
        this.nom = nom; this.dateDebut = dateDebut;
        this.dateFin = dateFin; this.prix = prix;
    }
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date d) { this.dateDebut = d; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date d) { this.dateFin = d; }
    public double getPrix() { return prix; }
    public void setPrix(double p) { this.prix = p; }
    public Projet getProjet() { return projet; }
    public void setProjet(Projet p) { this.projet = p; }
    public List<EmployeTache> getEmployeTaches() { return employeTaches; }
}