import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './eshterak.reducer';
import { IEshterak } from 'app/shared/model/eshterak.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EshterakUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const eshterakEntity = useAppSelector(state => state.eshterak.entity);
  const loading = useAppSelector(state => state.eshterak.loading);
  const updating = useAppSelector(state => state.eshterak.updating);
  const updateSuccess = useAppSelector(state => state.eshterak.updateSuccess);

  const handleClose = () => {
    props.history.push('/eshterak' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...eshterakEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...eshterakEntity,
          type: 'MONTH',
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="magic40App.eshterak.home.createOrEditLabel" data-cy="EshterakCreateUpdateHeading">
            <Translate contentKey="magic40App.eshterak.home.createOrEditLabel">Create or edit a Eshterak</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="eshterak-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('magic40App.eshterak.text')} id="eshterak-text" name="text" data-cy="text" type="text" />
              <ValidatedField
                label={translate('magic40App.eshterak.amount')}
                id="eshterak-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField label={translate('magic40App.eshterak.until')} id="eshterak-until" name="until" data-cy="until" type="text" />
              <ValidatedField label={translate('magic40App.eshterak.type')} id="eshterak-type" name="type" data-cy="type" type="select">
                <option value="MONTH">{translate('magic40App.UntilType.MONTH')}</option>
                <option value="DAY">{translate('magic40App.UntilType.DAY')}</option>
                <option value="YEAR">{translate('magic40App.UntilType.YEAR')}</option>
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/eshterak" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EshterakUpdate;
