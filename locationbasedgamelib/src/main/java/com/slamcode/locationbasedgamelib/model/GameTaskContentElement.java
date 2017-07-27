package com.slamcode.locationbasedgamelib.model;

/**
 * Interface for all general game task content elements to be put into one sequence
 */

public interface GameTaskContentElement {

    String CONTENT_TYPE_FIELD_NAME = "contentType";

    /**
     * Returns string value of type of content to be displayed
     * @return content type
     */
    String getContentType();

    /**
     * Returns integer value of type of content identifier
     * @return Numerical identifier of type of content
     */
    int getContentTypeId();
}
