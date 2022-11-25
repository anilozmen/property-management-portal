import { moneyFormat } from '../../services/helper';
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from 'react-redux';

import "../../pages/SavedProperties/SavedProperties.css";

import ProtectedComponent from "../ProtectedComponent/ProtectedComponent";
import { CUSTOMER } from '../../constants/roles';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import { addInSavedPropertyIdsAsync, deleteFromSavedPropertyIdsAsync } from '../../reducers/savedPropertyIdsForCustomer';

const Property = (props) => {
    const isSaved = useSelector(state => !!state.savedPropertyIds[props.id]);
    const propertyAttributes = props.propertyAttributes;
    const dispatch = useDispatch();

    const handleAddToSavedList = id => {
        if (isSaved) {
            return dispatch(deleteFromSavedPropertyIdsAsync(id, () => {
                if (typeof props.onRemoveFromSavedList === 'function') {
                    props.onRemoveFromSavedList();
                }
            }));
        }

        dispatch(addInSavedPropertyIdsAsync(id));
    }

    return (
        <div className="col-md-4">
            <div className="card-box-a card-shadow">
                <ProtectedComponent
                    requiredRole={CUSTOMER}
                    isPage={false}
                    component={
                        <div className={`customer-property-action ${isSaved ? 'saved' : ''}`} onClick={() => handleAddToSavedList(props.id)}>
                            <FontAwesomeIcon icon={faHeart} size="2x" />
                        </div>
                    }
                />
                <div className="img-box-a">
                    <img src="https://via.placeholder.com/700" alt="{props.name}" className="img-a img-fluid" />
                </div>
                <div className="card-overlay">
                    <div className="card-overlay-a-content">
                        <div className="card-header-a">
                            <h2 className="card-title-a">
                                <Link to={`/properties/${props.id}`} key={props.id} >{props.name}</Link>
                            </h2>
                        </div>
                        <div className="card-body-a">
                            <div className="price-box d-flex">
                                <span className="price-a">rent | {moneyFormat(props.price)}</span>
                            </div>
                            <Link to={`/properties/${props.id}`} key={props.id} className="link-a">
                                Click here to view
                                <span className="ion-ios-arrow-forward"></span>
                            </Link>

                        </div>
                        <div className="card-footer-a">
                            <ul className="card-info d-flex justify-content-around">
                                <li>
                                    <h4 className="card-info-title">Area</h4>
                                    <span>{propertyAttributes && propertyAttributes.area || "N/A"}</span>
                                </li>
                                <li>
                                    <h4 className="card-info-title">Beds</h4>
                                    <span>{propertyAttributes && propertyAttributes.noOfBedRooms || "N/A"}</span>
                                </li>
                                <li>
                                    <h4 className="card-info-title">Baths</h4>
                                    <span>{propertyAttributes && propertyAttributes.noOfRestrooms || "N/A"}</span>
                                </li>
                                <li>
                                    <h4 className="card-info-title">Garages</h4>
                                    <span>{propertyAttributes && propertyAttributes.hasGarage ? "Yes" : "No"}</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Property;