package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.GameTaskContent;
import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple implementation of content type to class matcher with type class pairs registry
 */

public final class RegisterBasedContentTypeToClassMatcher implements ContentTypeToClassMatcher {

    Map<String, Class<? extends GameTaskContentElement>> typeToClassRegistry = new HashMap<>();
    Map<Class<? extends GameTaskContentElement>, String> classToTypeRegistry = new HashMap<>();

    @Override
    public Class<? extends GameTaskContentElement> getClassForContentType(String contentTypeName) {
        return null;
    }

    public void register(Class<? extends GameTaskContentElement> clazz, String typeName)
    {
            this.classToTypeRegistry.put(clazz, typeName);
            this.typeToClassRegistry.put(typeName, clazz);
    }

    public void unregister(Class<? extends GameTaskContentElement> clazz)
    {
        if(!this.classToTypeRegistry.containsKey(clazz))
            return;
        this.typeToClassRegistry.remove(this.classToTypeRegistry.get(clazz));
        this.classToTypeRegistry.remove(clazz);
    }

    public void unregister(String typeName)
    {
        if(!this.typeToClassRegistry.containsKey(typeName))
            return;
        this.classToTypeRegistry.remove(this.typeToClassRegistry.get(typeName));
        this.typeToClassRegistry.remove(typeName);
    }
}
