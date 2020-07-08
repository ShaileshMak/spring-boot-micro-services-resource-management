import React, { Component } from "react";
import {
    Container, Row, Col,
    FormGroup, Dropdown, 
    DropdownToggle, DropdownMenu, 
    DropdownItem
  } from 'reactstrap';
import ProjectDetails from "./../projectDetails/ProjectDetails";

export default class projects extends Component {
    constructor(props) {
        super(props);
        this.state = {
            project: 'Choose project',
            projectDropdownOpen: false,
            selectedProject: null,
        }
        this.handleProjectDDChange = this.handleProjectDDChange.bind(this);
    }

    getprojectById = id => {
        const project = this.props.projects.filter(project => parseInt(project.projectId) === parseInt(id));
        return project[0];
    }

    handleProjectDDChange = (event) => {
        debugger;
        const { target } = event;
        const value = target.value;
        const selectedProject = this.props.projects[parseInt(value)];
        if (selectedProject.projectManager) {
            const projectManager = this.getProjectManager(parseInt(selectedProject.projectManager));
            selectedProject.projectManagerName = `${projectManager.firstName} ${projectManager.lastName}`;
        }
        this.setState({
            project: selectedProject.projectName,
            selectedProject: selectedProject
        });
    }
    projectToggle = () => this.setState({projectDropdownOpen : !this.state.projectDropdownOpen});

    getProjectManager = managerId => {
        const manager = this.props.employees.filter(employee => parseInt(employee.employeeId) === parseInt(managerId));
        return manager[0];
    }
    render() {
        const { 
            project,
            projectDropdownOpen,
            selectedProject
        } = this.state;
        const {
            projects
        } = this.props;

        const projectProps = {
            project: selectedProject
        };
        projectProps.projectManager = (selectedProject && selectedProject.projectManager && this.getProjectManager(selectedProject.projectManager)) || '';
        return(
            <Container>
                <Row>
                    <Col>
                        <h2>Registered Projects</h2>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <FormGroup>
                            <Dropdown
                                isOpen={projectDropdownOpen}
                                toggle={this.projectToggle}
                                id="project"
                                value={selectedProject}
                            >
                                <DropdownToggle style={{width: "100%", textAlign: "left"}} caret>
                                {project}
                                </DropdownToggle>
                                <DropdownMenu className="col-12">
                                {projects && projects.length > 0 && projects.map((pj,index) => <DropdownItem key={`projectSelect${index}`} onClick={this.handleProjectDDChange} name="project" value={index}>{pj.projectName}</DropdownItem>)}
                                </DropdownMenu>
                            </Dropdown>
                        </FormGroup>
                    </Col>
                </Row>
                {selectedProject ? (
                    <Row>
                        <Col>
                            <ProjectDetails project={selectedProject} projectManager={this.getProjectManager(selectedProject.projectManager)}/>
                        </Col>
                    </Row>
                ) : null}
                
            </Container>
        );
    }
}