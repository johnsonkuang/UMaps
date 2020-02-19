### Answers Score: 10/10
- Problem 1: 7/7
- Problem 4: 3/3

### Design: 3/3

### Documentation & Specification (including JavaDoc): 3/3

### Code quality (code and internal comments including RI/AF): 3/3

### Testing (test suite quality & implementation): 1/3

### Mechanics: 3/3

#### Overall Feedback

The representation makes sense.  Good documentation and design.  The
implementation testing is low coverage relative to the number of operations
supported.  Half the specification tests are useless because they don't use
`ListNodes` or `ListChildren` to check the result of the operations used.

#### More Details

For the `equals` method, it's not enough to just say "true iff this and o
represent the same Foo", since you have yet to define what that means.
