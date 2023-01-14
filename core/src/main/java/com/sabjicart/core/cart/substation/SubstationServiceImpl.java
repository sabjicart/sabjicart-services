
package com.sabjicart.core.cart.substation;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.model.Substation;
import com.sabjicart.api.substation.SubstationService;
import com.sabjicart.core.cart.repository.SubstationRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SubstationServiceImpl implements SubstationService
{

    SubstationRepository substationRepository;

    @Override
    public Substation getByName (String substationName) throws ServiceException
    {
        // Declaration
        Substation substation;

        try {
            substation = substationRepository.findBySubstationName(substationName);

            // validating whether substation with giveen name exist or not
            if (null == substation) {
                log.info("No substation found with the name: {}", substationName);
                throw new ServiceException("No substation found with the given name");
            }
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error("Error get substation by name={}", substationName, e);
            throw new ServiceException("Error finding substation details", e);
        }

        return substation;
    }
}
