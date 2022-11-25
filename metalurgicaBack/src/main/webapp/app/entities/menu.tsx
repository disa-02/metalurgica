import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/employee">
        <Translate contentKey="global.menu.entities.employee" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/admin">
        <Translate contentKey="global.menu.entities.admin" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/sales-person">
        <Translate contentKey="global.menu.entities.salesPerson" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/operator">
        <Translate contentKey="global.menu.entities.operator" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/notification">
        <Translate contentKey="global.menu.entities.notification" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/subscribe">
        <Translate contentKey="global.menu.entities.subscribe" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/sale">
        <Translate contentKey="global.menu.entities.sale" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/record">
        <Translate contentKey="global.menu.entities.record" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/roow">
        <Translate contentKey="global.menu.entities.roow" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        <Translate contentKey="global.menu.entities.product" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/made-of">
        <Translate contentKey="global.menu.entities.madeOf" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/raw-material">
        <Translate contentKey="global.menu.entities.rawMaterial" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
