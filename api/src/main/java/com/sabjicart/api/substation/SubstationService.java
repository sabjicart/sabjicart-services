
package com.sabjicart.api.substation;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.model.Substation;

public interface SubstationService
{
    /**
     * Interface to get substation by name
     * @param substationName
     * @return
     */
    Substation getByName (String substationName) throws ServiceException;
}
