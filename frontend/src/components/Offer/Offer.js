import axios from "axios";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { CUSTOMER } from "../../constants/roles";
import { fetchOffersAsyncAction } from "../../reducers/offer";
import ProtectedComponent from "../ProtectedComponent/ProtectedComponent";
import { AddOffer } from "./AddOffer";
import "./Offer.css";
import { OfferEntry } from "./OfferEntry";

const Offer = ({ propertyId, propertyStatus }) => {
  const { data: offers } = useSelector((state) => state.offer);

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchOffersAsyncAction(propertyId, null, false));
  }, []);

  const showAddButton = () => {
    console.log(propertyStatus);
    return (
      ["AVAILABLE", "PENDING"].includes(propertyStatus) &&
      offers.filter((o) => ["CREATED"].includes(o.status)).length === 0
    );
  };

  return (
    <React.Fragment>
      {showAddButton() && (
        <ProtectedComponent
          isPage={false}
          requiredRole={CUSTOMER}
          component={<AddOffer propertyId={propertyId} />}
        />
      )}
      {offers.map((o, i) => (
        <OfferEntry key={i} offer={o} propertyId={propertyId} />
      ))}
    </React.Fragment>
  );
};

export default Offer;
