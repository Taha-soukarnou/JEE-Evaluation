package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "Femme.marieeDeuxFoisOuPlus",
                query = "SELECT f FROM Femme f WHERE " +
                        "(SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2"
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Femme.nbrEnfantsEntreDates",
                query = "SELECT SUM(m.nbrEnfant) FROM Mariage m " +
                        "WHERE m.femme_id = :femmeId " +
                        "AND m.dateDebut >= :debut AND m.dateDebut <= :fin"
        )
})
@Entity
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", fetch = FetchType.LAZY)
    private List<Mariage> mariages;

    public Femme() {}
    public Femme(String nom, String prenom, String telephone,
                 String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> m) { this.mariages = m; }
}