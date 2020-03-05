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

import React, {Component} from 'react';
import EdgeList from "./EdgeList";
import Grid from "./Grid";
import GridSizePicker from "./GridSizePicker";

// Allows us to write CSS styles inside App.css, any any styles will apply to all components inside <App />
import "./App.css";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            gridSize: 4,  // The number of points in the grid
            edges: "",
            edgeCoords: [], //the current list of edges
        };
        //edge coordinates given as
        //{x1, y1, x2, y2, color}
    }



    updateGridSize = (event) => {
        // Every event handler with JS can optionally take a single parameter that
        // is an "event" object - contains information about an event. For mouse clicks,
        // it'll tell you thinks like what x/y coordinates the click was at. For text
        // box updates, it'll tell you the new contents of the text box, like we're using
        // below:
        if(Math.min(parseInt(event.target.value)) > 200){
            alert("Grid Size was too large, it has been reset to 200");
        } else if(Math.min(parseInt(event.target.value)) < 1){
            alert("Grid Size was too small, it has been reset to 1");
        } else {
            this.setState({
                gridSize: Math.max(1, Math.min(parseInt(event.target.value), 200)),
                edges: "",
                edgeCoords: []
            });
        }
    };

    updateEdgesList = (ref) => {
        this.setState({
            edges: ref.current.value
        });
    }

    drawEdges = () => {
        let edges = this.state.edges.split("\n");
        let errors = [];
        for(let idx in edges){
            if(!/^[0-9]+,[0-9]+\s[0-9]+,[0-9]+\s[a-zA-Z]+$/.test(edges[idx])){
                errors.push(edges[idx] + "\n");
            }
        }

        const errorMessage = () => {
            let errorMessage = "";
            for(let idx in errors){
                errorMessage += errors[idx];
            }
            return errorMessage;
        }

        if(errors.length > 0){
            alert("There are errors in the following entrys: \n" + errorMessage());
            return;
        }

        let edgeCoords = [];
        for(let idx in edges){
            const line = edges[idx].split(" ");
            const firstCoord = line[0].split(",");
            const secCoord = line[1].split(",");

            edgeCoords.push({
                x1: parseInt(firstCoord[0]),
                y1: parseInt(firstCoord[1]),
                x2: parseInt(secCoord[0]),
                y2: parseInt(secCoord[1]),
                color: line[2]
            })
        }

        this.setState({
            edgeCoords: edgeCoords,
        })
    }

    clearEdges = () => {
        this.setState({
            edges: "",
            edgeCoords: []
        })
    }

    render() {
        const canvas_size = 500;
        return (
            <div>
                <p id="app-title">Connect the Dots!</p>
                <GridSizePicker value={String(this.state.gridSize)} onChange={this.updateGridSize}/>
                <Grid size={this.state.gridSize} edgeCoords={this.state.gridSize ? this.state.edgeCoords : []} width={canvas_size} height={canvas_size}/>
                <EdgeList value={this.state.edges} onChange={this.updateEdgesList} onClear={this.clearEdges} onDraw={this.drawEdges} />
            </div>

        );
    }

}

export default App;
