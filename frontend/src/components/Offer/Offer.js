import axios from "axios";
import React, { useEffect, useState } from "react";
import { CUSTOMER } from "../../constants/roles";
import ProtectedComponent from "../ProtectedComponent/ProtectedComponent";
import { AddOffer } from "./AddOffer";
import "./Offer.css";
import { OfferEntry } from "./OfferEntry";

const Offer = ({ propertyId }) => {
  const [offers, setOffers] = useState([]);
  useEffect(() => fetchOffers(), []);

  const fetchOffers = () => {
    axios
      .get(`/properties/${propertyId}/offers`)
      .then((response) => {
        setOffers(response.data);
      })
      .catch((error) => {
        console.log(error);
        alert("Couldn't fetch offers");
      });
  };

  return (
    <React.Fragment>
      {offers.filter((o) => o.status === "CREATED").length === 0 && (
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
