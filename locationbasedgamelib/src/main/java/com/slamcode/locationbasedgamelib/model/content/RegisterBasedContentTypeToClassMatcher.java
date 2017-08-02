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

    public RegisterBasedContentTypeToClassMatcher()
    {
        this.register(DisplayPictureElement.class, DisplayPictureElement.CONTENT_TYPE);
        this.register(DisplayTextElement.class, DisplayTextElement.CONTENT_TYPE);
        this.register(TextComparisonInputElement.class, TextComparisonInputElement.CONTENT_TYPE);
        this.register(LocationComparisonInputElement.class, LocationComparisonInputElement.CONTENT_TYPE);
    }

    @Override
    public Class<? extends GameTaskContentElement> getClassForContentType(String contentTypeName) {
        Class<? extends GameTaskContentElement> clazz = null;

        if(typeToClassRegistry.containsKey(contentTypeName))
            clazz = typeToClassRegistry.get(contentTypeName);

        return clazz;
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
