package sy.msproduits.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitEntity {

    @Id
    @Column(name = "ref", length = 100)
    private String ref;

    @Column(nullable = false, length = 200)
    private String nom;

    @Column(nullable = false)
    private double stock;
}
