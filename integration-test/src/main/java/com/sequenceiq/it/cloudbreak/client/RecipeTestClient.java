package com.sequenceiq.it.cloudbreak.client;

import org.springframework.stereotype.Service;

import com.sequenceiq.it.cloudbreak.action.Action;
import com.sequenceiq.it.cloudbreak.action.v4.recipe.RecipeCreateAction;
import com.sequenceiq.it.cloudbreak.action.v4.recipe.RecipeDeleteAction;
import com.sequenceiq.it.cloudbreak.action.v4.recipe.RecipeGetAction;
import com.sequenceiq.it.cloudbreak.action.v4.recipe.RecipeListAction;
import com.sequenceiq.it.cloudbreak.dto.recipe.RecipeTestDto;

@Service
public class RecipeTestClient {

    public Action<RecipeTestDto> createV4() {
        return new RecipeCreateAction();
    }

    public Action<RecipeTestDto> getV4() {
        return new RecipeGetAction();
    }

    public Action<RecipeTestDto> deleteV4() {
        return new RecipeDeleteAction();
    }

    public Action<RecipeTestDto> listV4() {
        return new RecipeListAction();
    }

}
