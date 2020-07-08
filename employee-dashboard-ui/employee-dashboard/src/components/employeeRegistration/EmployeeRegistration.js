import React, { Component } from 'react';
import {
  Container, Row, Col, Form,
  FormGroup, Label, Input,
  Button, Alert,
  Dropdown, DropdownToggle, DropdownMenu, DropdownItem
} from 'reactstrap';

class EmployeeRegistration extends Component {
  constructor(props) {
    super(props);
      this.state = {
        employeeId: '',
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        salary: '',
        age: '',
        lob: 'Choose LOB',
        department: 'Choose Department',
        project: 'Choose Assigned Project',
        dropdownOpen: false,
        successMessage: false,
        projectDropdownOpen: false,
        selectedProject: {},
        depDropdownOpen: false,
        lobOptions: ['CIB', 'CTC', 'GTA', 'CORE', 'LOC'],
        departmentOptions: ['Development', 'Graphics', 'BSA', 'Testing', 'Management']
    }
    this.handleChange = this.handleChange.bind(this);
    this.handleDDChange = this.handleDDChange.bind(this);
    this.handleEmployeeSave = this.handleEmployeeSave.bind(this);
  }

  handleChange = (event) => {
    const { target } = event;
    const value = target.value;
    const { name } = target;
    this.setState({
      [ name ]: value
    });
  }

  handleDDChange = (event) => {
    const { target } = event;
    const value = target.value;
    const { name } = target;
    this.setState({
      [ name ]: this.state[`${name}Options`][parseInt(value) - 1]
    });
  }

  handleProjectDDChange = (event) => {
    const { target } = event;
    const value = target.value;
    const selectedProject = this.props.projects[parseInt(value) - 1]
    selectedProject && this.setState({
      project: selectedProject.projectName,
      selectedProject: selectedProject
    });
  }

  toggle = () => this.setState({dropdownOpen : !this.state.dropdownOpen});
  depToggle = () => this.setState({depDropdownOpen : !this.state.depDropdownOpen});
  projectToggle = () => this.setState({projectDropdownOpen : !this.state.projectDropdownOpen});

  handleEmployeeSave = (event) => {
    event.preventDefault();
    const data = {
        employeeId: this.props.nextEmployeeId,
        firstName: this.state.firstName,
        lastName: this.state.lastName,
        dateOfBirth: this.state.dateOfBirth,
        salary: this.state.salary,
        age: this.state.age,
        lob: this.state.lob,
        department: this.state.department
    };
    if (this.state.selectedProject && this.state.selectedProject.id)
      data.projectId = this.state.selectedProject.id.toString();
    fetch('http://localhost:8081/api/employees/employee', {
      method: 'post',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    }).then(response => response.json())
    .then(data => {
      this.setState({
        successMessage: true
      })
      this.resetForm()
      setTimeout(() => {
        this.setState({
          successMessage: false
        })
      }, 3000);
      this.props.onEmployeeSave();
    }).catch(function (err) {
      console.log(err);
      console.warn('Could not able ot add Employee');
    });;
  }

  resetForm = () => {
    this.setState({
      firstName: '',
      lastName: '',
      dateOfBirth: '',
      salary: '',
      age: '',
      lob: 'Choose LOB',
      department: 'Choose Department',
      project: 'Choose Assigned Project',
      selectedProject: null
    });
  }

  render() {
    const {
      firstName,
      lastName,
      dateOfBirth,
      salary,
      age,
      lob,
      dropdownOpen,
      department,
      depDropdownOpen,
      project,
      projectDropdownOpen,
      successMessage,
      lobOptions,
      departmentOptions
    } = this.state;
    const { projects } = this.props;
    return (
      <Container className="App">
        <Row>
          <Col>
            <h2 className="mb-3">Register Employee</h2>
            {successMessage ? ( 
              <>
                <Alert color="success">
                  Employee added successfully !!!
                </Alert>
              </>
            ): null}
            <Form className="form" onSubmit={this.handleEmployeeSave}>
              <Col>
                <FormGroup>
                  <Label className="float-left font-weight-bold">First Name</Label>
                  <Input
                    type="text"
                    name="firstName"
                    value={firstName}
                    id="firstName"
                    onChange={this.handleChange}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Label className="float-left font-weight-bold">Last Name</Label>
                  <Input
                    type="text"
                    name="lastName"
                    value={lastName}
                    id="lastName"
                    onChange={this.handleChange}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Label className="float-left font-weight-bold">Date Of Birth</Label>
                  <Input
                    type="date"
                    name="dateOfBirth"
                    value={dateOfBirth}
                    id="dateOfBirth"
                    onChange={this.handleChange}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Label className="float-left font-weight-bold">Salary</Label>
                  <Input
                    type="text"
                    name="salary"
                    value={salary}
                    id="salary"
                    onChange={this.handleChange}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Label className="float-left font-weight-bold">Age</Label>
                  <Input
                    type="text"
                    name="age"
                    value={age}
                    id="age"
                    onChange={this.handleChange}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Label className="float-left font-weight-bold">LOB</Label><br />
                  <Dropdown
                    isOpen={dropdownOpen} 
                    onChange={this.handleChange}
                    toggle={this.toggle}
                    id="lob"
                    name="lob"
                    value={lob}
                  >
                    <DropdownToggle style={{width: "100%", textAlign: "left"}} caret>
                      {lob}
                    </DropdownToggle>
                    <DropdownMenu className="col-12">
                      {lobOptions.map((lob,index) => <DropdownItem key={`lobSelect${index}`} onClick={this.handleDDChange} name="lob" value={index + 1}>{lob}</DropdownItem>)}
                    </DropdownMenu>
                  </Dropdown>
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Label className="float-left font-weight-bold">Department</Label><br />
                  <Dropdown
                    isOpen={depDropdownOpen}
                    toggle={this.depToggle}
                    id="department"
                    value={department}
                  >
                    <DropdownToggle style={{width: "100%", textAlign: "left"}} caret>
                      {department}
                    </DropdownToggle>
                    <DropdownMenu className="col-12">
                      {departmentOptions.map((department,index) => <DropdownItem key={`departmentSelect${index}`} onClick={this.handleDDChange} name="department" value={index + 1}>{department}</DropdownItem>)}
                    </DropdownMenu>
                  </Dropdown>
                </FormGroup>
              </Col>
              {projects && projects.length > 0 ? (
                <Col>
                  <FormGroup>
                    <Label className="float-left font-weight-bold">Assigned Project</Label><br />
                    <Dropdown
                      isOpen={projectDropdownOpen}
                      toggle={this.projectToggle}
                      id="project"
                      value={department}
                    >
                      <DropdownToggle style={{width: "100%", textAlign: "left"}} caret>
                        {project}
                      </DropdownToggle>
                      <DropdownMenu className="col-12">
                        {projects && projects.length > 0 && projects.map((project,index) => <DropdownItem key={`projectSelect${index}`} onClick={this.handleProjectDDChange} name="project" value={project.id}>{project.projectName}</DropdownItem>)}
                      </DropdownMenu>
                    </Dropdown>
                  </FormGroup>
                </Col>
                ) : null}
              <Button color="primary" size="lg" style={{width: "150px"}}>Save</Button>
            </Form>
          </Col>
        </Row>
      </Container>
    );
  }
}

export default EmployeeRegistration;
