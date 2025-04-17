package com.springboot.menu.entity;

import com.springboot.audit.BaseEntity;
import com.springboot.category.entity.MenuCategory;
import com.springboot.recipeboard.entity.RecipeBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuId;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "menuCategory_id")
    private MenuCategory menuCategory;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.PERSIST)
    private List<RecipeBoard> recipeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.PERSIST)
    private List<MenuIngredient> menuIngredients = new ArrayList<>();

    public void setRecipeBoard(RecipeBoard recipeBoard) {
        recipeBoards.add(recipeBoard);
        if (recipeBoard.getMenu() != this) {
            recipeBoard.setMenu(this);
        }
    }

    public void setMenuIngredient(MenuIngredient menuIngredient) {
        menuIngredients.add(menuIngredient);
        if (menuIngredient.getMenu() != this) {
            menuIngredient.setMenu(this);
        }
    }

    public void setMenuCategory(MenuCategory menuCategory) {
        this.menuCategory = menuCategory;
        if (!menuCategory.getMenus().contains(this)) {
            menuCategory.setMenu(this);
        }
    }
}
