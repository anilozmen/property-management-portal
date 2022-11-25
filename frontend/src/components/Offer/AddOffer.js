import axios from "axios";
import React, { useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchOffersAsyncAction, setIsInProgress } from "../../reducers/offer";
import Form from "../Form/Form";
import FormFieldWrapper from "../Form/FormFieldWrapper";
import TransitionsModal from "../Modal/Modal";

export const AddOffer = ({ propertyId, onAdded }) => {
  const formRef = useRef();
  const [showModal, setShowModal] = useState(false);
  const handleOpen = () => setShowModal(true);
  const handleClose = () => setShowModal(false);

  const dispatch = useDispatch();
  const { isInProgress } = useSelector((s) => s.offer);

  const sendOffer = (e) => {
    dispatch(setIsInProgress(true));
    e.preventDefault();
    const data = {
      amount: formRef.current.bidAmount.value,
      message: formRef.current.message.value,
    };

    axios
      .post(`properties/${propertyId}/offers`, data)
      .then((response) => {
        if (response.status !== 200) {
          alert("API Error");
        } else if (!response.data.success) {
          alert(response.data.message);
        } else {
          dispatch(fetchOffersAsyncAction(propertyId, onAdded));
        }
      })
      .catch(() => alert("Could not send offer"))
      .finally(() => dispatch(setIsInProgress(false)));
  };

  return (
    <>
      <button onClick={handleOpen} className="btn btn-a">
        Send an offer
      </button>
      <TransitionsModal
        handleClose={handleClose}
        handleOpen={handleOpen}
        open={showModal}
      >
        <Form ref={formRef} className="form-a contactForm" onSubmit={sendOffer}>
          <FormFieldWrapper>
            <label htmlFor="bidAmount" className="form-label">
              Amount:
            </label>
            <input
              name="bidAmount"
              type="number"
              className="form-control form-control-lg form-control-a"
              required
            />
          </FormFieldWrapper>
          <FormFieldWrapper>
            <label htmlFor="message" className="form-label">
              Messages
            </label>
            <textarea
              name="message"
              type="number"
              className="form-control form-control-lg form-control-a"
              minLength={5}
              required
            />
          </FormFieldWrapper>
          <button type="submit" className="btn btn-a" disabled={isInProgress}>
            Send Offer
          </button>
        </Form>
      </TransitionsModal>
    </>
  );
};
