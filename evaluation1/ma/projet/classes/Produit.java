package ma.projet.classes;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "produit")
@NamedQuery(
    name  = "Produit.prixSup100",
    query = "SELECT p FROM Produit p WHERE p.prix > 100"
)
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String reference;

    private float prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", fetch = FetchType.LAZY)
    private List<LigneCommandeProduit> lignes;

    public Produit() {}

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix      = prix;
        this.categorie = categorie;
    }

    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }

    public String getReference()             { return reference; }
    public void setReference(String r)       { this.reference = r; }

    public float getPrix()                   { return prix; }
    public void setPrix(float prix)          { this.prix = prix; }

    public Categorie getCategorie()                  { return categorie; }
    public void setCategorie(Categorie categorie)    { this.categorie = categorie; }

    public List<LigneCommandeProduit> getLignes()            { return lignes; }
    public void setLignes(List<LigneCommandeProduit> lignes) { this.lignes = lignes; }

    @Override
    public String toString() {
        return "Produit{id=" + id + ", reference='" + reference + "', prix=" + prix + "}";
    }
}
