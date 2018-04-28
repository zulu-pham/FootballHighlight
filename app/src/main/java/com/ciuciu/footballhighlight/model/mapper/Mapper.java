package com.ciuciu.footballhighlight.model.mapper;

import java.util.ArrayList;
import java.util.List;

public class Mapper<Entity, View> {
    public List<View> transform(List<Entity> entities, Transform<View, Entity> tranform) {
        if (entities == null) return new ArrayList<>();

        List<View> views = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            View view = tranform.apply(entity);
            if (view != null) {
                views.add(view);
            }
        }
        return views;
    }

    public View transform(Entity entity, Transform<View, Entity> tranform) {
        View view = tranform.apply(entity);
        return view;
    }
}
