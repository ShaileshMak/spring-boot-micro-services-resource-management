import React, { Component } from 'react';
import ProjectRegistration from './../projectRegistration/ProjectRegistration';
import Projects from './../projects/Projects';
import { Container, Row, Col } from 'reactstrap';

export default class Project extends Component {
    constructor(props) {
      super(props);
      this.state = {
        employees: [],
        projects: []
      }
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
  
    render() {
      const {
        employees,
        projects
      } = this.state;
      return (
        <Container>
          <Row className="mt-5">
            <Col xs="12" sm="6">
              <ProjectRegistration onProjectSave={this.getProjects} employees={employees} />
            </Col>
            {projects && projects.length > 0 ? (
              <Col xs="12" sm="6">
                <Projects projects={projects} employees={employees} onProjectUpdate={this.getProjects} />
              </Col>
            ) : null}
          </Row>
        </Container>
      )
    }
  }