package com.slamcode.locationbasedgamelib.persistence.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;
import com.slamcode.locationbasedgamelib.model.content.ContentTypeToClassMatcher;
import com.slamcode.locationbasedgamelib.model.content.RegisterBasedContentTypeToClassMatcher;

import java.lang.reflect.Type;

/**
 * Deserializer matching game content element types to its instance classes
 */

public final class GameTaskContentElementJsonDeserializer<Element extends GameTaskContentElement> implements JsonDeserializer<Element> {

    private final ContentTypeToClassMatcher contentTypeToClassMatcher;

    public GameTaskContentElementJsonDeserializer(ContentTypeToClassMatcher contentTypeToClassMatcher)
    {
        this.contentTypeToClassMatcher = contentTypeToClassMatcher;
    }

    public GameTaskContentElementJsonDeserializer()
    {
        this(new RegisterBasedContentTypeToClassMatcher());
    }

    @Override
    public Element deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext deserializationContext) throws JsonParseException {

        if(jsonElement.isJsonNull() || jsonElement.getAsJsonObject().entrySet().isEmpty())
            return null;

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive primitive = jsonObject.getAsJsonPrimitive(GameTaskContentElement.CONTENT_TYPE_FIELD_NAME);
        String typeName = primitive.getAsString();

        Class<Element> clazz = (Class<Element>) this.contentTypeToClassMatcher.getClassForContentType(typeName);

        return deserializationContext.deserialize(jsonElement, clazz);
    }
}
