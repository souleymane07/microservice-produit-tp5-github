package sy.msproduits.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sy.msproduits.entities.ProduitEntity;

public interface IProduitRepository extends JpaRepository<ProduitEntity, String> {
}
