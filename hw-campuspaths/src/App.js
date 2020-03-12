/*
 * Copyright (C) 2020 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import Map from "./Map";
import BuildingSelector from "./BuildingSelector";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            backgroundImage: null,
            busy: true,
            buildingValues: [],
            buildings: {},
            start: "",
            dest: "",
            pathData: {},
            paths: []
        }
    }


    componentDidMount() {
        this.getBuildingData();
        this.fetchAndSaveImage();
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    getBuildingData = async () => {
        try{
            fetch("http://localhost:4567/buildings")
                .then(response => {
                    if(!response.ok){
                        alert("Oops something went wrong! Expected: 200, Was: " + response.status);
                        return;
                    }
                    return response.json()
                })
                .then(data => {
                    const buildingValues = Object.values(data);
                    this.setState({
                        busy: false,
                        buildingValues: buildingValues,
                        buildings: data
                    })
                });
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }

    getPath = async () => {
        /**
         * Validation
         *  - start != dest
         *  whether the building exists is verified by the server
         */
        if(this.state.start === this.state.dest){
            return;
        }

        try{
            fetch("http://localhost:4567/path?start=" + this.state.start + "&dest=" + this.state.dest)
                .then(response => {
                    if(!response.ok){
                        alert("Oops something went wrong! Expected: 200, Was: " + response.status);
                        return;
                    }
                    return response.json()
                })
                .then(data => {
                    /**
                     * Data processing
                     *  -   Rounding cost
                     */
                    data["cost"] = Math.round(data["cost"]);
                    console.log(data);
                    this.setState({
                        pathData: data,
                        paths: data["path"]
                    });
                })
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }

    handleStartChange = (value) => {
        this.setState({
            start: this.getKeyByValue(this.state.buildings, value)
        });
    }

    handleDestChange = (value) => {
        this.setState({
            dest: this.getKeyByValue(this.state.buildings, value)
        });
    }

    render() {
        return (
            <div>
                <div className={"container"}>
                    <div className={"row"}>
                        <div className={"col-10"}>
                            <BuildingSelector
                                busy={this.state.busy}
                                buildingValues={this.state.buildingValues}
                                handleStart={this.handleStartChange}
                                handleDest={this.handleDestChange}
                            />
                        </div>
                        <div className={"col-2"}>
                            <div style={{margin: "0 5px"}}>
                                <button
                                    className={"btn btn-outline-info"}
                                    onClick={this.getPath}
                                >
                                    Find Path!
                                </button>
                            </div>
                            <div style={{margin: "0 5px"}}>
                                <button
                                    className={"btn btn-outline-danger"}
                                    onClick={() => window.location.reload(false)}
                                >
                                    Reset
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <Map
                    backgroundImage = {this.state.backgroundImage}
                    width={this.state.backgroundImage ? this.state.backgroundImage.width : 0}
                    height={this.state.backgroundImage ? this.state.backgroundImage.height : 0 }
                    path={this.state.paths}
                />
            </div>
        );
    }

    //gets the key in a javascript object based on a value
    //@requires: value must be a valid value in the object.values(object)
    getKeyByValue = (object, value) => {
        return Object.keys(object).find(key => object[key] === value);
    }
}

export default App;
