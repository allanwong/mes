/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 1.2.0
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.mes.deliveriesToMaterialFlow.hooks;

import static com.qcadoo.mes.deliveriesToMaterialFlow.constants.DeliveryFieldsDTMF.LOCATION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.mes.basic.ParameterService;
import com.qcadoo.mes.materialFlowResources.MaterialFlowResourcesService;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;

@Service
public class DeliveryHooksDTMF {

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private MaterialFlowResourcesService materialFlowResourcesService;

    public void setLocationDefaultValue(final DataDefinition deliveryDD, final Entity delivery) {
        Entity location = delivery.getBelongsToField(LOCATION);

        if (location == null) {
            delivery.setField(LOCATION, parameterService.getParameter().getBelongsToField(LOCATION));
        }
    }

    public boolean checkIfLocationIsWarehouse(final DataDefinition deliveryDD, final Entity delivery) {
        Entity location = delivery.getBelongsToField(LOCATION);

        if ((location != null) && !materialFlowResourcesService.isLocationIsWarehouse(location)) {
            delivery.addError(deliveryDD.getField(LOCATION), "delivery.validate.global.error.locationIsNotWarehouse");
            return false;
        }
        return true;
    }

}
