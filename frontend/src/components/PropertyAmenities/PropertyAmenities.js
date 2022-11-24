import './PropertyAmenities.css';

export default function PropertyAmenities({propertyAttributes}) {

    if (!propertyAttributes) {
        return <div>
            No property amenities listed yet !!
        </div>;
    }

    return (
        <div className="amenities-list color-text-a">
            <ul className="list-a no-margin">
                <li>Bedrooms: {propertyAttributes.noOfBedRooms}</li>
                <li>Has {propertyAttributes.hasGarage || "No"} Garage</li>
                <li>Has {propertyAttributes.hasParking || "No"} Parking</li>
                <li>Restrooms: {propertyAttributes.noOfRestrooms}</li>
                <li>Area: {propertyAttributes.area}</li>
            </ul>
        </div>
    );
}

function ListElementAmenity(type, value) {
    return (<li>{type} : {value}</li>);
}
