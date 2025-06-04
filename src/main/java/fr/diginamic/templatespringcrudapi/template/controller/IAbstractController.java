package fr.diginamic.templatespringcrudapi.template.controller;

import fr.diginamic.templatespringcrudapi.template.entity.IIdentifiable;
import fr.diginamic.templatespringcrudapi.template.exception.FunctionnalException;
import fr.diginamic.templatespringcrudapi.template.service.ICrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Interface générique REST pour les opérations CRUD de base.
 * @param <T>   L'entité JPA.
 * @param <ID>  Le type de l'identifiant.
 * @param <DTO> Le type du DTO exposé.
 */
@RequestMapping("/default")
public interface IAbstractController<T extends IIdentifiable<ID>, ID, DTO>
{
    
    ICrudService<T, ID, DTO> getService();
    
    /**
     * Récupère toutes les entités.
     * @return Liste des entités converties en DTO.
     */
    @Operation(summary = "Lister toutes les entités", description = "Retourne l’ensemble des entités converties en DTO.")
    @ApiResponse(
          responseCode = "200",
          description = "Liste des entités",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
    )
    @GetMapping
    ResponseEntity<List<DTO>> findAll();
    
    /**
     * Récupère une entité par son identifiant.
     * @param id Identifiant de l'entité.
     * @return L'entité trouvée, convertie en DTO.
     * @throws FunctionnalException si l'entité n'est pas trouvée.
     */
    @Operation(summary = "Trouver une entité par son ID", description = "Retourne l’entité identifiée par son ID.")
    @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Entité trouvée", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", description = "Entité non trouvée", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<DTO> findById(@PathVariable ID id) throws FunctionnalException;
    
    /**
     * Crée une nouvelle entité.
     * @param entity Entité à créer.
     * @return Entité créée, convertie en DTO.
     * @throws FunctionnalException en cas d'erreur fonctionnelle.
     */
    @Operation(summary = "Créer une nouvelle entité", description = "Insère une nouvelle entité dans le système.")
    @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Entité créée", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    ResponseEntity<DTO> insert(@RequestBody T entity) throws FunctionnalException;
    
    /**
     * Met à jour une entité existante.
     * @param entity Entité mise à jour.
     * @return Entité mise à jour, convertie en DTO.
     * @throws FunctionnalException en cas d’erreur fonctionnelle.
     */
    @Operation(summary = "Mettre à jour une entité", description = "Met à jour une entité existante.")
    @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Entité mise à jour", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
          @ApiResponse(responseCode = "404", description = "Entité non trouvée", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @PutMapping
    ResponseEntity<DTO> update(@RequestBody T entity) throws FunctionnalException;
    
    /**
     * Supprime une entité par son identifiant.
     * @param id Identifiant de l'entité.
     * @return Réponse vide avec code 200 si succès.
     * @throws FunctionnalException si l'entité n'est pas trouvée.
     */
    @Operation(summary = "Supprimer une entité", description = "Supprime une entité identifiée par son ID.")
    @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Entité supprimée"),
          @ApiResponse(responseCode = "404", description = "Entité non trouvée", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable ID id) throws FunctionnalException;
}
