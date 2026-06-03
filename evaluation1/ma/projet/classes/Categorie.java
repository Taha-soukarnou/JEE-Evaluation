package ma.projet.classes;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String code;

    private String libelle;

    @OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY)
    private List<Produit> produits;

    public Categorie() {}

    public Categorie(String code, String libelle) {
        this.code    = code;
        this.libelle = libelle;
    }

    public int getId()                   { return id; }
    public void setId(int id)            { this.id = id; }

    public String getCode()              { return code; }
    public void setCode(String code)     { this.code = code; }

    public String getLibelle()           { return libelle; }
    public void setLibelle(String l)     { this.libelle = l; }

    public List<Produit> getProduits()           { return produits; }
    public void setProduits(List<Produit> list)  { this.produits = list; }

    @Override
    public String toString() {
        return "Categorie{id=" + id + ", code='" + code + "', libelle='" + libelle + "'}";
    }
}
