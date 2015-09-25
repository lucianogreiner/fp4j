package io.codelink.fp4j;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Either<L, R> {
	
	private Optional<L> left;
	
	private Optional<R> right;

	private Either(Optional<L> left, Optional<R> right) {
		this.left = left;
		this.right = right;
	}

	public L left() {
		return left.get();
	}

	public R right() {
		return right.get();
	}

	public boolean isLeftPresent() {
		return left.isPresent();
	}

	public boolean isRightPresent() {
		return right.isPresent();
	}
	
	public L leftOrElse(L other) {
		return left.orElse(other);
	}
	
	public R rightOrElse(R other) {
		return right.orElse(other);
	}

	public L leftOrElseGet(Supplier<? extends L> supplier) {
		return left.orElseGet(supplier);
	}
	
	public R rightOrElseGet(Supplier<? extends R> supplier) {
		return right.orElseGet(supplier);
	}
	
	public <X extends Throwable> L leftOrElseThrow(Supplier<X> exceptionSupplier) throws X {
		return left.orElseThrow(exceptionSupplier);
	}
	
	public <X extends Throwable> R rightOrElseThrow(Supplier<X> exceptionSupplier) throws X {
		return right.orElseThrow(exceptionSupplier);
	}
	
	public <U> Optional<U> mapLeft(Function<? super L, ? extends U> mapper) {
		return left.map(mapper);
	}
	
	public <U> Optional<U> mapRight(Function<? super R, ? extends U> mapper) {
		return right.map(mapper);
	}
	
	public <U> Optional<U> flatMapLeft(Function<? super L, Optional<U>> mapper) {
		return left.flatMap(mapper);
	}
	
	public <U> Optional<U> flatMapRight(Function<? super R, Optional<U>> mapper) {
		return right.flatMap(mapper);
	}
	
	public Optional<L> filterLeft(Predicate<? super L> predicate) {
		return left.filter(predicate);
	}
	
	public Optional<R> filterRight(Predicate<? super R> predicate) {
		return right.filter(predicate);
	}
	
	public static <L, R> Either<L, R> left(L value) {
		return new Either<>(Optional.of(value), Optional.empty());
	}
	
	public static <L, R> Either<L, R> right(R value) {
		return new Either<>(Optional.empty(), Optional.of(value));
	}

}