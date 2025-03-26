import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Alert, Col, Row } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9">
        <h1 className="display-4">
          <Translate contentKey="home.title">Welcome to Farmer App!</Translate>
        </h1>
        <p className="lead">
          <Translate contentKey="home.subtitle">Your complete farm management solution</Translate>
        </p>
        {account?.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                Welcome back, {account.login}! Manage your farms and crops efficiently.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.authenticated.prefix">To get started </Translate>
              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
              <Translate contentKey="global.messages.info.authenticated.suffix">
                , you can use your registered credentials or contact support for assistance.
              </Translate>
            </Alert>

            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount">New to Farmer App?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register as a farmer</Translate>
              </Link>
            </Alert>
          </div>
        )}
        <h2>
          <Translate contentKey="home.features.title">Key Features</Translate>
        </h2>
        <Row>
          <Col md="6">
            <h3>
              <Translate contentKey="home.onboarding.title">Easy Onboarding</Translate>
            </h3>
            <p>
              <Translate contentKey="home.onboarding.description">
                Quick and simple registration process for farmers with support for multiple languages.
              </Translate>
            </p>
            <h3>
              <Translate contentKey="home.farms.title">Farm Management</Translate>
            </h3>
            <p>
              <Translate contentKey="home.farms.description">
                Add and manage multiple farms under your profile with detailed tracking.
              </Translate>
            </p>

            <h3>
              <Translate contentKey="home.crops.title">Crop Management</Translate>
            </h3>
            <p>
              <Translate contentKey="home.crops.description">Track different crops for each farm with detailed information.</Translate>
            </p>
          </Col>
          <Col md="6">
            <h3>
              <Translate contentKey="home.demand.title">Market Demand & Pricing</Translate>
            </h3>
            <p>
              <Translate contentKey="home.demand.description">View daily demand and price updates for your crops in real-time.</Translate>
            </p>

            <h3>
              <Translate contentKey="home.harvest.title">Harvest Planning</Translate>
            </h3>
            <p>
              <Translate contentKey="home.harvest.description">Create and manage harvest plans for each crop with scheduling.</Translate>
            </p>

            <h3>
              <Translate contentKey="home.pickup.title">Pickup & Payments</Translate>
            </h3>
            <p>
              <Translate contentKey="home.pickup.description">
                Track pickup confirmations, gradation reports, and receive timely payments.
              </Translate>
            </p>
          </Col>
        </Row>

        <h2>
          <Translate contentKey="home.platform.title">Platform Features</Translate>
        </h2>
        <ul>
          <li>
            <Link to="/admin/metrics" className="feature-link">
              <Translate contentKey="home.link.monitoring">Application Monitoring</Translate>
            </Link>
          </li>
          <li>
            <Link to="/admin/user-management" className="feature-link">
              <Translate contentKey="home.link.usermanagement">User Management</Translate>
            </Link>
          </li>
          <li>
            <Link to="/language" className="feature-link">
              <Translate contentKey="home.link.language">Language Settings</Translate>
            </Link>
          </li>
          <li>
            <Link to="/docs" className="feature-link">
              <Translate contentKey="home.link.documentation">Documentation</Translate>
            </Link>
          </li>
        </ul>
      </Col>
    </Row>
  );
};

export default Home;
