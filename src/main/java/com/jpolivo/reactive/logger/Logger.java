package com.jpolivo.reactive.logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.MDC;

import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

public interface Logger {

	/*static String getContextMapKey() {
		return "context-map";
	}

	default <T> Consumer<Signal<T>> logOnNext(Consumer<T> log) {
		return signal -> {
			if (signal.getType() != SignalType.ON_NEXT)
				return;

			Optional<Map<String, String>> maybeContextMap = signal.getContextView().getOrEmpty(getContextMapKey());

			if (maybeContextMap.isEmpty()) {
				log.accept(signal.get());
			} else {
				MDC.setContextMap(maybeContextMap.get());
				try {
					log.accept(signal.get());
				} finally {
					MDC.clear();
				}
			}
		};
	}

	default <T> Consumer<Signal<T>> logOnError(Consumer<Throwable> log) {
		return signal -> {
			if (!signal.isOnError())
				return;

			Optional<Map<String, String>> maybeContextMap = signal.getContextView().getOrEmpty(getContextMapKey());

			if (maybeContextMap.isEmpty()) {
				log.accept(signal.getThrowable());
			} else {
				MDC.setContextMap(maybeContextMap.get());
				try {
					log.accept(signal.getThrowable());
				} finally {
					MDC.clear();
				}
			}
		};
	}

	default Function<Context, Context> put(String key, String value) {
		return ctx -> {
			Optional<Map<String, String>> maybeContextMap = ctx.getOrEmpty(getContextMapKey());

			if (maybeContextMap.isPresent()) {
				maybeContextMap.get().put(key, value);
				return ctx;
			} else {
				Map<String, String> ctxMap = new HashMap<>();
				ctxMap.put(key, value);

				return ctx.put(getContextMapKey(), ctxMap);
			}
		};
	}*/

	

	default Runnable wrap(final Runnable r) {
		Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
		return () -> {
			Map<String, String> backupOfContextMap = MDC.getCopyOfContextMap();
			MDC.setContextMap(copyOfContextMap);
			try {
				r.run();
			} finally {
				if (backupOfContextMap == null) {
					MDC.clear();
				} else {
					MDC.setContextMap(backupOfContextMap);
				}
			}
		};
	}

	default <T> Consumer<T> wrap(final Consumer<? super T> consumer) {
		Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
		return t -> {
			Map<String, String> backupOfContextMap = MDC.getCopyOfContextMap();
			MDC.setContextMap(copyOfContextMap);
			try {
				consumer.accept(t);
				/*
				 * the following might be helpful for debugging to indicate the element that
				 * raised the problem } catch (RuntimeException e) { throw new
				 * RuntimeException("unable to handle " + t, e);
				 */
			} finally {
				if (backupOfContextMap == null) {
					MDC.clear();
				} else {
					MDC.setContextMap(backupOfContextMap);
				}
			}
		};
	}

}
