package sy.msproduits.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sy.msproduits.dto.Produit;
import sy.msproduits.services.ProduitService;

import java.util.List;

@RestController
@RequestMapping("/produits")
@AllArgsConstructor
public class ProduitController {
    private ProduitService produitService;

    @GetMapping
    public List<Produit> getProduits() {
        return produitService.getProduits();
    }

    @GetMapping("/{ref}")
    public Produit getProduit(@PathVariable("ref") String ref) {
        return produitService.getProduit(ref);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Produit createProduit(@Valid @RequestBody Produit produit) {
        return produitService.createProduit(produit);
    }

    @PutMapping("/{ref}")
    public Produit updateProduit(@PathVariable("ref") String ref, @Valid @RequestBody Produit produit) {
        return produitService.updateProduit(ref, produit);
    }

    @DeleteMapping("/{ref}")
    public void deleteProduit(@PathVariable("ref") String ref) {
        produitService.deleteProduit(ref);
    }
}