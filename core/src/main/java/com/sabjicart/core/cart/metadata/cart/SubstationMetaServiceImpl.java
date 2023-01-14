
package com.sabjicart.core.cart.metadata.cart;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.metadata.cart.AddressInfo;
import com.sabjicart.api.messages.metadata.cart.CartPlateInfo;
import com.sabjicart.api.messages.metadata.cart.SubstationCartResponse;
import com.sabjicart.api.messages.metadata.cart.SubstationInfo;
import com.sabjicart.api.messages.metadata.cart.SubstationResponse;
import com.sabjicart.api.metadata.cart.SubstationMetaService;
import com.sabjicart.api.model.Address;
import com.sabjicart.api.model.Substation;
import com.sabjicart.api.model.SubstationCart;
import com.sabjicart.core.cart.repository.AddressRepository;
import com.sabjicart.core.cart.repository.SubstationCartRepository;
import com.sabjicart.core.cart.repository.SubstationRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SubstationMetaServiceImpl implements SubstationMetaService
{

    SubstationCartRepository substationCartRepository;
    SubstationRepository substationRepository;
    AddressRepository addressRepository;

    @Override
    public SubstationCartResponse getCarts (Long substationId)
        throws ServiceException
    {
        SubstationCartResponse substationCartResponse =
            new SubstationCartResponse();
        List<CartPlateInfo> cartPlateInfoList = new ArrayList<>();

        try {
            List<SubstationCart> substationCartList =
                substationCartRepository.findBySubstationIdAndActiveTrue(
                    substationId);
            for (SubstationCart substationCart : substationCartList) {
                CartPlateInfo cartPlateInfo = CartPlateInfo.builder()
                                                           .cartPlateNumber(
                                                               substationCart.getCartPlateNumber())
                                                           .cartDriverName(
                                                               substationCart.getCartDriverName())
                                                           .build();
                cartPlateInfoList.add(cartPlateInfo);
            }
            substationCartResponse.setCartPlateInfoList(cartPlateInfoList);

        }
        catch (Exception e) {
            log.error("Error getting substation cart by Id={}",
                substationId,
                e
            );
            throw new ServiceException("Error finding substation cart details",
                e
            );
        }
        ResponseUtil.success(substationCartResponse);
        return substationCartResponse;
    }

    @Override
    public SubstationResponse getSubstations () throws ServiceException
    {
        SubstationResponse substationResponse = new SubstationResponse();
        List<SubstationInfo> substationInfos = new ArrayList<>();

        try {
            List<Substation> substationList =
                substationRepository.findByActiveTrue();
            for (Substation substation : substationList) {
                Address address =
                    addressRepository.findBySubstationId(substation.getId())
                                     .orElseThrow(() -> new ServiceException(
                                         "Error finding address for substationId="
                                             + substation.getId()));
                AddressInfo addressInfo = new AddressInfo(address);
                SubstationInfo substationInfo = SubstationInfo.builder()
                                                              .substationId(
                                                                  substation.getId())
                                                              .substationName(
                                                                  substation.getSubstationName())
                                                              .addressInfo(
                                                                  addressInfo)
                                                              .build();
                substationInfos.add(substationInfo);
            }
            substationResponse.setSubstationInfoList(substationInfos);
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error("Error fetching substation details", e);
            throw new ServiceException("Error fetching substation details", e);
        }
        ResponseUtil.success(substationResponse);
        return substationResponse;

    }
}
