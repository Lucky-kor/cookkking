package com.springboot.menu.service;

import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.ingredient.entity.Ingredient;
import com.springboot.ingredient.entity.MainIngredient;
import com.springboot.menu.entity.Menu;
import com.springboot.menu.repository.MenuRepository;
import com.springboot.recipeboard.entity.RecipeBoard;
import com.springboot.recipeboard.repository.RecipeBoardRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private MenuRepository menuRepository;
    private RecipeBoardRepository recipeBoardRepository;

    public MenuService(MenuRepository menuRepository, RecipeBoardRepository recipeBoardRepository) {
        this.menuRepository = menuRepository;
        this.recipeBoardRepository = recipeBoardRepository;
    }


    public Menu createMenu(Menu menu) {
        // 메뉴 검증
        verifyMenuName(menu);

        return menuRepository.save(menu);
    }

    public Menu updateMenu(Menu menu) {
        // 메뉴 검증
        Menu findMenu = verifyMenuId(menu.getMenuId());
        Optional.ofNullable(menu.getMenuName()).ifPresent(name -> findMenu.setMenuName(name));

        return menuRepository.save(findMenu);
    }

    public Menu findMenu(long menuId) {
        // 메뉴 검증
        return verifyMenuId(menuId);
    }

    public Page<Menu> findMenus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("menuId").descending());
        return menuRepository.findAll(pageable);
    }

    public void deleteMenu(long menuId) {
        // 메뉴 검증
        Menu findMenu = verifyMenuId(menuId);
        menuRepository.delete(findMenu);
    }

    public List<RecipeBoard> recommendMenus(List<Ingredient> ingredients, int page, int size) {
        verifyIngredient(ingredients);

        List<RecipeBoard> recipeBoards = getRecipeBoardsWithIngredients(ingredients, PageRequest.of(page, size * 10));

//        List<RecipeBoard> allRecipeBoards = recipeBoards.getContent();
        // 1. 셔플
        Collections.shuffle(recipeBoards);

        // 2. 원하는 수 만큼 잘라내기
        int randomSize = 10;

        int contentSize = Math.min(randomSize, recipeBoards.size());
        List<RecipeBoard> randomList = recipeBoards.subList(0, contentSize);

        Collections.shuffle(randomList);

        return randomList;

        // 3. 다시 Page로 감싸기
//        return new PageImpl<>(recipeBoards, recipeBoards.getPageable(), recipeBoards.getTotalElements());
    }

    private void verifyIngredient(List<Ingredient> ingredients) {
        // 재료 검증 로직
        long mainIngredientCount = ingredients.stream()
                .filter(ingredient -> ingredient instanceof MainIngredient)
                .count();
        if (ingredients.size() < 2) {
            throw new BusinessLogicException(ExceptionCode.INSUFFICIENT_MAIN_INGREDIENTS);
        }
    }

    private List<RecipeBoard> getRecipeBoardsWithIngredients(List<Ingredient> ingredients, Pageable pageable) {
        // 재료에 맞는 레시피 조회 로직
        List<Long> ingredientIds = ingredients.stream()
                .map(Ingredient::getIngredientId)
                .collect(Collectors.toList());
        return recipeBoardRepository.findMatchedRecipeBoards(ingredientIds, ingredientIds.size());
    }

    private void verifyMenuName(Menu menu) {
        // 메뉴 검증 로직
        Optional<Menu> optionalMenu = menuRepository.findByMenuName(menu.getMenuName());
        if (optionalMenu.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_MENU);
        }
    }

    private Menu verifyMenuId(long menuId) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        if (optionalMenu.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.MENU_NOT_FOUND);
        }

        return optionalMenu.get();
    }
}