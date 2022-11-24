import axios from "axios";
import React, { useRef, useState } from "react";
import Form from "../Form/Form";
import FormFieldWrapper from "../Form/FormFieldWrapper";
import TransitionsModal from "../Modal/Modal";

export const AddOffer = ({ propertyId }) => {
    const formRef = useRef();
    const [showModal, setShowModal] = useState(false);
    const handleOpen = () => setShowModal(true);
    const handleClose = () => setShowModal(false);

    const sendOffer = (e) => {
        e.preventDefault();
        const data = {
            amount: formRef.current.bidAmount.value,
            message: formRef.current.message.value,
        };
        console.log(data);
        axios
            .post(`properties/${propertyId}/offers`, data)
            .then((response) => {
                if (response.status != 200) {
                    alert("Could not send offer");
                } else if (!response.data.success) {
                    alert(response.data.message);
                }
            })
            .catch(() => alert("Could not send offer"));
    };

    return (
        <>
            <button onClick={handleOpen} className="btn btn-a">Send an offer</button>
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
                            required />
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
                            required />
                    </FormFieldWrapper>
                    <button type="submit" className="btn btn-a">
                        Send Offer
                    </button>
                </Form>
            </TransitionsModal>
        </>
    );
};
