import React, {Component} from 'react';

import { useAlert } from 'react-alert'
import Combobox from "react-widgets/lib/Combobox";

import "bootstrap/dist/css/bootstrap.css";
import "react-widgets/dist/css/react-widgets.css";

/**
 * Handles building selection, passes information up to App
 */
class BuildingSelector extends Component {
    render() {
        return(
          <div className={"container"}>
              <div className={"row"}>
                  <div className={"col-md-4"}>
                      <h2>Start: </h2>
                  </div>
                  <div className={"col-md-8"}>
                      <Combobox
                          busy={this.props.busy}
                          data={this.props.buildingValues}
                          caseSensitive={false}
                          minLength={3}
                          filter={'contains'}
                          style={{width: "60%"}}
                          onSelect={this.props.handleStart}
                      />
                  </div>
              </div>
              <div className={"row"}>
                  <div className={"col-md-4"}>
                      <h2>Destination: </h2>
                  </div>
                  <div className={"col-md-8    "}>
                      <Combobox
                          busy={this.props.busy}
                          data={this.props.buildingValues}
                          caseSensitive={false}
                          minLength={3}
                          filter={'contains'}
                          style={{width: "60%"}}
                          onSelect={this.props.handleDest}
                      />
                  </div>
              </div>
          </div>
        );
    }
}
//No validation, except for empty list (done server side) dropdown guarantees well-formed input

export default BuildingSelector;