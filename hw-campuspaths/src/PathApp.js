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
import { CSSTransition, transit } from "react-css-transition";
import { Link } from "react-router-dom";
import { IoIosArrowUp, IoIosArrowDown} from "react-icons/io";
import { MdEmail, MdRefresh } from "react-icons/md";
import { FaMapMarkedAlt } from "react-icons/fa";

import "bootstrap/dist/css/bootstrap.css";

class PathApp extends Component {
    constructor(props) {
        super(props);
        this.state = {
            backgroundImage: null,
            busy: true,
            buildingValues: [],
            buildings: {},
            start: "",
            startValue: "",
            dest: "",
            destValue: "",
            pathData: {},
            paths: [],
            footerOn: false,
            email: "",
            formEmailSent: false,
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
            alert("Start and destination is the same! Resetting now.\n" +
                "这是什么意思，起点和终点是同一个地方，您在干什么？？？重新启动了。");
            window.location.reload(false);
            return;
        }

        try{
            fetch("http://localhost:4567/path?start=" + this.state.start + "&dest=" + this.state.dest)
                .then(response => {
                    if(!response.ok){
                        if(this.state.start === "" || this.state.dest === ""){
                            alert("你是笨蛋吗？");
                        } else {
                            alert("Oops something went wrong! Expected: 200, Was: " + response.status);
                        }
                        return;
                    }
                    return response.json()
                })
                .then(data => {
                    /**
                     * Data processing
                     *  -   Rounding cost
                     */
                    try{
                        data["cost"] = Math.round(data["cost"]);
                        console.log(data);
                        this.setState({
                            pathData: data,
                            paths: data["path"]
                        });
                    } catch (e) {
                        console.log(e);
                    }
                })
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
        this.getEmailDirections();
    }

    handleStartChange = (value) => {
        this.setState({
            start: this.getKeyByValue(this.state.buildings, value),
            startValue: value
        });
    }

    handleDestChange = (value) => {
        this.setState({
            dest: this.getKeyByValue(this.state.buildings, value),
            destValue: value
        });
    }

    handleSubmit = async (event) => {
        event.preventDefault();

        if(this.sendPath(
            this.state.email,
            this.state.startValue,
            this.state.destValue,
            this.state.path_directions
        )){

        this.setState({
            formSubmitted: true
        })}
    }

    getEmailDirections = async () => {
        try{
            fetch("http://localhost:4567/email-directions?start=" + this.state.start + "&dest=" + this.state.dest)
                .then(response => {
                    if(!response.ok){
                        alert("Oops something went wrong with fetching the long emailable path! Expected: 200, Was: " + response.status);
                        return;
                    }
                    return response.json()
                })
                .then(data => {
                    this.setState({
                        path_directions: data
                    })
                })
        } catch (e) {
            alert("There was an error contacting the server. Try again later!");
            console.log(e);
        }
    };

    validateEmail = (mail) =>
    {
        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))
        {
            return (true)
        }
        alert("You have entered an invalid email address!")
        return (false)
    }

    async sendPath(to_email, start, end, path_directions) {
        if(this.validateEmail(to_email)){
            let template_params = {
                "to_email": to_email,
                "start": start,
                "end": end,
                "path_directions": path_directions
            }

            var service_id = "default_service";
            var template_id = "uw_campusmap";
            window.emailjs.send(service_id, template_id, template_params)
                .then(res => {
                    this.setState({ formEmailSent: true })
                })
                // Handle errors here however you like, or use a React error boundary
                .catch(err => console.error('Failed to send feedback. Error: ', err));
            return true;
        }
        return false;
    }

    render() {
        return (
            <div style={{overflow: "hidden"}}>
                <Map
                    backgroundImage = {this.state.backgroundImage}
                    width={this.state.backgroundImage ? this.state.backgroundImage.width : 0}
                    height={this.state.backgroundImage ? this.state.backgroundImage.height : 0 }
                    path={this.state.paths}
                />
                <div style={{position: "fixed", bottom: 0, width: "100%"}}>
                    <CSSTransition
                        defaultStyle={{ transform: "translate(0, 0)", width: "100%"}}
                        enterStyle={{ transform: transit("translate(0, -125px)", 1000, "ease-in-out") }}
                        leaveStyle={{ transform: transit("translate(0, 0)", 1000, "ease-in-out") }}
                        activeStyle={{ transform: "translate(0, -125px)" }}
                        active={this.state.footerOn}
                    >
                        <button className={"btn btn-outline-info"} style={{margin: "5px"}} onClick={() => (
                            this.setState({footerOn: !this.state.footerOn})
                        )}>
                            {this.state.footerOn ? <IoIosArrowDown/> : <IoIosArrowUp/>}
                        </button>
                        <footer style={
                            {
                                backgroundColor: "#FFF",
                                marginTop: "200px",
                                height: "125px",
                                width: "100%",
                                opacity: "90%",
                                resize: "none",
                                clear: "both",
                                position: "absolute",
                                bottom: "-125px"
                            }} key={this.state.footerOn}>
                            <div className={"container"} style={{margin: "20px auto"}}>
                                <div className={"row"}>
                                    <div className={"col-7"}>
                                        <BuildingSelector
                                            busy={this.state.busy}
                                            buildingValues={this.state.buildingValues}
                                            startValue = {this.state.startValue}
                                            destValue = {this.state.destValue}
                                            handleStart={this.handleStartChange}
                                            handleDest={this.handleDestChange}
                                        />
                                    </div>
                                    <div className={"col-1"}>
                                        <div style={{marginTop: "1 px", marginBottom: "10px"}}>
                                            <button
                                                className={"btn btn-outline-info"}
                                                onClick={this.getPath}
                                            >
                                                <FaMapMarkedAlt/>
                                            </button>
                                        </div>
                                        <div style={{margin: "5px 0"}}>
                                            <button
                                                className={"btn btn-outline-danger"}
                                                onClick={() => window.location.reload(false)}
                                            >
                                                <MdRefresh/>
                                            </button>
                                        </div>
                                    </div>
                                    <div className={"col-3"}>
                                        <form style={{display: (this.state.paths.length === 0 ? "none" : "block")}}>
                                            <div className="form-group">
                                                <input type="email" className="form-control" id="exampleInputEmail1"
                                                       aria-describedby="emailHelp" placeholder="Enter email" value={this.state.email}
                                                       onChange={this.onEmailChange}
                                                />
                                                {this.state.formEmailSent ? <small>
                                                    email successfully sent!
                                                </small>: ""}
                                            </div>
                                        </form>
                                    </div>
                                    <div className={"col-1"}>
                                        <button
                                            className={"btn btn-outline-primary"}
                                            style={{display: (this.state.paths.length === 0 ? "none" : "block")}}
                                            onClick={this.handleSubmit}
                                        >
                                            <MdEmail/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </footer>
                    </ CSSTransition>
                </div>
            </div>
        );
    }

    //updates the email value in the input form
    onEmailChange = (event) => {
        this.setState({
            email: event.target.value
        })
    }



    //gets the key in a javascript object based on a value
    //@requires: value must be a valid value in the object.values(object)
    getKeyByValue = (object, value) => {
        return Object.keys(object).find(key => object[key] === value);
    }
}

export default PathApp;
