import React, { Component } from 'react';
import EmployeeRegistration from '../employeeRegistration/EmployeeRegistration';
import Employees from '../employees/Employees';
import { Container, Row, Col } from 'reactstrap';

export default class Employee extends Component {
    constructor(props) {
      super(props);
      this.state = {
        employees: [],
        projects: [],
        nextEmployeeId: -1
      }
      this.setNextEmployeeId = this.setNextEmployeeId.bind(this);
    }
  
    componentDidMount() {
      this.getEmployees();
      this.getProjects();
    }
  
    getEmployees = () => {
      fetch('http://localhost:8082/api/employees', {
        method: 'get',
        mode: 'cors'
      }).then(response => response.json())
      .then(data => {
        if(data && data.employees) {
          this.setState({nextEmployeeId: data.employees.length + 1});
          this.setState({employees: data.employees});
        }
      }).catch(function (err) {
        console.log(err);
        console.warn('Could not find an employees');
      });;
    }
  
    getProjects = () => {
      fetch('http://localhost:8081/api/projects', {
        method: 'get',
        mode: 'cors'
      }).then(response => response.json())
      .then(data => {
        if(data && data.projects)
          this.setState({projects: data.projects});
        console.log(data)
      }).catch(function (err) {
        console.log(err);
        console.warn('Could not find an projects');
      });;
    }
  
  
    setNextEmployeeId = () => {
      this.setState({
        nextEmployeeId: this.state.employees && this.state.employees.length > -1 && this.state.employees.length
      })
    }
  
    render() {
      const {
        employees,
        projects,
        nextEmployeeId
      } = this.state;
      return (
        <Container>
          <Row className="mt-5">
            <Col xs="12" sm="6">
              <EmployeeRegistration onEmployeeSave={this.getEmployees} nextEmployeeId={nextEmployeeId} projects={projects} />
            </Col>
            {employees && employees.length > 0 ? (
              <Col xs="12" sm="6">
                <Employees onEmployeeLoad={this.setNextEmployeeId} employeeOptions={employees} projects={projects} />
              </Col>
            ) : null}
          </Row>
        </Container>
      )
    }
  }