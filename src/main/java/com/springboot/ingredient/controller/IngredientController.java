package com.springboot.ingredient.controller;

import com.springboot.dto.MultiResponseDto;
import com.springboot.dto.SingleResponseDto;
import com.springboot.ingredient.dto.IngredientDto;
import com.springboot.ingredient.entity.Ingredient;
import com.springboot.ingredient.mapper.IngredientMapper;
import com.springboot.recipestep.service.IngredientService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;
    private final IngredientMapper mapper;
    private static final String RECIPE_STEP_DEFAULT_URL = "recipe-steps/";

    public IngredientController(IngredientService ingredientService, IngredientMapper mapper) {
        this.ingredientService = ingredientService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postIngredient(@RequestBody IngredientDto.Post ingredientPostDto) {
        Ingredient ingredient = mapper.ingredientPostDtoToIngredient(ingredientPostDto);
        Ingredient savedIngredient = ingredientService.createIngredient(ingredient);
        URI location = URI.create(RECIPE_STEP_DEFAULT_URL + savedIngredient.getIngredientId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{ingredientId}")
    public ResponseEntity patchIngredient(@PathVariable long ingredientId, @RequestBody IngredientDto.Patch ingredientPatchDto) {
        ingredientPatchDto.setIngredientId(ingredientId);
        Ingredient ingredient = mapper.ingredientPatchDtoToIngredient(ingredientPatchDto);
        Ingredient updatedIngredient = ingredientService.updateIngredient(ingredient);
        IngredientDto.Response response = mapper.ingredientToIngredientResponseDto(updatedIngredient);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{ingredientId}")
    public ResponseEntity getIngredient(@PathVariable long ingredientId) {
        Ingredient ingredient = ingredientService.findIngredient(ingredientId);
        IngredientDto.Response response = mapper.ingredientToIngredientResponseDto(ingredient);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getIngredients(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String dtype
    ) {
        Page<Ingredient> pageIngredients = ingredientService.findIngredients(page - 1, size, dtype);
        List<Ingredient> ingredients = pageIngredients.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.ingredientsToIngredientResponseDtos(ingredients), pageIngredients),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{ingredientId}")
    public ResponseEntity deleteIngredient(@PathVariable long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return ResponseEntity.noContent().build();
    }

}
