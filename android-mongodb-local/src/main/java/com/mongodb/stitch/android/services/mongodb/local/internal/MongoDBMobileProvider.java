package com.mongodb.stitch.android.services.mongodb.local.internal;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mongodb.embedded.client.MongoClients;
import com.mongodb.embedded.client.MongoEmbeddedSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoDBMobileProvider's purpose is to automatically initialize embedded MongoDB and to listen to
 * application events in order to relay them to any listeners registered with the provider.
 */
public final class MongoDBMobileProvider extends ContentProvider {
  private static final String TAG = MongoDBMobileProvider.class.getSimpleName();

  private static final class TrimMemoryCommandModes {
    private static final String TRIM_MODE_AGGRESSIVE = "aggressive";
    private static final String TRIM_MODE_MODERATE = "moderate";
    private static final String TRIM_MODE_CONSERVATIVE = "conservative";
  }

  /**
   * EventListener is a listener interface which can be registered with this provider to react to
   * battery and memory level events.
   */
  public interface EventListener {
    /**
     * Called by the provider when it receives a ACTION_BATTERY_LOW broadcast event from the
     * Android system.
     */
    void onLowBatteryLevel();

    /**
     * Called by the provider when it receives a ACTION_BATTERY_OKAY broadcast event from the
     * Android system.
     */
    void onOkayBatteryLevel();

    /**
     * Called by the provider when its onTrimMemory method is invoked by the Android system.
     * @param memoryTrimMode the MongoDB memory trim mode that should be passed as the argument
     *                       to an embedded MongoDB trimMemory command. The listener is not
     *                       invoked when the Android system sends the TRIM_MEMORY_UI_HIDDEN
     *                       level.
     */
    void onTrimMemory(final String memoryTrimMode);
  }

  private static List<EventListener> eventListeners = new ArrayList<>();

  /**
   * Registers a {@link MongoDBMobileProvider.EventListener} with the provider
   * @param listener the listener to register with the provider.
   */
  public static void addEventListener(EventListener listener) {
    eventListeners.add(listener);
  }

  @Override
  public boolean onCreate() {
    try {
      // Initialize Embedded MongoDB
      MongoClients.init(MongoEmbeddedSettings.builder().build());

      // Set up a broadcast receiver to listen for battery events.
      final BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
          final String action = intent.getAction();
          if (action == null) {
            return;
          }

          if (action.equals(Intent.ACTION_BATTERY_LOW)) {
            for (final EventListener listener : eventListeners) {
              listener.onLowBatteryLevel();
            }
          } else if (action.equals(Intent.ACTION_BATTERY_OKAY)) {
            for (final EventListener listener : eventListeners) {
              listener.onOkayBatteryLevel();
            }
          }
        }
      };

      final IntentFilter filter = new IntentFilter();
      filter.addAction(Intent.ACTION_BATTERY_LOW);
      filter.addAction(Intent.ACTION_BATTERY_OKAY);

      final Context context = getContext();
      if (context != null) {
        context.registerReceiver(br, filter);
      } else {
        Log.e(TAG, "Could not register broadcast receiver for battery events");
      }
    } catch (final Exception e) {
      Log.e(TAG, "Error automatically initializing embedded MongoDB", e);
    }

    return false;
  }

  @Override
  public void onTrimMemory(int level) {
    // We don't care about the UI being hidden as a memory event.
    if (level == TRIM_MEMORY_UI_HIDDEN) {
      return;
    }

    String mode;
    switch (level) {
      case TRIM_MEMORY_BACKGROUND:
      case TRIM_MEMORY_RUNNING_CRITICAL:
        mode = TrimMemoryCommandModes.TRIM_MODE_AGGRESSIVE;
        break;
      case TRIM_MEMORY_RUNNING_LOW:
      case TRIM_MEMORY_COMPLETE:
        mode = TrimMemoryCommandModes.TRIM_MODE_MODERATE;
        break;
      case TRIM_MEMORY_MODERATE:
      case TRIM_MEMORY_RUNNING_MODERATE:
        mode = TrimMemoryCommandModes.TRIM_MODE_CONSERVATIVE;
        break;
      default: return; // ignores UI_HIDDEN mode
    }

    for (final EventListener listener : eventListeners) {
      listener.onTrimMemory(mode);
    }

    super.onTrimMemory(level);
  }

  @NonNull
  @Override
  public Cursor query(
      @NonNull Uri uri,
      @Nullable String[] strings,
      @Nullable String s,
      @Nullable String[] strings1,
      @Nullable String s1) {
    throw new UnsupportedOperationException();
  }

  @NonNull
  @Override
  public String getType(@NonNull Uri uri) {
    throw new UnsupportedOperationException();
  }

  @NonNull
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int update(
      @NonNull Uri uri,
      @Nullable ContentValues contentValues,
      @Nullable String s,
      @Nullable String[] strings) {
    throw new UnsupportedOperationException();
  }
}
