package com.slamcode.locationbasedgamelayout.view;

import com.slamcode.locationbasedgamelayout.R;
import com.slamcode.locationbasedgamelib.model.content.DisplayPictureElement;
import com.slamcode.locationbasedgamelib.model.content.DisplayTextElement;
import com.slamcode.locationbasedgamelib.model.content.TextComparisonInputElement;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by smoriak on 24/07/2017.
 */
public class GameTaskContentSimpleLayoutProviderTest {

    @Test
    public void getGameTaskHeaderLayoutId_test()
    {
        GameTaskContentSimpleLayoutProvider provider = new GameTaskContentSimpleLayoutProvider();
        assertEquals(R.layout.header_contentelement, provider.getGameTaskHeaderLayoutId());
    }

    @Test
    public void getGameTaskListItemLayoutId_test()
    {
        GameTaskContentSimpleLayoutProvider provider = new GameTaskContentSimpleLayoutProvider();
        assertEquals(R.layout.gametask_listitem, provider.getGameTaskListItemLayoutId());
    }

    @Test
    public void getGameTaskContentElementLayoutId_test()
    {
        GameTaskContentSimpleLayoutProvider provider = new GameTaskContentSimpleLayoutProvider();

        assertEquals(R.layout.text_displayelement, provider.getGameTaskContentElementLayoutId(DisplayTextElement.CONTENT_TYPE_ID));

        assertEquals(R.layout.picture_displayelement, provider.getGameTaskContentElementLayoutId(DisplayPictureElement.CONTENT_TYPE_ID));

        assertEquals(R.layout.textcomparison_inputelement, provider.getGameTaskContentElementLayoutId(TextComparisonInputElement.CONTENT_TYPE_ID));
    }

}