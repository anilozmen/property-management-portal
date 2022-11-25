import { useEffect, useMemo, useState } from "react";
import { useNavigate, useParams } from "react-router";
import Layout from "../Layout/Layout";
import { moneyFormat } from "../../services/helper";
import axios from "axios";
import Messages from "../Messages/Messages";
import { CUSTOMER, OWNER } from "../../constants/roles";
import { getUserType, getAccessToken } from "../../services/token";
import PropertyAmenities from "../PropertyAmenities/PropertyAmenities";
import ProtectedComponent from "../ProtectedComponent/ProtectedComponent";
import Offer from "../Offer/Offer";
import "./PropertyDetail.css";
import Tab from "../Tab/Tab";
import { useDispatch } from "react-redux";
import { setData } from "../../reducers/offer";

const PropertyDetail = () => {
  const params = useParams();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [propertyDetail, setPropertyDetail] = useState({});
  const userType = useMemo(() => getUserType(), [getUserType()]);
  const userLoggedIn = useMemo(
    () => () => {
      return getAccessToken() !== null;
    },
    []
  );

  useEffect(() => {
    fetchDetails();

    return () => dispatch(setData([]));
  }, [params.id]);

  const fetchDetails = () => {
    if (params.id) {
      axios
        .get("properties/" + params.id)
        .then((response) => {
          setPropertyDetail(response.data);
        })
        .catch((err) => console.log(err.message));
    }
  };

  let propertyDetailsDisplay = null;

  if (params.id) {
    propertyDetailsDisplay = (
      <Layout>
        <div className="title-single-box">
          <h1 className="title-single">{propertyDetail.name}</h1>
          {propertyDetail.address && (
            <span className="color-text-a">
              {propertyDetail.address.address1}{" "}
              {propertyDetail.address.address2}, {propertyDetail.address.city},{" "}
              {propertyDetail.address.state}, {propertyDetail.address.zipCode}
            </span>
          )}
          <span
            className={`offer-status ${
              propertyDetail.propertyStatus
                ? propertyDetail.propertyStatus.toLowerCase()
                : ""
            }`}
          >
            {propertyDetail.propertyStatus}
          </span>
        </div>
        <section className="property-single nav-arrow-b">
          <div className="container">
            <div className="row">
              <div className="col-sm-12">
                <div className="owl-carousel owl-arrow gallery-property text-center">
                  <div className="carousel-item-a row">
                    <div className={"property-image"}>
                      <img src="https://via.placeholder.com/700" alt="" />
                      <div className={"property-view-count"}>
                        {propertyDetail.viewCount} views
                      </div>
                    </div>
                    <div className="col-sm-4">
                      <ProtectedComponent
                        isPage={false}
                        requiredRoles={[CUSTOMER, OWNER]}
                        component={
                          <Tab
                            tabDetails={[
                              {
                                title: "Messages",
                                content: (
                                  <Messages
                                    isOwner={userType === OWNER}
                                    propertyId={params.id}
                                  />
                                ),
                              },
                              {
                                title: "Offer",
                                content: (
                                  <Offer
                                    propertyStatus={
                                      propertyDetail.propertyStatus
                                    }
                                    propertyId={params.id}
                                    fetchDetails={fetchDetails}
                                  />
                                ),
                              },
                            ]}
                          />
                        }
                      />
                    </div>
                  </div>
                </div>
                <div className="row justify-content-between">
                  <div className="col-md-5 col-lg-4">
                    <div className="property-price d-flex justify-content-center foo">
                      <div className="card-header-c d-flex">
                        <div className="card-box-ico">
                          <h5 className="title-c">
                            {moneyFormat(propertyDetail.price || 0.0)}
                          </h5>
                        </div>
                      </div>
                    </div>
                    <div className="property-summary">
                      <div className="row">
                        <div className="col-sm-12">
                          <div className="title-box-d section-t4">
                            <h3 className="title-d">Quick Summary</h3>
                          </div>
                        </div>
                      </div>
                      <div className="summary-list">
                        <ul className="list">
                          <li className="d-flex justify-content-between">
                            <strong>Location:</strong>
                            <span>
                              {propertyDetail.address && (
                                <div>
                                  {propertyDetail.address.address1}{" "}
                                  {propertyDetail.address.address2},{" "}
                                  {propertyDetail.address.city},{" "}
                                  {propertyDetail.address.state},{" "}
                                  {propertyDetail.address.zipCode}
                                </div>
                              )}
                            </span>
                          </li>
                          <li className="d-flex justify-content-between">
                            <strong>Property Type:</strong>
                            <span>{propertyDetail.propertyType}</span>
                          </li>
                          <li className="d-flex justify-content-between">
                            <strong>Listing Type:</strong>
                            <span>{propertyDetail.listingType}</span>
                          </li>
                          <li className="d-flex justify-content-between">
                            <strong>Status:</strong>
                            <span>{propertyDetail.propertyStatus}</span>
                          </li>
                        </ul>
                      </div>
                    </div>
                  </div>
                  <div className="col-md-7 col-lg-7 section-md-t3">
                    <div className="row">
                      <div className="col-sm-12">
                        <div className="title-box-d">
                          <h3 className="title-d">Property Description</h3>
                        </div>
                      </div>
                    </div>
                    <div className="property-description">
                      <p className="description color-text-a">
                        {propertyDetail.description}
                      </p>
                    </div>
                    <div className="row section-t3">
                      <div className="col-sm-12">
                        <div className="title-box-d">
                          <h3 className="title-d">Amenities</h3>
                        </div>
                      </div>
                    </div>
                    <PropertyAmenities
                      propertyAttributes={propertyDetail.propertyAttributes}
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </Layout>
    );
  }

  return <div>{propertyDetailsDisplay}</div>;
};

export default PropertyDetail;
