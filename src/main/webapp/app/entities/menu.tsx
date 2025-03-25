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
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
