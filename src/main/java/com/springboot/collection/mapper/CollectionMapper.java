package com.springboot.collection.mapper;

import com.springboot.collection.dto.CollectionDto;
import com.springboot.collection.entity.Collection;
import com.springboot.recipeboard.dto.RecipeBoardDto;
import com.springboot.recipeboard.entity.RecipeBoard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
    Collection collectionPostDtoToCollection(CollectionDto.Post collectionPostDto);
    Collection collectionPatchDtoToCollection(CollectionDto.Patch collectionPatchDto);
    List<CollectionDto.Response> collectionsToCollectionResponseDtos(List<Collection> collections);
    CollectionDto.Response collectionToCollectionResponseDto(Collection collection);
}
