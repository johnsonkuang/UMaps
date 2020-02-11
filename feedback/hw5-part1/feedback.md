### Answers Score: 18/20
- Problem 1a: 6/7

IntQueue1

I think you meant "every element in entries != null".

IntQueue2

The representation invariant for `IntQueue2` ought to be:
```
entries != null && 0 <= front < entries.length && 0 <= size <= entries.length
```
And primitives can't be null anyways, so it doesn't make sense to check for that.
(-1)

- Problem 1b: 1/1
- Problem 1c: 5/6

5

For #5, note that `Iterator` contains a `remove` method which can mutate the
underlying data (-1).

- Problem 2: 3/3
- Problem 3: 3/3

### Design: 1/3

You don't need both ListNodes and getNodes -- ListNodes can be implemented on
top of getNodes. And your Graph should definitely *not* impose any ordering on
nodes. Any sorting should be done by clients (e.g. the test driver).

And listNodes/listChildren returning Strings is not super useful. You need to
return a reference to the actual edge label and target node -- these will be
important for the graph algorithms you will be implementing in future homeworks.

And you can probably ditch the second constructor -- it is just exposing you to
potential rep exposure if you are not careful.

And, you should be wary of using lists throughout your Graph. It could get way
too slow. There are other data structures which will be asymptotically faster
which you should use instead.

### Documentation & Specification (including JavaDoc): 3/3

Make sure that your specs don't discuss implementation details (anything not
mentioned in the class comments as abstract state, like spec fields) -- e.g. in
addEdge.

You ought to have abstract invariants of the graph listed in the class comment 
(e.g. regarding repeated edge labels, nulls as nodes/edges, etc.)

If you don't want to make the invalid inputs throw exceptions (i.e. if you prefer
@requires), be sure to use assertions to sanity check the pre-conditions as a
defensive programming measure (in HW5-2).

### Testing (test suite quality & implementation): 3/3

Mostly good spec tests, but... your test that just creates a graph is not super
useful. All it is testing is that the constructor doesn't crash. It doesn't check
that anything is correct about its implementation. It might be okay to omit a
test like this if it is not useful and just explain that the code is tested 
elsewhere (e.g. the constructor is tested in the test of AddNode). But don't just
put in tests like that which don't really test much.

### Code quality (code stubs/skeletons only, nothing else): 3/3

### Mechanics: 3/3

#### Overall Feedback

Make sure to think through your graph ADT and all these comments carefully, then
make significant design changes before you begin implementing HW5-2.

#### More Details

None.
