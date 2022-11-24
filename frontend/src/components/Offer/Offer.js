import axios from "axios";
import React, { useEffect, useState } from "react";
import ProtectedComponent from "../ProtectedComponent/ProtectedComponent";
import "./Offer.css";
import { OfferEntry } from "./OfferEntry";

export const changeStatus = (status, propertyId, offerId) => {
  return axios.put(`properties/${propertyId}/offers/${offerId}`, {
    status: status.toUpperCase(),
  });
};

const Offer = ({ propertyId }) => {
  const [offers, setOffers] = useState([]);

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

  useEffect(() => fetchOffers(), []);


  return (
    <React.Fragment>
        {/* <ProtectedComponent isPage={false} component={<button onClick={addOffer}>Send an offer</button>} />
        <ProtectedComponent isPage={false} component={<AddOffer propertyId={propertyId} />} /> */}
      {offers.map((o, i) => (
        <OfferEntry key={i} offer={o} propertyId={propertyId} />
      ))}
    </React.Fragment>
  );
};

export default Offer;
