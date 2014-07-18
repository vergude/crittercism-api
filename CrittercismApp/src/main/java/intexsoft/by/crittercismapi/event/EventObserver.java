package intexsoft.by.crittercismapi.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Created by anastasya.konovalova on 20.06.14.
 */
public class EventObserver
{
	private static final String TAG = EventObserver.class.getSimpleName();
	private static final String EXTRA_EVENT = TAG + ":EVENT";
	private static final String ACTION_FORMAT = TAG + ":%1$s.%2$s";

	private EventObserver()
	{
	}

	public static <R extends Receiver, E extends Event> void register(Context context, R receiver, Class<E> eventClass)
	{
		registerInternal(context, receiver, eventClass, false);
	}

	public static <R extends Receiver, E extends Event> void registerSticky(Context context, R receiver, Class<E> eventClass)
	{
		registerInternal(context, receiver, eventClass, true);
	}

	public static <R extends Receiver> void unregister(Context context, R receiver)
	{
		if (context != null && receiver != null)
		{
			context.unregisterReceiver(receiver);
		}
	}

	public static <E extends Event> void sendEvent(Context context, E event)
	{
		sendEventInternal(context, event, false);
	}

	public static <E extends Event> void sendStickyEvent(Context context, E event)
	{
		sendEventInternal(context, event, true);
	}

	public static <E extends Event> void removeStickyEvent(Context context, Class<E> eventClass)
	{
		context.removeStickyBroadcast(getEventIntent(eventClass));
	}

	// INTERNAL METHODS

	private static <R extends Receiver, E extends Event> void registerInternal(Context context, R receiver, Class<E> eventClass, boolean sticky)
	{
		if (context != null && receiver != null && eventClass != null)
		{
			Intent intent = context.registerReceiver(receiver, getEventIntentFilter(eventClass));
			if (sticky)
			{
				receiver.onReceive(context, intent);
			}
		}
	}

	private static <E extends Event> void sendEventInternal(Context context, E event, boolean sticky)
	{
		if (context != null && event != null)
		{
			Intent intent = getEventIntent(event);
			if (sticky)
			{
				context.sendStickyBroadcast(intent);
			}
			else
			{
				context.sendBroadcast(intent);
			}
		}
	}

	private static String getEventAction(@NotNull Class<? extends Event> eventClass)
	{
		return String.format(ACTION_FORMAT, eventClass.getPackage().getName(), eventClass.getSimpleName());
	}

	private static Intent getEventIntent(@NotNull Class<? extends Event> eventClass)
	{
		return new Intent(getEventAction(eventClass));
	}

	private static IntentFilter getEventIntentFilter(@NotNull Class<? extends Event> eventClass)
	{
		return new IntentFilter(getEventAction(eventClass));
	}

	private static <E extends Event> Intent getEventIntent(@NotNull E event)
	{
		Intent intent = getEventIntent(event.getClass());
		intent.putExtra(EXTRA_EVENT, event);
		return intent;
	}

	// INNER CLASSES

	public interface Event extends Serializable
	{
	}

	public static abstract class Receiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (intent != null)
			{
				Event event = (Event) intent.getSerializableExtra(EXTRA_EVENT);
				onReceive(context, event);
			}
		}

		protected abstract void onReceive(Context context, Event event);
	}
}
