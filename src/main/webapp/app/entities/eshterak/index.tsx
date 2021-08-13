import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Eshterak from './eshterak';
import EshterakDetail from './eshterak-detail';
import EshterakUpdate from './eshterak-update';
import EshterakDeleteDialog from './eshterak-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EshterakUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EshterakUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EshterakDetail} />
      <ErrorBoundaryRoute path={match.url} component={Eshterak} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EshterakDeleteDialog} />
  </>
);

export default Routes;
