package com.airdropaddict.webpage.client.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientEventBus {
	private static final Map<Class<? extends Object>, List<EventHandler<Object>>> handlers = new HashMap<>();

	private ClientEventBus() {
	}

	public static <T> void register(Class<T> eventType, EventHandler<T> handler) {
		List<EventHandler<Object>> handlerList = handlers.get(eventType);
		if (handlerList == null) {
			handlerList = new ArrayList<EventHandler<Object>>();
			handlers.put(eventType, handlerList);
		}
		
		@SuppressWarnings("unchecked")
		EventHandler<Object> casted = (EventHandler<Object>) handler;
		handlerList.add(casted);
	}

	public static <T> void unregister(Class<T> eventType, EventHandler<T> handler) {
		// bus.unregister(listener);
	}

	public static void post(Object event) {
		List<EventHandler<Object>> handlerList = handlers.get(event.getClass());
		if (handlerList == null) {
			return;
		}

		for (EventHandler<Object> handler : handlerList) {
			handler.handle(event);
		}
	}

	public interface EventHandler<T> {
		void handle(T event);
	}
}
