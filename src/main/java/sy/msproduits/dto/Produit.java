package sy.msproduits.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {

    private String ref;

    @NotNull(message = "Le nom ne doit pas etre null")
    private String nom;

    @NotNull
    private double stock;
}
