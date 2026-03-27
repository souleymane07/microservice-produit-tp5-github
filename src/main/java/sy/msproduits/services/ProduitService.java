package sy.msproduits.services;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sy.msproduits.dao.IProduitRepository;
import sy.msproduits.dto.Produit;
import sy.msproduits.entities.ProduitEntity;
import sy.msproduits.exception.EntityNotFoundException;
import sy.msproduits.mapping.ProduitMapper;
import org.springframework.http.HttpStatus;
import sy.msproduits.exception.RequestException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProduitService {

    private final IProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
    public List<Produit> getProduits() {
        return produitRepository.findAll().stream()
                .map(entity -> new Produit(entity.getRef(), entity.getNom(), entity.getStock()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Produit getProduit(String ref) {
        return produitRepository.findById(ref)
                .map(entity -> new Produit(entity.getRef(), entity.getNom(), entity.getStock()))
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("produit.notfound", new Object[]{ref}, LocaleContextHolder.getLocale())
                ));
    }

    @Transactional
    public Produit createProduit(Produit produit) {
        // Vérification si la référence existe déjà
        if (produitRepository.existsById(produit.getRef())) {
            throw new RequestException(
                    messageSource.getMessage("produit.exists", new Object[]{produit.getRef()}, LocaleContextHolder.getLocale()),
                    HttpStatus.CONFLICT
            );
        }

        // Conversion Manuelle DTO -> Entité
        ProduitEntity entityToSave = new ProduitEntity();
        entityToSave.setRef(produit.getRef());
        entityToSave.setNom(produit.getNom());
        entityToSave.setStock(produit.getStock());

        // Sauvegarde
        ProduitEntity savedEntity = produitRepository.save(entityToSave);

        // Retour Manuel Entité -> DTO
        return new Produit(savedEntity.getRef(), savedEntity.getNom(), savedEntity.getStock());
    }

    @Transactional
    public Produit updateProduit(String ref, Produit produit) {
        return produitRepository.findById(ref)
                .map(existingEntity -> {
                    // Mise à jour manuelle des champs
                    existingEntity.setNom(produit.getNom());
                    existingEntity.setStock(produit.getStock());

                    // Sauvegarde de l'entité mise à jour
                    ProduitEntity updatedEntity = produitRepository.save(existingEntity);

                    // Retour Manuel
                    return new Produit(updatedEntity.getRef(), updatedEntity.getNom(), updatedEntity.getStock());
                }).orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("produit.notfound", new Object[]{ref}, LocaleContextHolder.getLocale())
                ));
    }

    @Transactional
    public void deleteProduit(String ref) {
        if (!produitRepository.existsById(ref)) {
            throw new EntityNotFoundException(
                    messageSource.getMessage("produit.notfound", new Object[]{ref}, LocaleContextHolder.getLocale())
            );
        }
        try {
            produitRepository.deleteById(ref);
        } catch (Exception e) {
            throw new RequestException(
                    messageSource.getMessage("produit.errordeletion", new Object[]{ref}, LocaleContextHolder.getLocale()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}