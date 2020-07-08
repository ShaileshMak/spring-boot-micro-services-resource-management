import React, { Component } from "react";
import {
    Container, Row, Col,
    FormGroup, Dropdown, 
    DropdownToggle, DropdownMenu, 
    DropdownItem
  } from 'reactstrap';
import EmployeeDetails from "../employeeDetails/EmployeeDetails";

export default class Employees extends Component {
    constructor(props) {
        super(props);
        this.state = {
            employee: 'Choose Employee',
            employeeDropdownOpen: false,
            selectedEmployee: null,
        }
        this.handleEmployeeDDChange = this.handleEmployeeDDChange.bind(this);
    }

    getEmployeeById = id => {
        const employee = this.props.employeeOptions.filter(employee => parseInt(employee.employeeId) === parseInt(id));
        return employee[0];
    }

    handleEmployeeDDChange = (event) => {
      const { target } = event;
      const value = target.value;
      const { name } = target;
      const selectedEmployee = this.props[`${name}Options`][parseInt(value)];
      if(selectedEmployee.projectId) {
        const project = this.getProject(selectedEmployee.projectId);
        const manager = this.getEmployeeById(project.projectManager);
        selectedEmployee.projectManagerName = `${manager.firstName} ${manager.lastName}`; 
      } else {

      }
      selectedEmployee && this.setState({
        employee: `${selectedEmployee.firstName} ${selectedEmployee.lastName}`,
        selectedEmployee: selectedEmployee
      });
    }
    employeeToggle = () => this.setState({employeeDropdownOpen : !this.state.employeeDropdownOpen});

    getProject = projectId => {
        const project = this.props.projects.filter(project => parseInt(project.id) === parseInt(projectId));
        return project[0];
    }
    render() {
        const { 
            employee,
            employeeDropdownOpen,
            selectedEmployee
        } = this.state;
        const {
            employeeOptions,
            projects
        } = this.props;
        return(
            <Container>
                <Row>
                    <Col>
                        <h2>Registered Employees</h2>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <FormGroup>
                            <Dropdown
                                isOpen={employeeDropdownOpen}
                                toggle={this.employeeToggle}
                                id="employee"
                                value={selectedEmployee}
                            >
                                <DropdownToggle style={{width: "100%", textAlign: "left"}} caret>
                                {employee}
                                </DropdownToggle>
                                <DropdownMenu className="col-12">
                                {employeeOptions && employeeOptions.length > 0 && employeeOptions.map((employee,index) => <DropdownItem key={`employeeSelect${index}`} onClick={this.handleEmployeeDDChange} name="employee" value={index}>{employee.firstName} {employee.lastName}</DropdownItem>)}
                                </DropdownMenu>
                            </Dropdown>
                        </FormGroup>
                    </Col>
                </Row>
                {selectedEmployee ? (
                    <Row>
                        <Col>
                            <EmployeeDetails employees={employeeOptions} employee={selectedEmployee} projects={projects} project={this.getProject(selectedEmployee.projectId)}/>
                        </Col>
                    </Row>
                ) : null}
                
            </Container>
        );
    }
}