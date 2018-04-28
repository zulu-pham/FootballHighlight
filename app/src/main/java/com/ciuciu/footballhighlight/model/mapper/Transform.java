package com.ciuciu.footballhighlight.model.mapper;

public interface Transform<View, Entity> {
    View apply(Entity entity);
}

