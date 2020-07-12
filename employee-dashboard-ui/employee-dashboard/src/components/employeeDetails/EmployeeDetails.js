import React, { Component } from "react";
import { Container, Row, Col } from "reactstrap";
import { Alert, Button, Modal, ModalHeader, ModalBody, ModalFooter, FormGroup, Label, Dropdown, DropdownItem, DropdownMenu, DropdownToggle } from 'reactstrap';

export default class EmployeeDetails extends Component {
    constructor(props) {
        super(props)
        this.state = {
            isModalOpen: false,
            project: 'Choose Project',
            projectDropdownOpen: false,
            successMessage: false,
            unmountOnClose: false,
            projectManagerName: '',
            selectedProject: {},
            updatedProject: {}
        }
    }

    projectToggle = () => this.setState({projectDropdownOpen : !this.state.projectDropdownOpen});
    handleProjectDDChange = (event) => {
        const { target } = event;
        const value = target.value;
        const selectedProject = this.props.projects[parseInt(value) - 1]
        selectedProject && this.setState({
            project: selectedProject.projectName,
            selectedProject: selectedProject
        });
    }
        
    toggle = () => {
        this.setState({
            isModalOpen: !this.state.isModalOpen
        })
    }

    getProjectManager = managerId => {
        const manager = this.props.employees.filter(employee => parseInt(employee.employeeId) === parseInt(managerId));
        return manager[0];
    }

    assignPrjectToEmployee = (e) => {
        const projectId = this.state.selectedProject.id;
        const id =this.props.employee.id;
        const data = {
            firstName: this.props.employee.firstName,
            lastName: this.props.employee.lastName,
            employeeId: this.props.employee.employeeId
        }
        fetch(`http://localhost:8081/api/projects/${projectId}/${id}`, {
            method: 'put',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
            }).then(response => response.json())
            .then(data => {
                const manager = this.getProjectManager(data.projectManager);
                this.setState({
                    successMessage: true,
                    selectedProject: data,
                    projectManagerName: `${manager.firstName} ${manager.lastName}`
                });
                setTimeout(() => {
                    this.setState({
                        updatedProject: data,
                        successMessage: false
                    });
                    this.toggle();
                }, 500);
                this.props.onProjectSave();
            }).catch(function (err) {
            console.log(err);
            console.warn('Could not able ot add project');
        });
    }

    render() {
        const { 
            isModalOpen,
            projectDropdownOpen,
            project,
            successMessage,
            unmountOnClose
        } = this.state;

        const {
            firstName,
            lastName,
            employeeId,
            dateOfBirth,
            lob,
            department
        } = this.props.employee;
        const projectName = (this.state.updatedProject && this.state.updatedProject.projectName) || (this.props.project && this.props.project.projectName) || '';
        const projectDes = (this.state.updatedProject && this.state.updatedProject.projectDes) || (this.props.project && this.props.project.projectDes) || '';
        const getProjectManagerName = () => {
            if (!this.props.project) return;
            const manager = this.getProjectManager(this.props.project.projectManager);
            const pmName = `${manager.firstName} ${manager.lastName}`;
            return this.state.projectManagerName || pmName || '';
        }
        const { projects } = this.props;
        return (
            <Container>
                <Row>
                    <Col>
                        <div>
                            <span><strong>Name: </strong>{firstName} {lastName}</span>
                        </div>
                        <div>
                            <span><strong>Employee Id: </strong>{employeeId}</span>
                        </div>
                        <div>
                            <span><strong>Date Of Birth: </strong>{dateOfBirth}</span>
                        </div>
                        <div>
                            <span><strong>LOB: </strong>{lob}</span>
                        </div>
                        <div>
                            <span><strong>Department: </strong>{department}</span>
                        </div>
                        {projectName ? (
                            <>
                                <div>
                                    <span><strong>Project: </strong>{projectName}</span>
                                </div>
                                <div>
                                    <span><strong>Project Description: </strong>{projectDes}</span>
                                </div>
                                <div>
                                    <span><strong>Project Manager: </strong>{getProjectManagerName()}</span>
                                </div>
                            </>
                        ) : (
                            <>
                                <div>
                                    <span><strong>Project: </strong><Button color="primary" onClick={this.toggle}>Assign Project</Button></span>
                                </div>
                                <Modal isOpen={isModalOpen} toggle={this.toggle} unmountOnClose={unmountOnClose}>
                                    <ModalHeader toggle={this.toggle}>Assign Project to {firstName} {lastName}</ModalHeader>
                                    <ModalBody>
                                        {successMessage ? ( 
                                            <>
                                                <Alert color="success">
                                                    Project asigned to {firstName} {lastName} successfully !!!
                                                </Alert>
                                            </>
                                        ): null}
                                        <FormGroup>
                                            <Label className="float-left font-weight-bold">Projects</Label><br />
                                            <Dropdown
                                            isOpen={projectDropdownOpen}
                                            toggle={this.projectToggle}
                                            id="project"
                                            value={project}
                                            >
                                            <DropdownToggle style={{width: "100%", textAlign: "left"}} caret>
                                                {project}
                                            </DropdownToggle>
                                            <DropdownMenu className="col-12">
                                                {projects && projects.length > 0 && projects.map((pj,index) => <DropdownItem key={`projectSelect${index}`} onClick={this.handleProjectDDChange} name="project" value={pj.id}>{pj.projectName}</DropdownItem>)}
                                            </DropdownMenu>
                                            </Dropdown>
                                        </FormGroup>
                                    </ModalBody>
                                    <ModalFooter>
                                        <Button color="primary" onClick={this.assignPrjectToEmployee}>Save</Button>{' '}
                                        <Button color="secondary" onClick={this.toggle}>Cancel</Button>
                                    </ModalFooter>
                                </Modal>
                            </>
                        )}
                    </Col>
                </Row>
            </Container>
        );
    }
}