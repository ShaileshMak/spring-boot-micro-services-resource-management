import React, { Component } from "react";
import { Container, Row, Col, Button } from "reactstrap";

export default class ProjectDetails extends Component {
    render() {
        let projectName = '';
        let projectDes = '';
        let projectStatus = '';
        if (this.props.project) {
            projectName = this.props.project.projectName;
            projectDes = this.props.project.projectDes;
            projectStatus = this.props.project.projectStatus;
        }
        debugger;
        return (
            <Container>
                <Row>
                    <Col>
                        <div>
                            <span><strong>Project: </strong>{projectName}</span>
                        </div>
                        <div>
                            <span><strong>Project Description: </strong>{projectDes}</span>
                        </div>
                        <div>
                            <span><strong>Project Manager: </strong>{this.props.projectManager ? (
                                `${this.props.projectManager.firstName} ${this.props.projectManager.lastName}`) : (
                                    <Button color="primary" >Assign PM</Button>
                                )}</span>
                        </div>
                        <div>
                            <span><strong>Project Status: </strong>{projectStatus}</span>
                        </div>
                    </Col>
                </Row>
            </Container>
        );
    }
}