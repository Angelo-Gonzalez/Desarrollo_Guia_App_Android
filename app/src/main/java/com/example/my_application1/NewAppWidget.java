package com.example.my_application1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        //Mostrar un Toasto cuando se actualicé el widget
        Toast.makeText(context, "Widget actualizado: " + appWidgetId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        // Mostrar Toasto cuando se actualicé el widget
        Toast.makeText(context, "widget actualizados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context) {
        // Mostrar Toasto cuando se cree el primer widget
        Toast.makeText(context, "Primer widget creado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        // Mostrar Toasto cuando se elimine el último widget
        Toast.makeText(context, "Último widget eliminado", Toast.LENGTH_SHORT).show();
    }
}