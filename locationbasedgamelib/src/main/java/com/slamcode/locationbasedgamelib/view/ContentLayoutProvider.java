package com.slamcode.locationbasedgamelib.view;

import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;

/**
 * Interface for provider to abstract and ease the selection of a proper layout for game task content element.
 */

public interface ContentLayoutProvider {

    int getGameTaskHeaderLayoutId();

    int getGameTaskListItemLayoutId();

    int getGameTaskContentElementLayoutId(int contentTypeId);
}
