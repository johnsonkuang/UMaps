### Answers Score: 23/26
- Pseudocode (Part 0): 6/8

Multiplication (Question 1): 2/3

A sum operation which turns many terms into one polynomial was used without
definition.

Division (Question 2,3): 4/5

Missing initial value for 'r'.

The loop in polynomial division needs to terminate when p becomes 0.  If you
don't have this condition in the loop, then an infinite loop can occur when the
degree of the divisor is 0, since the degree of p = 0 is also 0.

- Altered Rep Invs (Parts 1a, 2b, and 2c): 5/6

1.1: 2/2

2.2: 2/2

While mul,div,etc. would constantly increase the zero term's expt, this
shouldn't cause any major issues. We haven't talked about integer overflow in
this class, but it is well defined.

2.3: 1/2

isNaN would still function without changes, as we've not changed the restriction
on NaN's coefficient being NaN.

- Mutability (Part 1b): 2/2
- checkRep Usage (Parts 1c, 2a, and 3a): 6/6
- RatPoly Design (Part 3b): 4/4

### Code Quality Score: 3/3

### Mechanics: 3/3

#### Overall Feedback

A nice codebase! There are a few small things to mention, but nothing major.
Keep up the good work!

#### More Details

Make sure to remove useless comments, like TODOs and commented out code, to
make your code as clear as possible.

`degree` could be made much simpler by taking advantage of the representation
invariant that dictates `this.terms` is always sorted.

In `add` and `sub`, a lot of the code to merge terms duplicates work done in
`sortedInsert`, and could be much, much simpler.

In `add` and `sub`, there are loops to remove zero terms from ArrayLists freshly
copied from `this.terms`. `RatPoly`'s representation invariant guarantees no
zero terms, so this should be unnecessary.
