package ma.projet.classes;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @OneToMany(mappedBy = "commande", fetch = FetchType.LAZY)
    private List<LigneCommandeProduit> lignes;


    public Commande() {}

    public Commande(Date date) {
        this.date = date;
    }

    public int getId()                   { return id; }
    public void setId(int id)            { this.id = id; }

    public Date getDate()                { return date; }
    public void setDate(Date date)       { this.date = date; }

    public List<LigneCommandeProduit> getLignes()            { return lignes; }
    public void setLignes(List<LigneCommandeProduit> lignes) { this.lignes = lignes; }

    @Override
    public String toString() {
        return "Commande{id=" + id + ", date=" + date + "}";
    }
}
