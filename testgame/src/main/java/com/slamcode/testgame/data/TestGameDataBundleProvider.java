package com.slamcode.testgame.data;

import android.content.Context;
import android.util.TypedValue;

import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.builder.GameTaskBuilder;
import com.slamcode.locationbasedgamelib.persistence.GameDataBundleProvider;
import com.slamcode.testgame.R;

import java.util.List;

public final class TestGameDataBundleProvider implements GameDataBundleProvider<TestGameDataBundle> {

    private Context context;

    public TestGameDataBundleProvider(Context context) {
        this.context = context;
    }

    @Override
    public TestGameDataBundle getDefaultBundleInstance() {
        TestGameDataBundle defaultBundle = new TestGameDataBundle();
        List<GameTaskData> gameTasks = defaultBundle.getGameTasks();

        gameTasks.add(createTaskCat());
        gameTasks.add(createTaskTheatre());
        gameTasks.add(createTaskKiller());
        gameTasks.add(createTaskCreature());
        gameTasks.add(createTaskPlace());

        gameTasks.add(createTaskFinal());

        return defaultBundle;
    }

    @Override
    public void updateBundle(TestGameDataBundle dataBundle) {
            dataBundle.setGameTasks(this.getDefaultBundleInstance().getGameTasks());
    }

    @Override
    public Class<TestGameDataBundle> getBundleClassType() {
        return TestGameDataBundle.class;
    }

    private GameTaskData createTaskCat() {
        return new GameTaskBuilder(1).withTitle(getString(R.string.game_task_cat_title))
                .withTextElement(getString(R.string.game_task_cat_content))
                .withTextInputComparisonElement(getString(R.string.game_task_input_confirm_button_text), getStringArray(R.array.game_task_cat_answers))
                .withTipsForPreviousTextInputElement(getStringArray(R.array.game_task_cat_tips_answers), getStringArray(R.array.game_task_cat_tips_messages))
                .withTipsForPreviousTextInputElement(getStringArray(R.array.game_task_cat_tips_general_messages))
                .getTask();
    }

    private GameTaskData createTaskTheatre()
    {
        return new GameTaskBuilder(2).withTitle(getString(R.string.game_task_theatre_title))
                .withTextElement(getString(R.string.game_task_theatre_content))
                .withLocationComparisonElement(getString(R.string.game_task_location_input_confirm_button_text),
                        getFloat(R.dimen.game_task_theatre_location_latitude),
                        getFloat(R.dimen.game_task_theatre_location_longitude),
                        getFloat(R.dimen.game_task_theatre_location_acceptance_distance),
                        null)
                .getTask();
    }

    private GameTaskData createTaskKiller() {
        return new GameTaskBuilder(3).withTitle(getString(R.string.game_task_killer_title))
                .withTextElement(getString(R.string.game_task_killer_content))
                .withTextInputComparisonElement(getString(R.string.game_task_input_confirm_button_text), getStringArray(R.array.game_task_killer_answers))
                .withTipsForPreviousTextInputElement(getStringArray(R.array.game_task_killer_tips_general_messages))
                .getTask();
    }

    private GameTaskData createTaskCreature() {
        return new GameTaskBuilder(4).withTitle(getString(R.string.game_task_creature_title))
                .withTextElement(getString(R.string.game_task_creature_content_before_audio))
                .withAudioPlayerElement(R.raw.creature_2, null, this.context)
                .withTextElement(getString(R.string.game_task_creature_content_after_audio))
                .withTextInputComparisonElement(getString(R.string.game_task_input_confirm_button_text), getStringArray(R.array.game_task_creature_answers))
                .withTipsForPreviousTextInputElement(getStringArray(R.array.game_task_creature_tips_answers), getStringArray(R.array.game_task_creature_tips_messages))
                .withTipsForPreviousTextInputElement(getStringArray(R.array.game_task_creature_tips_general_messages))
                .getTask();
    }

    private GameTaskData createTaskFinal() {
        return new GameTaskBuilder(99).withTitle(getString(R.string.game_task_final_title))
                .withTextElement(getString(R.string.game_task_final_message))
                .getTask();
    }

    private GameTaskData createTaskPlace()
    {
        return new GameTaskBuilder(2).withTitle(getString(R.string.game_task_place_title))
                .withTextElement(getString(R.string.game_task_place_content))
                .withLocationComparisonElement(getString(R.string.game_task_location_input_confirm_button_text),
                        getFloat(R.dimen.game_task_place_location_latitude),
                        getFloat(R.dimen.game_task_place_location_longitude),
                        getFloat(R.dimen.game_task_place_location_acceptance_distance),
                        null)
                .getTask();
    }

    private String getString(int resId)
    {
        return this.context.getString(resId);
    }

    private String[] getStringArray(int resId)
    {
        return this.context.getResources().getStringArray(resId);
    }

    private float getFloat(int resId) {

        TypedValue outValue = new TypedValue();
        this.context.getResources().getValue(resId, outValue, true);
        return outValue.getFloat();
    }
}
