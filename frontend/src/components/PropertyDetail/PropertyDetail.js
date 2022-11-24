import { useEffect, useMemo, useState } from "react";
import { useNavigate, useParams } from "react-router";
import Layout from '../Layout/Layout';
import { moneyFormat } from '../../services/helper';
import axios from "axios";
import Messages from "../Messages/Messages";
import { getUserType, getAccessToken } from '../../services/token';
import { OWNER } from "../../constants/roles";

const PropertyDetail = () => {

    const params = useParams();
    const navigate = useNavigate();
    const [propertyDetail, setPropertyDetail] = useState({});
    const userType = useMemo(() => getUserType(), [getUserType()]);
    const userLoggedIn = useMemo(() => () => {
        return getAccessToken() !== null;
    }, []);

    useEffect(
        () => {
            if (params.id) {
                axios.get('properties/' + params.id)
                    .then(response => {
                        setPropertyDetail(response.data)
                    })
                    .catch(err => console.log(err.message))
            }
        }, [params.id])


    let propertyDetailsDisplay = null;

    if (params.id) {

        propertyDetailsDisplay = (
            <Layout>
                <div className="title-single-box">
                    <h1 className="title-single">{propertyDetail.name}</h1>
                    <span className="color-text-a">City, State, Zipcode</span>
                </div>
                <section className="property-single nav-arrow-b">
                    <div className="container">
                        <div className="row">
                            <div className="col-sm-12">
                                <div className="owl-carousel owl-arrow gallery-property text-center">
                                    <div className="carousel-item-a row">
                                        <img className='col-sm-8' src="https://via.placeholder.com/700" alt="" />
                                        <div className='col-sm-4'>
                                            {userLoggedIn() && <Messages isOwner={userType === OWNER} propertyId={params.id} />}
                                        </div>
                                    </div>

                                </div>
                                <div className="row justify-content-between">
                                    <div className="col-md-5 col-lg-4">
                                        <div className="property-price d-flex justify-content-center foo">
                                            <div className="card-header-c d-flex">
                                                <div className="card-box-ico">
                                                    <h5 className="title-c">{moneyFormat(propertyDetail.price || 0.0)}</h5>
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
                                                            City, State, zipCode
                                                        </span>
                                                    </li>
                                                    <li className="d-flex justify-content-between">
                                                        <strong>Property Type:</strong>
                                                        <span>{propertyDetail.propertyType}</span>
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
                                        <div className="amenities-list color-text-a">
                                            <ul className="list-a no-margin">
                                                <li>Balcony</li>
                                                <li>Outdoor Kitchen</li>
                                                <li>Cable Tv</li>
                                                <li>Deck</li>
                                                <li>Tennis Courts</li>
                                                <li>Internet</li>
                                                <li>Parking</li>
                                                <li>Sun Room</li>
                                                <li>Concrete Flooring</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </Layout>
        );


    }

    return propertyDetailsDisplay;
};


export default PropertyDetail;