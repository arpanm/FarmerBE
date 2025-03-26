import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/session-context">
        <Translate contentKey="global.menu.entities.sessionContext" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/farmer">
        <Translate contentKey="global.menu.entities.farmer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/address">
        <Translate contentKey="global.menu.entities.address" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/document">
        <Translate contentKey="global.menu.entities.document" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/otp">
        <Translate contentKey="global.menu.entities.otp" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pan-details">
        <Translate contentKey="global.menu.entities.panDetails" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/terms-and-condition">
        <Translate contentKey="global.menu.entities.termsAndCondition" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/farm">
        <Translate contentKey="global.menu.entities.farm" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/category">
        <Translate contentKey="global.menu.entities.category" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/crop">
        <Translate contentKey="global.menu.entities.crop" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/accessories">
        <Translate contentKey="global.menu.entities.accessories" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bank-details">
        <Translate contentKey="global.menu.entities.bankDetails" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/demand">
        <Translate contentKey="global.menu.entities.demand" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/price">
        <Translate contentKey="global.menu.entities.price" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/hervest-plan">
        <Translate contentKey="global.menu.entities.hervestPlan" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/supply-confirmation">
        <Translate contentKey="global.menu.entities.supplyConfirmation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pick-up-confirmation">
        <Translate contentKey="global.menu.entities.pickUpConfirmation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pickup-gradation">
        <Translate contentKey="global.menu.entities.pickupGradation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pickup-payment">
        <Translate contentKey="global.menu.entities.pickupPayment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/banner-content">
        <Translate contentKey="global.menu.entities.bannerContent" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/carousel-content">
        <Translate contentKey="global.menu.entities.carouselContent" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/hervest-plan-rule">
        <Translate contentKey="global.menu.entities.hervestPlanRule" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/collection-center">
        <Translate contentKey="global.menu.entities.collectionCenter" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/location-mapping">
        <Translate contentKey="global.menu.entities.locationMapping" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/demand-data">
        <Translate contentKey="global.menu.entities.demandData" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/demand-data-file">
        <Translate contentKey="global.menu.entities.demandDataFile" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
