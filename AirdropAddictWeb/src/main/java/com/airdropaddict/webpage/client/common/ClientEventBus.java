package com.airdropaddict.webpage.client.common;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ClientEventBus {
	private static final Map<Class<? extends Object>, Set<EventHandler<Object>>> handlersByType = new HashMap<>();

	private ClientEventBus() {
	}

	public static <T extends Object> boolean register(Class<T> eventType, EventHandler<T> handler) {
		Set<EventHandler<Object>> handlers = handlersByType.computeIfAbsent(eventType, t -> new LinkedHashSet<>());
		@SuppressWarnings("unchecked")
		EventHandler<Object> casted = (EventHandler<Object>) handler;
		return handlers.add(casted);
	}

	public static <T> boolean unregister(Class<T> eventType, EventHandler<T> handler) {
		Set<EventHandler<Object>> handlers = handlersByType.get(eventType);
		return handlers == null ? false : handlers.remove(handler);
	}

	public static <T> void post(Object event) {
		ofNullable(event).map(e -> handlersByType.get(e.getClass())).ifPresent(s -> s.forEach(h -> h.handle(event)));
	}

	public interface EventHandler<T> {
		void handle(T event);
	}
}
