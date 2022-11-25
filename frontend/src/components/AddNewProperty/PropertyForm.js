import { useRef } from "react";
import Form from "../Form/Form";
import FormFieldWrapper from "../Form/FormFieldWrapper";

const listingTypes = [
  ['Sale', 'SALE'],
  ['Rent', 'RENT']
];
const propertyTypes = [
  ['House', 'HOUSE'],
  ['Condo', 'CONDO'],
  ['Apartment', 'APARTMENT']
];
const yesNoOptions = [
  ['Yes', true],
  ['No', false]
];

const PropertyForm = props => {
  const formRef = useRef(null);

  const handleSubmit = event => {
    event.preventDefault();

    const form = formRef.current;

    if (!form)
      return;

    const formData = {
      name: form.name.value,
      description: form.description.value,
      address: {
        address1: form.address1.value,
        address2: form.address2.value,
        city: form.city.value,
        state: form.state.value,
        zipCode: form.zipCode.value
      },
      price: form.price.value,
      listingType: form.listingType.value,
      propertyType: form.propertyType.value,
      propertyAttributes: {
        area: form.area.value || null,
        noOfBedRooms: form.noOfBedRooms.value || null,
        noOfFloors: form.noOfFloors.value || null,
        noOfRestRooms: form.noOfRestRooms.value || null,
        hasGarage: form.hasGarage.value || null,
        hasParking: form.hasParking.value || null,
        hasLaundry: form.hasLaundry.value || null
      }
    };

    props.onSubmit(formData);
  }

  const data = (props.isUpdate && props.data)
    ? props.data
    : ({
      propertyAttributes: {},
      address: {}
    });

  return (
    <Form ref={formRef} className="form-a contactForm" onSubmit={handleSubmit}>
      <FormFieldWrapper>
        <label htmlFor="name" className="form-label">Name</label>
        <input id="name" name="name" type="text" className="form-control form-control-lg form-control-a" minLength={5} required defaultValue={data.name || ""} />
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label htmlFor="description" className="form-label">Description</label>
        <textarea id="description" name="description" className="form-control form-control-lg form-control-a" minLength={10} rows={3} required defaultValue={data.description || ""}></textarea>
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label htmlFor="address1" className="form-label">Address 1</label>
        <input id="address1" name="address1" type="text" className="form-control form-control-lg form-control-a" required defaultValue={data.address.address1 || ""} />
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label htmlFor="address1" className="form-label">Address 2</label>
        <input id="address2" name="address2" type="text" className="form-control form-control-lg form-control-a" defaultValue={data.address.address2 || ""} />
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label htmlFor="city" className="form-label">City</label>
        <input id="city" name="city" type="text" className="form-control form-control-lg form-control-a" required defaultValue={data.address.city || ""} />
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label htmlFor="state" className="form-label">State</label>
        <input id="state" name="state" type="text" className="form-control form-control-lg form-control-a" required defaultValue={data.address.state || ""} />
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label htmlFor="zipCode" className="form-label">Zip</label>
        <input id="zipCode" name="zipCode" type="number" className="form-control form-control-lg form-control-a" required defaultValue={data.address.zipCode || ""} />
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label htmlFor="price" className="form-label">Price</label>
        <input id="price" name="price" type="number" className="form-control form-control-lg form-control-a" min={0} step={0.01} required defaultValue={data.price || ""} />
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label className="form-label">Listing Type</label>
        <div className="row">
          {listingTypes.map((listingType, idx) => (
            <div key={idx} className="col-md-2">
              <label htmlFor={`listingType${listingType[0]}`}>
                <input id={`listingType${listingType[0]}`} name="listingType" type="radio" value={listingType[1]} className="form-control" required defaultChecked={data.listingType === listingType[1]} />
                <span>&ensp;{listingType[0]}&ensp;</span>
              </label>
            </div>
          ))}
        </div>
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label className="form-label">Property Type</label>
        <div className="row">
          {propertyTypes.map((propertyType, idx) => (
            <div key={idx} className="col-md-2">
              <label htmlFor={`propertyType${propertyType[0]}`}>
                <input id={`propertyType${propertyType[0]}`} name="propertyType" type="radio" value={propertyType[1]} className="form-control" required defaultChecked={data.propertyType === propertyType[1]} />
                <span>&ensp;{propertyType[0]}&ensp;</span>
              </label>
            </div>
          ))}
        </div>
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label htmlFor="area" className="form-label">Area</label>
        <input id="area" name="area" type="number" min={0} className="form-control form-control-lg form-control-a" defaultValue={data.propertyAttributes.area || ""} />
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label htmlFor="noOfBedRooms" className="form-label">No of Bed Rooms</label>
        <input id="noOfBedRooms" name="noOfBedRooms" type="number" min={0} className="form-control form-control-lg form-control-a" defaultValue={data.propertyAttributes.noOfBedRooms || ""} />
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label htmlFor="noOfFloors" className="form-label">No of Floors</label>
        <input id="noOfFloors" name="noOfFloors" type="number" min={0} className="form-control form-control-lg form-control-a" defaultValue={data.propertyAttributes.noOfFloors || ""} />
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label htmlFor="noOfRestRooms" className="form-label">No of Rest Rooms</label>
        <input id="noOfRestRooms" name="noOfRestRooms" type="number" min={0} className="form-control form-control-lg form-control-a" defaultValue={data.propertyAttributes.noOfRestRooms || ""} />
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label className="form-label">Has Garage</label>
        <div className="row">
          {yesNoOptions.map((yesNoOption, idx) => (
            <div key={idx} className="col-md-2">
              <label htmlFor={`hasGarage${yesNoOption[0]}`}>
                <input id={`hasGarage${yesNoOption[0]}`} name="hasGarage" type="radio" value={yesNoOption[1]} className="form-control" defaultChecked={data.propertyAttributes.hasGarage === yesNoOption[1]} />
                <span>&ensp;{yesNoOption[0]}&ensp;</span>
              </label>
            </div>
          ))}
        </div>
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label className="form-label">Has Parking</label>
        <div className="row">
          {yesNoOptions.map((yesNoOption, idx) => (
            <div key={idx} className="col-md-2">
              <label htmlFor={`hasParking${yesNoOption[0]}`}>
                <input id={`hasParking${yesNoOption[0]}`} name="hasParking" type="radio" value={yesNoOption[1]} className="form-control" defaultChecked={data.propertyAttributes.hasParking === yesNoOption[1]} />
                <span>&ensp;{yesNoOption[0]}&ensp;</span>
              </label>
            </div>
          ))}
        </div>
      </FormFieldWrapper>
      <FormFieldWrapper>
        <label className="form-label">Has Laundry</label>
        <div className="row">
          {yesNoOptions.map((yesNoOption, idx) => (
            <div key={idx} className="col-md-2">
              <label htmlFor={`hasLaundry${yesNoOption[0]}`}>
                <input id={`hasLaundry${yesNoOption[0]}`} name="hasLaundry" type="radio" value={yesNoOption[1]} className="form-control" defaultChecked={data.propertyAttributes.hasLaundry === yesNoOption[1]} />
                <span>&ensp;{yesNoOption[0]}&ensp;</span>
              </label>
            </div>
          ))}
        </div>
      </FormFieldWrapper>
      <div className="col-md-12 text-right mb-4">
        <button type="submit" className="btn btn-a">{props.isUpdate ? 'Update' : 'Add New'} Product</button>
      </div>
    </Form>
  );
}

export default PropertyForm;
