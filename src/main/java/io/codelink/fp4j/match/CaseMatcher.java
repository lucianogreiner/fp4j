package io.codelink.fp4j.match;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class CaseMatcher<T,R> implements Supplier<R> {

	private T value;
	
	private Case<T,R>[] cases;
		
	private CaseMatcher(T value, Case<T, R>[] cases) {
		this.value = value;
		this.cases = Objects.requireNonNull(cases);
	}

	@Override
	public R get() {
		for(Case<T, R> c : cases) {
			if(c.matches(value)) {
				return c.apply(value);
			}
		}
		return null;
	}
	
	@SafeVarargs
	public static <T,R> R match(T value, Case<T,R>... cases) {
		return new CaseMatcher<>(value, cases).get();
	}
	
	@SafeVarargs
	public static <T,R> CaseMatcher<T, R> matcher(T value, Case<T,R>... cases) {
		return new CaseMatcher<>(value, cases);
	}
	
	@SafeVarargs
	public static <T,R> Function<T, R> matcher(Case<T,R>... cases) {
		return (T value) -> match(value, cases);
	}
	
	public static <T,R> Case<T,R> when(Predicate<? super T> predicate, Function<? super T, ? extends R> action) {
		return new Case<>(predicate, action);
	}
	
	public static <T,R> Case<T,R> when(Predicate<? super T> predicate, Supplier<? extends R> supplier) {
		Function<T, R> action = toFunction(supplier);
		return when(predicate, action);
	}
	
	public static <T,R> Case<T,R> when(Predicate<? super T> predicate, Consumer<? super T> consumer) {
		Function<T, R> action = toFunction(consumer);
		return when(predicate, action);
	}
	
	public static <T,R> Case<T,R> when(Predicate<? super T> predicate, R result) {
		Function<T, R> action = toFunction(result);
		return when(predicate, action);
	}

	public static <T,R> Case<T,R> otherwise(Function<? super T, ? extends R> action) {
		return when((T value) -> true, action);
	}
	
	public static <T,R> Case<T,R> otherwise(Supplier<? extends R> supplier) {
		Function<T, R> action = toFunction(supplier);
		return otherwise(action);
	}
	
	public static <T,R> Case<T,R> otherwise(Consumer<? super T> consumer) {
		Function<T, R> action = toFunction(consumer);
		return otherwise(action);
	}
		
	public static <T,R> Case<T,R> otherwise(R result) {
		Function<T, R> action = toFunction(result);
		return otherwise(action);
	}
	
	private static <T, R> Function<T, R> toFunction(R result) {
		return (T value) -> result;
	}
	
	private static <T, R> Function<T, R> toFunction(Supplier<? extends R> supplier) {
		return (T) -> supplier.get();
	}
	
	private static <T, R> Function<T, R> toFunction(Consumer<? super T> consumer) {
		return (T value) -> { 
			consumer.accept(value);
			return null;
		};
	}
	
	public static final class Case<T,R> {
		
		private Predicate<? super T> predicate;
		private Function<? super T, ? extends R> action;
		
		private Case(Predicate<? super T> predicate, Function<? super T, ? extends R> action) {
			this.predicate = predicate;
			this.action = action;
		}
		
		private boolean matches(T value) {
			return predicate.test(value);
		}
		
		R apply(T value) {
			return action.apply(value);
		}
		
	}
}
