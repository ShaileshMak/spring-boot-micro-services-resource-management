import React, { Component } from 'react';
import {
  Container, Row, Col, Form,
  FormGroup, Label, Input,
  Button, Alert,
  Dropdown, DropdownToggle, DropdownMenu, DropdownItem
} from 'reactstrap';

export default class ProjectRegistration extends Component {
  constructor(props) {
    super(props);
      this.state = {
        projectName: '',
        projectDes: '',
        projectManager: 'Choose Project Manager',
        pmDropdownOpen: false,
        projectStatus: 'Choose STATUS',
        dropdownOpen: false,
        selectedProjectManager: null,
        projectStatusOptions: ['Complete', 'WIP']
    }
    this.handleChange = this.handleChange.bind(this);
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

  handleProjectManagerDDChange = (event) => {
    const { target } = event;
    const value = target.value;
    const selectedProjectManager = this.props.employees[parseInt(value) - 1]
    selectedProjectManager && this.setState({
      projectManager: `${selectedProjectManager.firstName} ${selectedProjectManager.lastName}`,
      selectedProjectManager: selectedProjectManager
    });
  }

  toggle = () => this.setState({dropdownOpen : !this.state.dropdownOpen});
  PMtoggle = () => this.setState({pmDropdownOpen : !this.state.pmDropdownOpen});

  handleProjectSave = (event) => {
    event.preventDefault();
    const data = {
        projectName: this.state.projectName,
        projectDes: this.state.projectDes,
        projectManager: this.state.selectedProjectManager && this.state.selectedProjectManager.employeeId,
        projectStatus: this.state.projectStatus
    };
    fetch('http://localhost:8083/api/projects/project', {
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
      this.props.onProjectSave();
    }).catch(function (err) {
      console.log(err);
      console.warn('Could not able ot add project');
    });
  }

  resetForm = () => {
    this.setState({
      projectName: '',
      projectDes: '',
      projectManager: 'Choose Project Manager',
      projectStatus: 'Choose Status'
    });
  }

  render() {
    const {
      projectName,
      projectDes,
      projectManager,
      projectStatus,
      dropdownOpen,
      pmDropdownOpen,
      successMessage,
      projectStatusOptions
    } = this.state;
    const projectManagerOptions = this.props.employees;
    return (
      <Container className="App">
        <Row>
          <Col>
            <h2 className="mb-3">Register Project</h2>
            {successMessage ? ( 
              <>
                <Alert color="success">
                  Project added successfully !!!
                </Alert>
              </>
            ): null}
            <Form className="form" onSubmit={this.handleProjectSave}>
              <Col>
                <FormGroup>
                  <Label className="float-left font-weight-bold">Project Name</Label>
                  <Input
                    type="text"
                    name="projectName"
                    value={projectName}
                    id="projectName"
                    onChange={this.handleChange}
                  />
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Label className="float-left font-weight-bold">Project Description</Label>
                  <Input
                    type="textarea"
                    rows={5}
                    name="projectDes"
                    value={projectDes}
                    id="projectDes"
                    onChange={this.handleChange}
                />
                                    
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Label className="float-left font-weight-bold">Project Manager</Label><br />
                  <Dropdown
                    isOpen={pmDropdownOpen}
                    toggle={this.PMtoggle}
                    id="projectManager"
                    name="projectManager"
                    value={projectManager}
                  >
                    <DropdownToggle style={{width: "100%", textAlign: "left"}} caret>
                      {projectManager}
                    </DropdownToggle>
                    <DropdownMenu className="col-12">
                      {projectManagerOptions && projectManagerOptions.map((pm,index) => <DropdownItem key={`projectManagerSelect${index}`} onClick={this.handleProjectManagerDDChange} name="projectManager" value={index + 1}>{pm.firstName} {pm.lastName}</DropdownItem>)}
                    </DropdownMenu>
                  </Dropdown>
                </FormGroup>
              </Col>
              <Col>
                <FormGroup>
                  <Label className="float-left font-weight-bold">Project Status</Label><br />
                  <Dropdown
                    isOpen={dropdownOpen} 
                    toggle={this.toggle}
                    id="projectStatus"
                    name="projectStatus"
                    value={projectStatus}
                  >
                    <DropdownToggle style={{width: "100%", textAlign: "left"}} caret>
                      {projectStatus}
                    </DropdownToggle>
                    <DropdownMenu className="col-12">
                      {projectStatusOptions && projectStatusOptions.map((ps,index) => <DropdownItem key={`projectStatusSelect${index}`} onClick={this.handleDDChange} name="projectStatus" value={index + 1}>{ps}</DropdownItem>)}
                    </DropdownMenu>
                  </Dropdown>
                </FormGroup>
              </Col>
              <Button color="primary" size="lg" style={{width: "150px"}}>Save</Button>
            </Form>
          </Col>
        </Row>
      </Container>
    );
  }
}
