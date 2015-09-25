# fp4j
Functional Programming Utilities for Java 8.

This library contains some functional programming constructs heavily inspired on Scala programming language that are still missing in Java 8 standard api.

## Either
Java 8 introduced java.util.Optional object that makes it easier to represent an optional values in a null-safe manner. The use of it turns very noticeably what parameters, return values and variables might be null. 

It's also quite often useful to have some logic that might result in either one of two different types of values depending on a set of inputs. In Java, and in the majority of programming languages, it is not possible for same function or method that result more than one type and value.

In this cases, the solution is usually to split the implementation into separated methods. This is ok when the consumer is interested in one of the possible outcomes, otherwise it might be necessary to call both methods in order to get the actual result, or to have an extra method to advice the outcome type.

The Either object abstraction consists in a wrapper object containing two optional values of unrelated types - left and right. Similary to  java Optional implementation, which makes it possible to check if the value is present, the Either allows the check for left or right presence, one and only one side value will be there.

Example:
```java
import static io.codelink.fp4j.Either.left;
import static io.codelink.fp4j.Either.right;
import io.codelink.fp4j.Either;

public Either<String,Integer> someInterestingStuff(Boolean whatToDo) {
  if(whatToDo) {
    return left("A string here");
  }
  return right(1000);
}

public void foo() {
  Either<String,Integer> result = this.someInterestingStuff(false);
  if(result.isLeftPresent()) {
    String s = result.left();
    // deal with String result...
  } else {
    Integer i = result.right();
    // deal with Integer result...
  }
}
```

##Pattern Matching

  Besides some cool features, there were enhancements in Java 8 API to take advantage of FP using lambdas. Maybe the most impacting improvement has been made in Collections API with the introduction of Streams. With Streams, it is possible to pass functions to perform common tasks over collection elements, such as iteration and filtering. The result is a more concise and small code.

Most of functional programming languages have the pattern matching construct. Beyond the concept, with pattern matching it is possible to write complex condition checking and action execution in an elegant and functional manner. 

Example:

```java
import static io.codelink.fp4j.match.CaseMatcher.match;
import static io.codelink.fp4j.match.CaseMatcher.otherwise;
import static io.codelink.fp4j.match.CaseMatcher.when;
import static io.codelink.fp4j.match.CaseMatchers.*;

// Match Number values
public String evaluateAmount(Integer amount) {
	return match(amount,
				  when(eq(10), "Bingo!"),
				  when(in(2,3,5,7), (val) -> val + " is a Prime Number!"),
				  when(range(11, 20), () -> "It is between 11 and 20!"),
				  otherwise(this::getDefaultString)); // call a supplier for String val
}

// Match Strings
public Boolean evaluateString(String value) {
  return match(value,
            when(eq("SPECIAL"), true),
            when(not(hasLength()), () -> false),
            otherwise(true)); // does not make much sense here hum...
}

// Match Collections
public EvaluationType evaluateList(List<String> names) {
	return match(names,
				  when(empty(), UNRATED), // return a value
				  when(size(between(5, 10)), () -> SMALL) // use a supplier
				  when(contain("Jonny", "Steve").and(size(2))
				  	, (list) -> specialEvaluation(list)) // use a function
				  otherwise(doSomethingElse())); // call a method
}


// Use your Pattern matcher as a Supplier or Function to pass it along and call later!!!
public void amazingMatcher() {
   String val = "fp4j"
   Supplier<String> stringMatcher = matcher(val,
					when(contains("fp"), "It's functional!"),
					otherwise("It's something else"));
   // Execute...
   String result = stringMatcher.get();

   // What if you don't have the value, but just the cases?
   Function<Integer, String> funcMatcher = matcher(
						when(eq(10), "Bingo!"),
						when(gt(10), "Wrong"),
						otherwise("Fine"));
   // Call the function
   String result2 = funcMatcher.apply(20);
   // or pass it along...
   doSomething(funcMatcher);
}

```
