package com.example.android.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Mourad on 1/12/2018.
 */

public class WidgetUpdateService extends IntentService {

    public static final String ACTION_WIDGET_RECIPE_UPDATE = "com.example.android.bakingapp.action.update_widget";
    public static final String EXTRA_WIDGET_INGREDIENTS = "com.example.android.bakingapp.extra.widget_ingredients";

    public WidgetUpdateService() {
        super("com.example.android.bakingapp.WidgetUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_WIDGET_RECIPE_UPDATE.equals(action)) {
                String ingredientList = intent.getStringExtra(EXTRA_WIDGET_INGREDIENTS);
                handleActionUpdateWidgetRecipe(ingredientList);
            }
        }
    }

    public static void startWidgetUpdate(Context context, String ingredientList) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.putExtra(EXTRA_WIDGET_INGREDIENTS, ingredientList);
        intent.setAction(ACTION_WIDGET_RECIPE_UPDATE);
        context.startService(intent);
    }


    private void handleActionUpdateWidgetRecipe(String ingredientList) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
        BakingAppWidget.updateRecipeAppWidgets(this, appWidgetManager, ingredientList, appWidgetIds);
    }
}
