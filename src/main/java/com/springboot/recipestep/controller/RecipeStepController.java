package com.springboot.recipestep.controller;

import com.springboot.dto.MultiResponseDto;
import com.springboot.dto.SingleResponseDto;
import com.springboot.recipestep.dto.RecipeStepDto;
import com.springboot.recipestep.entity.RecipeStep;
import com.springboot.recipestep.mapper.RecipeStepMapper;
import com.springboot.recipestep.service.RecipeStepService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recipe-steps")
public class RecipeStepController {
    private static final String RECIPE_STEP_DEFAULT_URL = "recipe-steps/";
    private RecipeStepService recipeStepService;
    private RecipeStepMapper mapper;

    public RecipeStepController(RecipeStepService recipeStepService, RecipeStepMapper mapper) {
        this.recipeStepService = recipeStepService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postRecipeStep(@RequestBody RecipeStepDto.post recipeStepDto) {
        RecipeStep recipeStep = mapper.recipeStepPostDtoToRecipeStep(recipeStepDto);
        RecipeStep createdRecipeStep = recipeStepService.createRecipeStep(recipeStep);
        URI location = URI.create(RECIPE_STEP_DEFAULT_URL + createdRecipeStep.getRecipeStepId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{recipeStepId}")
    public ResponseEntity getRecipeStep(@PathVariable long recipeStepId) {
        RecipeStep recipeStep = recipeStepService.findRecipeStep(recipeStepId);
        RecipeStepDto.Response response = mapper.recipeStepToRecipeStepResponseDto(recipeStep);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getRecipeSteps(@Positive @RequestParam int page,
                                           @Positive @RequestParam int size) {
        Page<RecipeStep> pageRecipeSteps = recipeStepService.findRecipeSteps(page - 1, size);
        List<RecipeStep> recipeSteps = pageRecipeSteps.getContent();

        return new ResponseEntity<>
                (new MultiResponseDto<>(mapper.recipeStepsToRecipeStepResponseDtos(recipeSteps),
                        pageRecipeSteps),
                        HttpStatus.OK);
    }

    @PatchMapping("/{recipeStepId}")
    public ResponseEntity patchRecipeStep(@PathVariable long recipeStepId, @RequestBody RecipeStepDto.patch recipeStepDto) {
        recipeStepDto.setRecipeStepId(recipeStepId);
        RecipeStep recipeStep = mapper.recipeStepPatchDtoToRecipeStep(recipeStepDto);
        RecipeStep updatedRecipeStep = recipeStepService.updateRecipeStep(recipeStep);
        RecipeStepDto.Response response = mapper.recipeStepToRecipeStepResponseDto(updatedRecipeStep);

        return new ResponseEntity(new SingleResponseDto(response), HttpStatus.OK);
    }

    @DeleteMapping("/{recipeStepId}")
    public ResponseEntity deleteRecipeStep(@PathVariable long recipeStepId) {
        recipeStepService.deleteRecipeStep(recipeStepId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
