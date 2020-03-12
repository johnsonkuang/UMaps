import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.css";
import { Link } from "react-router-dom";

import "normalize.css";
import "./navbar.css";
import "bootstrap/dist/css/bootstrap.css";

//Stateless Functional Component

class NavBar extends Component {
    render() {
        return (
            <header style={{height: "50px"}}>
                        <input type="checkbox" id="nav-toggle" className="nav-toggle" />
                        <nav>
                            <ul>
                                <li style={{marginTop: "50px;"}}>
                                    <Link
                                        to="/"
                                        className={
                                            "nav-item " + (this.props.active === "RA" ? "active" : "")
                                        }
                                    >
                                        <a>
                                            home
                                        </a>
                                    </Link>
                                </li>
                                <li>
                                    <Link
                                        to="/path-app"
                                        className={
                                            "nav-item " + (this.props.active === "RA" ? "active" : "")
                                        }
                                    >
                                        <a href="">find path</a>
                                    </Link>
                                </li>
                            </ul>
                        </nav>
                        <label htmlFor="nav-toggle" className="nav-toggle-label">
                            <span></span>
                        </label>
            </header>
        );
    }
}

export default NavBar;
