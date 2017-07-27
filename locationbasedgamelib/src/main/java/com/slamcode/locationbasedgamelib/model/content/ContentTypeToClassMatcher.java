package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.GameTaskContent;
import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;

/**
 * Provides matching of game task content type to its representative classes in java
 */

public interface ContentTypeToClassMatcher {

    Class<? extends GameTaskContentElement> getClassForContentType(String contentTypeName);
}
