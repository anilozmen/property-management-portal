import React from "react";
import { OfferActions } from "./OfferActions";

export const OfferEntry = ({ offer, propertyId }) => {
  const { amount, message, propertyPrice, status } = offer;

  return (
    <>
      <div className="col-md-10 section-md-t3 offer-container">
        <div className="icon-box section-b2">
          <div className="icon-box-content">
            <span className={"offer-status " + status.toLowerCase()}>
              {status}
            </span>
            <div className="icon-box-content">
              <div className="row">
                <div className="col-md-3">Amount:</div>
                <div className="color-a col-md-9">{amount}</div>
              </div>
              <div className="row">
                <div className="col-md-3">Price:</div>
                <div className="color-a col-md-9">{propertyPrice}</div>
              </div>
              <div className="row">
                <div className="col-md-3">Message:</div>
                <div className="color-a col-md-9">{message}</div>
              </div>
            </div>
          </div>
        </div>
        <OfferActions offer={offer} propertyId={propertyId} propertyStatus={offer.propertyStatus} />
      </div>
      <hr />
    </>
  );
};
