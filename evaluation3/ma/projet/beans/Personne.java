package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Personne {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String nom;
    protected String prenom;
    protected String telephone;
    protected String adresse;

    @Temporal(TemporalType.DATE)
    protected Date dateNaissance;

    public Personne() {}
    public Personne(String nom, String prenom, String telephone,
                    String adresse, Date dateNaissance) {
        this.nom = nom; this.prenom = prenom;
        this.telephone = telephone; this.adresse = adresse;
        this.dateNaissance = dateNaissance;
    }
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String t) { this.telephone = t; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String a) { this.adresse = a; }
    public Date getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(Date d) { this.dateNaissance = d; }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}