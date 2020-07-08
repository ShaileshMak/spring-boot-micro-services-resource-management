import React from 'react';
import './App.css';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from 'react-router-dom'

import Home from './components/employee/Employee';
import Project from './components/project/Project';

export default function App() {
  return (
    <Router id="routerContainer">
      <div>
        <nav>
          <ul>
            <li className="link-li">
              <Link to="/">Employee</Link>
            </li>
            <li className="link-li">
              <Link to="/project">Project</Link>
            </li>
          </ul>
        </nav>

        {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
        <Switch>
          <Route path="/project">
            <Project />
          </Route>
          <Route path="/">
            <Home />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}
