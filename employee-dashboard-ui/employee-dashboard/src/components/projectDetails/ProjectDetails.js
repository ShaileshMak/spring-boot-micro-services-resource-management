import React, { Component } from "react";
import { Container, Row, Col } from "reactstrap";
import { Alert, Button, Modal, ModalHeader, ModalBody, ModalFooter, FormGroup, Label, Dropdown, DropdownItem, DropdownMenu, DropdownToggle } from 'reactstrap';

export default class ProjectDetails extends Component {
    constructor(props) {
        super(props)
        this.state = {
            isModalOpen: false,
            projectManager: 'Choose Project Manager',
            projectManagerObj: null,
            projectManagerDropdownOpen: false,
            successMessage: false,
            unmountOnClose: false,
            employees: [],
            selectedEmployee: null
        }
    }

    componentDidMount = () => {
        const { 
            employees,
            projectManager
         } = this.props;
        this.setState({
            employees: employees,
            projectManagerObj: projectManager
        })
    };

    componentWillReceiveProps = (nextProps) => {
        const { 
            employees,
            projectManager
         } = nextProps;
        this.setState({
            employees: employees,
            projectManagerObj: projectManager
        })
    }

    toggle = () => this.setState({isModalOpen: !this.state.isModalOpen});

    projectManagerToggle = () => this.setState({projectManagerDropdownOpen: !this.state.projectManagerDropdownOpen});

    handleProjectManagerDDChange = (employee) => this.setState({
        projectManager: `${employee.firstName} ${employee.lastName}`,
        selectedEmployee: employee
    });

    getProjectManager = managerId => {
        const manager = this.props.employees.filter(employee => parseInt(employee.employeeId) === parseInt(managerId));
        return manager[0];
    }

    assignPrjectManager = () => {
        const projectId = this.props.project.id;
        const employeeId =this.state.selectedEmployee.employeeId;
        fetch(`http://localhost:8081/api/projects/manager/${projectId}/${employeeId}`, {
            method: 'put',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            }
            }).then(response => response.json())
            .then(data => {
                const manager = this.getProjectManager(data.projectManager);
                this.setState({
                    successMessage: true,
                    projectManagerObj: manager,
                    projectManagerName: `${manager.firstName} ${manager.lastName}`
                });
                setTimeout(() => {
                    this.setState({
                        successMessage: false
                    });
                    this.toggle();
                    this.props.onProjectUpdate();
                }, 500);
                this.props.onProjectSave();
            }).catch(function (err) {
            console.log(err);
            console.warn('Could not able ot add project');
        });
    }

    render() {
        let projectName = '';
        let projectDes = '';
        let projectStatus = '';
        if (this.props.project) {
            projectName = this.props.project.projectName;
            projectDes = this.props.project.projectDes;
            projectStatus = this.props.project.projectStatus;
        }
        const { 
            isModalOpen,
            projectManagerDropdownOpen,
            projectManager,
            successMessage,
            unmountOnClose,
            employees,
            selectedEmployee,
            projectManagerObj
        } = this.state;
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
                            {projectManagerObj ? (
                                <span><strong>Project Manager: </strong>{projectManagerObj.firstName} {projectManagerObj.lastName}</span>) : (
                                    <>
                                        <strong>Project Manager: </strong>
                                        <Button color="primary" onClick={this.toggle}>Assign Project Manager</Button>
                                        <Modal isOpen={isModalOpen} toggle={this.toggle} unmountOnClose={unmountOnClose}>
                                            <ModalHeader toggle={this.toggle}>Assign Manager to {projectName}</ModalHeader>
                                            <ModalBody>
                                                {successMessage ? ( 
                                                    <>
                                                        <Alert color="success">
                                                            Project Manager {selectedEmployee.firstName} {selectedEmployee.lastName} assigned successfully !!!
                                                        </Alert>
                                                    </>
                                                ): null}
                                                <FormGroup>
                                                    <Label className="float-left font-weight-bold">Projects</Label><br />
                                                    <Dropdown
                                                    isOpen={projectManagerDropdownOpen}
                                                    toggle={this.projectManagerToggle}
                                                    id="project"
                                                    value={projectManager}
                                                    >
                                                    <DropdownToggle style={{width: "100%", textAlign: "left"}} caret>
                                                        {projectManager}
                                                    </DropdownToggle>
                                                    <DropdownMenu className="col-12">
                                                        {employees && employees.length > 0 && employees.map((employee,index) => <DropdownItem key={`projectManagerSelect${index}`} onClick={(e) => this.handleProjectManagerDDChange(employee)} name="projectManager" value={employee.id}>{employee.firstName} {employee.lastName}</DropdownItem>)}
                                                    </DropdownMenu>
                                                    </Dropdown>
                                                </FormGroup>
                                            </ModalBody>
                                            <ModalFooter>
                                                <Button color="primary" onClick={this.assignPrjectManager}>Save</Button>{' '}
                                                <Button color="secondary" onClick={this.toggle}>Cancel</Button>
                                            </ModalFooter>
                                        </Modal>
                                    </>
                                )}
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