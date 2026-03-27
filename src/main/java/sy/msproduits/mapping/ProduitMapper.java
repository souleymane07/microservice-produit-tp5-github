package sy.msproduits.mapping;

import org.mapstruct.Mapper;
import sy.msproduits.dto.Produit;
import sy.msproduits.entities.ProduitEntity;

// L'ajout de componentModel = "spring" est CRUCIAL
@Mapper(componentModel = "spring")
public interface ProduitMapper {
    Produit toProduit(ProduitEntity produitEntity);
    ProduitEntity fromProduit(Produit produit);
}