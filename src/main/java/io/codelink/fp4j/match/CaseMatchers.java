package io.codelink.fp4j.match;

import java.util.Collection;
import java.util.function.Predicate;

import io.codelink.fp4j.Either;

public class CaseMatchers<T> {

	@SafeVarargs
	public static <T> Predicate<T> in(T... values) {
		return (T other) -> {
			for(T value : values) {
				if(eq(value).test(other)) {
					return true;
				}
			}
			return false;
		};
	}
	
	public static <T> Predicate<T> eq(T value) {
		return (T other) -> {
			return value == other || value.equals(other);
		};
	}
	
	public static <T> Predicate<T> instanceOf(Class<?> clazz) {
		return (T value) -> {
			return clazz.isAssignableFrom(value.getClass());
		};
	}
	
	public static <T extends Collection<?>, Y> Predicate<T> empty() {
		return (T other) -> {
			return other == null || other.isEmpty();
		};
	}
	
	public static <T,R> Predicate<T> not(Predicate<T> predicate) {
		return predicate.negate();
	}
	
	public static <T extends Collection<?>, Y> Predicate<T> has(Y value) {
		return contain(value);
	}
	
	@SafeVarargs
	public static <T extends Collection<?>, Y> Predicate<T> contain(Y... values) {
		return (T collection) -> {
			if(collection == null || collection.isEmpty()) {
				return false;
			}
			for(Y value : values) {
				if(!collection.contains(value)) {
					return false;
				}
			}
			return true;
		};
	}
	
	public static <T extends Collection<?>, Y> Predicate<T> size(Integer size) {
		return (T collection) -> {
			return collection != null && collection.size() == size;
		};
	}
	
	public static <T extends Collection<?>, Y> Predicate<T> size(Predicate<Integer> filter) {
		return (T collection) -> {
			return collection != null && filter.test(collection.size());
		};
	}
	
	public static Predicate<Integer> range(Integer initialValue, Integer finalValue) {
		return (Integer other) -> {
			return other != null &&  other >= initialValue && other <= finalValue;
		};
	}
	
	public static Predicate<String> hasLength() {
		return (String value) -> {
			return value != null &&  value.length() > 0;
		};
	}
	
	public static Predicate<String> length(Integer size) {
		return (String value) -> {
			return value != null &&  value.length() == size;
		};
	}
	
	public static <L,R> Predicate<Either<L,R>> hasLeft() {
		return (Either<L,R> value) -> {
			return value != null && value.isLeftPresent();
		};
	}
	
	public static <L,R> Predicate<Either<L,R>> left(Predicate<? super L> predicate) {
		return (Either<L,R> value) -> {
			Predicate<Either<L,R>> left = hasLeft();
			return left.test(value) && predicate.test(value.left());
		};
	}
	
	public static <L,R> Predicate<Either<L,R>> hasRight() {
		return (Either<L,R> value) -> {
			return value != null && value.isRightPresent();
		};
	}
	
	public static <L,R> Predicate<Either<L,R>> right(Predicate<? super R> rightPredicate) {
		return (Either<L,R> value) -> {
			Predicate<Either<L,R>> right = hasRight();
			return right.test(value) && rightPredicate.test(value.right());
		};
	}
	
}