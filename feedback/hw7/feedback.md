### Scores: 2/2
- Documenting Changes: 2/2

### Design: 3/3

### Documentation & Specification (including JavaDoc): 3/3

### Code quality (code and internal comments including RI/AF when appropriate): 3/3

### Testing (test suite quality & implementation): 2/3

### Mechanics: 3/3

#### Overall Feedback
Good job! Most everything looks good in this assignment. Just remember to write more thorough tests!

#### More Details
- Your graph building operation in CampusMap should be factored out so that it can be easily tested.
- You talk about throwing exceptions in your CampusMap, but those are never tested in implementation tests
- You should not need an "N extends Object" in your declaration in Graph. By default, all generic types should extend Object unless otherwis specified.
- Those wild cards in the map are not necessary and might cause some trouble for you down the line.
