import React, { Component } from "react";
import OpenViduVideoComponent from "./OvVideo";
import "./UserVideo.css";

export default class MainUserVideoComponent extends Component {
  getNicknameTag() {
    // Gets the nickName of the user
    return this.props.nickname;
    // return JSON.parse(this.props.streamManager.stream.connection.data)
    //   .clientData;
  }

  render() {
    return (
      <div>
        {this.props.streamManager !== undefined ? (
          <div className="streamcomponent">
            <OpenViduVideoComponent streamManager={this.props.streamManager} />
          </div>
        ) : null}
      </div>
    );
  }
}
