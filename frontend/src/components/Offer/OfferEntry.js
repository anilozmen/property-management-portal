import React from "react";
import { getUserType } from "../../services/token";
import { changeStatus } from "./Offer";

const OwnerOfferActions = ({ offer: { id, status }, propertyId }) => {
    const approve = () => {
        changeStatus("approved", propertyId, id);
    };
    const reject = () => changeStatus("rejected", propertyId, id);

    if (status !== "CREATED")
        return null;

    return (
        <React.Fragment>
            <button type="button" className="btn btn-b btn-action" onClick={reject}>
                Reject
            </button>
            <button type="button" className="btn btn-a btn-action" onClick={approve}>
                Approve
            </button>
        </React.Fragment>
    );
};
const CustomerOfferActions = ({ offer: { id, status }, propertyId }) => {
    if (status !== "CREATED")
        return null;

    const cancel = () => {
        changeStatus("cancelled", propertyId, id);
    };

    return (
        <React.Fragment>
            <button className="btn btn-a" onClick={cancel}>
                Cancel
            </button>
        </React.Fragment>
    );
};
export const OfferEntry = ({ offer, propertyId }) => {
    const { amount, message, propertyPrice, status } = offer;

    let actions;

    if (getUserType() === "OWNER") {
        actions = (
            <OwnerOfferActions
                offer={offer}
                propertyId={propertyId}
            ></OwnerOfferActions>
        );
    } else {
        actions = (
            <CustomerOfferActions
                offer={offer}
                propertyId={propertyId}
            ></CustomerOfferActions>
        );
    }

    return (
        <React.Fragment>
            <div className="col-md-5 section-md-t3 offer-container">
                <div className="icon-box section-b2">
                    <div className="icon-box-content table-cell">
                        <div className="icon-box-title">
                            <h4 className="icon-title">Say Hello</h4>
                        </div>
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
                {actions}
            </div>
            <hr />
        </React.Fragment>
    );
};
