import { Typography } from "@mui/material";
import React, { Component } from "react";
import OpenViduVideoComponent from "./OvVideo";
import "./UserVideo.css";

export default class UserVideoComponent extends Component {
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
            <Typography>{this.props.nickname}</Typography>
            <OpenViduVideoComponent streamManager={this.props.streamManager} />
          </div>
        ) : null}
      </div>
    );
  }
}
