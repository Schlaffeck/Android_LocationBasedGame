package com.slamcode.locationbasedgamelib.model.utils;

import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;
import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.InputContentElement;
import com.slamcode.locationbasedgamelib.model.content.LocationComparisonInputElement;

import java.util.List;

/**
 * Utility methods for working with game task data classes
 */

public class GameTaskDataUtils {

    public static boolean hasInputOfType(GameTaskData gameTaskData, Class<? extends InputContentElement> inputType)
    {
        boolean found =  false;

        List<GameTaskContentElement> elements = gameTaskData.getGameTaskContent().getContentElements();
        for(int i = 0; i < elements.size() && !found; i++)
            found = inputType.isInstance(elements.get(i));

        if(!found && gameTaskData.getHelpTaskContent() != null)
        {
            elements = gameTaskData.getHelpTaskContent().getContentElements();
            for(int i = 0; i < elements.size() && !found; i++)
                found = inputType.isInstance(elements.get(i));
        }

        return found;
    }

    public static boolean hasLocationComparisonInput(GameTaskData gameTaskData)
    {
        return hasInputOfType(gameTaskData, LocationComparisonInputElement.class);
    }
}
