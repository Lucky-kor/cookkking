package com.springboot.ingredient.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("SEASONING")
public class SeasoningIngredient extends Ingredient {
}
