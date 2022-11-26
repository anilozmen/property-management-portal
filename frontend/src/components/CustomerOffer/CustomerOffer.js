import axios from "axios";
import { Link } from "react-router-dom";
import { CUSTOMER } from "../../constants/roles";
import ProtectedComponent from "../ProtectedComponent/ProtectedComponent";
import "./CustomerOffer.css";

const CustomerOffer = ({ offer }) => {

  const handleDownloadRecipt = event => {
    event.preventDefault();

    axios.get(
      '/receipts',
      {
        params: {
          property_id: offer.propertyId
        },
        responseType: 'blob',
        headers: {
          Accept: "application/pdf"
        }
      }
    )
      .then(res => {
        const downloadLink = document.createElement('a');

        downloadLink.href = window.URL.createObjectURL(new Blob([res.data]));
        downloadLink.target = '_blank';
        downloadLink.style.display = 'none';
        downloadLink.download = 'receipt.pdf';
        document.body.appendChild(downloadLink);
        downloadLink.click();
      })
      .catch(error => {
        alert("Failed to download receipt!");
        console.log(error);
      })
  }

  return (
    <div className="box-comments">
      <ul className="list-comments">
        <li>
          <Link to={`/properties/${offer.propertyId}`}>
            <h4 className="comment-author">{offer.propertyName}</h4>
          </Link>

          <div className="row">
            <div className="col-md-3 comment-avatar">
              <img src={offer.image} alt="" />
            </div>
            <div className="col-md-3 comment-details">
              <span>
                <b>Property Status:</b> {offer.propertyStatus}
              </span>
              <span>
                <b>Listing Type:</b> {offer.propertyListingType}
              </span>
              <span>
                <b>Property Price:</b> {offer.propertyPrice}
              </span>
            </div>
            <div className="col-md-6">
              <span>
                <b>Offer:</b> {offer.amount}
              </span>
              <span>
                <b>Offered on:</b> {new Date(offer.date).toDateString()}
              </span>
              <span>
                <b>Status:</b>
                <span
                  style={{ display: "inline" }}
                  className={"offer-status " + offer.status.toLowerCase()}
                >
                  {offer.status}
                </span>
                <p className="comment-description">
                  <span>
                    <b>Message: </b>
                  </span>
                  {offer.message}
                </p>
              </span>
              <ProtectedComponent
                requiredRole={CUSTOMER}
                isPage={false}
                component={offer.status === 'APPROVED' &&
                  offer.propertyStatus === 'COMPLETED' &&
                  (<button onClick={handleDownloadRecipt} className="btn btn-b">Download Recipt</button>)}
              />
            </div>
          </div>
        </li>
      </ul>
    </div>
  );
};

export default CustomerOffer;
