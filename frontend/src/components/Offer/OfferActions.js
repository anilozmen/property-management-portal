import axios from "axios";
import React from "react";
import { CUSTOMER, OWNER } from "../../constants/roles";
import ProtectedComponent from "../ProtectedComponent/ProtectedComponent";

export const OfferActions = ({ offer: { id, status }, propertyId }) => {
    const changeStatus = (status) => {
        return axios.put(`properties/${propertyId}/offers/${id}`, {
            status: status.toUpperCase(),
        });
    };

    const approve = () => changeStatus("approved", propertyId, id);
    const reject = () => changeStatus("rejected", propertyId, id);
    const cancel = () => changeStatus("cancelled", propertyId, id);

    if (status !== "CREATED")
        return null;
    return (
        <>
            <ProtectedComponent
                isPage={false}
                requiredRole={OWNER}
                component={<button
                    type="button"
                    className="btn btn-a btn-action"
                    onClick={approve}
                >
                    Approve
                </button>} />
            <ProtectedComponent
                isPage={false}
                requiredRole={OWNER}
                component={<button className="btn btn-b btn-action" onClick={reject}>
                    Reject
                </button>} />
            <ProtectedComponent
                isPage={false}
                requiredRole={CUSTOMER}
                component={<button className="btn btn-b btn-action" onClick={cancel}>
                    Cancel
                </button>} />
        </>
    );
};
