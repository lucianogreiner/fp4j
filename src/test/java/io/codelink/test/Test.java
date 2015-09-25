package io.codelink.test;

import static io.codelink.fp4j.Either.left;
import static io.codelink.fp4j.Either.right;
import static io.codelink.fp4j.match.CaseMatcher.*;
import static io.codelink.fp4j.match.CaseMatcher.otherwise;
import static io.codelink.fp4j.match.CaseMatcher.when;
import static io.codelink.fp4j.match.CaseMatchers.*;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import io.codelink.fp4j.Either;
import io.codelink.fp4j.match.CaseMatcher;

public class Test {

	@org.junit.Test
	public void test() {
		Either<String, Integer> value1 = left("Luciano");
		Either<String, Integer> value2 = right(10);

		Supplier<Integer> z = () -> 20;
		
		CaseMatcher<String, Integer> s = matcher("XPTO", 
								when(eq("xpto"), () -> 19),
								otherwise(20));

		System.out.println(s.get());
		
		
		
	}
	
	public String someOperation(Integer amount) {
		return match(amount,
					when(eq(10), "Bingo!"),
					when(in(2,3,5,7), (val) -> val + " is a Prime Number!"),
					otherwise(this::getDefault));
	}	

	public String getDefault() {
		return "Something we can't tell!";
	}
	
	public static <L,R> Predicate<Either<L,R>> leftValue(Predicate<L> predicate) {
		return (Either<L,R> e) -> e.isLeftPresent();
	}
	
}
