/*
 * Copyright ©2019 Dan Grossman.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

/*
 * A Textfield that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */

import React, {Component} from 'react';

/**
 * Props:
 *
 * onChange - a listener for when the edge text area has a keyboard event
 * value - the value to display in the text area
 */
class EdgeList extends Component {
    render() {
        // IntelliJ might complain about "this.props.onChange" not existing.
        // Don't worry, inside <App /> we're passing something in as an onChange prop, so it exists.
        // IntelliJ just isn't quite smart enough to understand how props work in React.
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={this.props.onChange}
                    value={this.props.value}
                /> <br/>
                <button onClick={() => {console.log('Draw onClick');}}>Draw</button>
                <button onClick={() => {console.log('Clear onClick');}}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
