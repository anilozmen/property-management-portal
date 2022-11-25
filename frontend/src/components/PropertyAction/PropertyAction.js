import axios from "axios";
import { useDispatch } from "react-redux";
import { fetchOffersAsyncAction } from "../../reducers/offer";

const PropertyAction = ({ propertyId, onSuccess }) => {
  const dispatch = useDispatch();
  const sendAction = (action) => {
    axios
      .put(`/properties/${propertyId}/action`, action)
      .then((response) => {
        if (response.status === 200) {
          if (response.data.success) {
            onSuccess();
            dispatch(fetchOffersAsyncAction(propertyId, null, true));
          } else alert(response.data.message);
        } else {
          alert("API ERROR");
        }
      })
      .catch((e) => {
        alert("API Error.");
        console.log(e);
      });
  };

  const complete = () => sendAction({ action: "COMPLETE" });
  const cancel = () => sendAction({ action: "CANCEL" });

  return (
    <div className="float-right">
      <button className="btn btn-success" onClick={complete}>
        {" "}
        Completed{" "}
      </button>
      <button className="btn btn-danger" onClick={cancel}>
        {" "}
        Cancel Contingency{" "}
      </button>
    </div>
  );
};

export default PropertyAction;
