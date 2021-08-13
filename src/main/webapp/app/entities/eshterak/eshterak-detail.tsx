import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './eshterak.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EshterakDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const eshterakEntity = useAppSelector(state => state.eshterak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eshterakDetailsHeading">
          <Translate contentKey="magic40App.eshterak.detail.title">Eshterak</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{eshterakEntity.id}</dd>
          <dt>
            <span id="text">
              <Translate contentKey="magic40App.eshterak.text">Text</Translate>
            </span>
          </dt>
          <dd>{eshterakEntity.text}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="magic40App.eshterak.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{eshterakEntity.amount}</dd>
          <dt>
            <span id="until">
              <Translate contentKey="magic40App.eshterak.until">Until</Translate>
            </span>
          </dt>
          <dd>{eshterakEntity.until}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="magic40App.eshterak.type">Type</Translate>
            </span>
          </dt>
          <dd>{eshterakEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/eshterak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/eshterak/${eshterakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EshterakDetail;
