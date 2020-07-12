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

    componentWillReceiveProps = (nextProps) => {
        const currentProjectIndex = this.state.project;
        const filterProjects = nextProps.projects.filter(project => {
            return (project.projectName === currentProjectIndex);
        });
        const selectedProject = filterProjects && filterProjects.length> 0 && filterProjects[0]
        this.setState({
            project: selectedProject.projectName,
            selectedProject: selectedProject
        });
    }

    getprojectById = id => {
        const project = this.props.projects.filter(project => parseInt(project.projectId) === parseInt(id));
        return project[0];
    }

    handleProjectDDChange = (event) => {
        const { target } = event;
        const value = target.value;
        this.updateProjectDetails(value);
    }

    updateProjectDetails = index => {
        const selectedProject = this.props.projects[parseInt(index)];
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
        return (manager && manager.length > 0 && manager[0]) || null;
    }
    render() {
        const { 
            project,
            projectDropdownOpen,
            selectedProject
        } = this.state;
        const {
            projects,
            employees,
            onProjectUpdate
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
                            <ProjectDetails project={selectedProject} projectManager={this.getProjectManager(selectedProject.projectManager)} employees={employees} onProjectUpdate={onProjectUpdate} />
                        </Col>
                    </Row>
                ) : null}
                
            </Container>
        );
    }
}