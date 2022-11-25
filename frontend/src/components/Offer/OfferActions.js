import axios from "axios";
import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { CUSTOMER, OWNER } from "../../constants/roles";
import ProtectedComponent from "../ProtectedComponent/ProtectedComponent";
import { fetchOffersAsyncAction, setIsInProgress } from "../../reducers/offer";

export const OfferActions = ({
  offer: { id, status },
  propertyId,
  propertyStatus,
}) => {
  const dispatch = useDispatch();
  const { isInProgress } = useSelector((s) => s.offer);

  const changeStatus = (status) => {
    dispatch(setIsInProgress(true));
    return axios
      .put(`properties/${propertyId}/offers/${id}`, {
        status: status.toUpperCase(),
      })
      .then((response) => {
        if (response.status === 200 && response.data.success) {
          dispatch(fetchOffersAsyncAction(propertyId));
        }
      })
      .finally(() => dispatch(setIsInProgress(false)));
  };

  const approve = () => changeStatus("approved", propertyId, id);
  const reject = () => changeStatus("rejected", propertyId, id);
  const cancel = () => changeStatus("cancelled", propertyId, id);

  const showActions = ["AVAILABLE", "PENDING"].includes(propertyStatus);

  if (status !== "CREATED") return null;
  return (
    <>
      {showActions && (
        <ProtectedComponent
          isPage={false}
          requiredRole={OWNER}
          component={
            <button
              disabled={isInProgress}
              type="button"
              className="btn btn-a btn-action"
              onClick={approve}
            >
              Approve
            </button>
          }
        />
      )}
      {showActions && (
        <ProtectedComponent
          isPage={false}
          requiredRole={OWNER}
          component={
            <button
              disabled={isInProgress}
              className="btn btn-b btn-action"
              onClick={reject}
            >
              Reject
            </button>
          }
        />
      )}
      {showActions && (
        <ProtectedComponent
          isPage={false}
          requiredRole={CUSTOMER}
          component={
            <button
              disabled={isInProgress}
              className="btn btn-b btn-action"
              onClick={cancel}
            >
              Cancel
            </button>
          }
        />
      )}
    </>
  );
};
