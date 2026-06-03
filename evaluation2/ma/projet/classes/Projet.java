package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Projet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Employe chefDeProjet;

    @OneToMany(mappedBy = "projet")
    private List<Tache> taches;

    public Projet() {}
    public Projet(String nom, Date dateDebut, Date dateFin) {
        this.nom = nom; this.dateDebut = dateDebut; this.dateFin = dateFin;
    }
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date d) { this.dateDebut = d; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date d) { this.dateFin = d; }
    public Employe getChefDeProjet() { return chefDeProjet; }
    public void setChefDeProjet(Employe e) { this.chefDeProjet = e; }
    public List<Tache> getTaches() { return taches; }
}