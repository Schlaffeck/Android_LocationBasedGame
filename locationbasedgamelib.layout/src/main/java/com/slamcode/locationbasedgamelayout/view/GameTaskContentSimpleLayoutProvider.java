package com.slamcode.locationbasedgamelayout.view;

import com.slamcode.locationbasedgamelayout.R;
import com.slamcode.locationbasedgamelib.model.content.DisplayAudioPlayerElement;
import com.slamcode.locationbasedgamelib.model.content.DisplayPictureElement;
import com.slamcode.locationbasedgamelib.model.content.DisplayTextElement;
import com.slamcode.locationbasedgamelib.model.content.LocationComparisonInputElement;
import com.slamcode.locationbasedgamelib.model.content.TextComparisonInputElement;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;

/**
 * Class provides simple predefined layouts from this library resources
 */

public final class GameTaskContentSimpleLayoutProvider implements ContentLayoutProvider {

    @Override
    public int getGameTaskHeaderLayoutId() {
        return R.layout.header_contentelement;
    }

    @Override
    public int getGameTaskListItemLayoutId() {
        return R.layout.gametask_listitem;
    }

    @Override
    public int getGameTaskContentElementLayoutId(int contentTypeId) {

        if(contentTypeId == DisplayTextElement.CONTENT_TYPE_ID)
            return R.layout.text_displayelement;
        if(contentTypeId == DisplayPictureElement.CONTENT_TYPE_ID)
            return R.layout.picture_displayelement;
        if(contentTypeId == TextComparisonInputElement.CONTENT_TYPE_ID)
            return R.layout.textcomparison_inputelement;
        if(contentTypeId == LocationComparisonInputElement.CONTENT_TYPE_ID)
            return R.layout.locationcomparison_inputelement;
        if(contentTypeId == DisplayAudioPlayerElement.CONTENT_TYPE_ID)
            return R.layout.audioplayer_displayelement;

        return 0;
    }

    @Override
    public int getGameTaskDataLayoutId() {
        return R.layout.gametask_data;
    }
}
