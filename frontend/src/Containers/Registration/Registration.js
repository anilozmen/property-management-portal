import './Registration.css';
import {useEffect, useRef} from "react";
import axios from "axios";

export default function Registration() {
    const formRef = useRef();

    useEffect(() => {
        document.title = "Registration Page";
    }, []);

    function submitRegistrationData(data) {
        axios.post('authenticate/register', data).then(response => {

        }).catch(error => {
            //todo: handle error properly .. show snackbar with proper error message, we might get from server.
            console.log(error.message);
        })
    }

    function onRegisterClick(e) {
        e.preventDefault();
        const form = formRef.current;
        const data = {
            'email': form.email.value,
            'firstName': form.fname.value,
            'lastName': form.lname.value,
            'password': form.password.value,
            'phoneNumber': form.phoneNumber.value,
            'address': {
                'address1': form.street_address_1.value,
                'address2': form.street_address_2.value,
                'city': form.city.value,
                'state': form.state.value,
                'zipCode': form.zipcode.value
            }
        }
        submitRegistrationData(data);
    }

    return (<form ref={formRef} className='form-registration'>
        <div className={'registration-screen'}>
            
            <h1>Registration Page</h1>
            
            <label htmlFor={'email'}>Email</label>
            <input type={'email'} name={'email'}/>

            <label htmlFor={'password'}>Password</label>
            <input type={'password'} name={'password'}/>


            <label htmlFor={'fname'}>First Name</label>
            <input type={'text'} name={'fname'}/>

            <label htmlFor={'lname'}>Last Name</label>
            <input type={'text'} name={'lname'}/>

            <label htmlFor={'street_address'}>Street Address 1</label>
            <input type={'text'} name={'street_address_1'}/>

            <label htmlFor={'street_address'}>Street Address 2</label>
            <input type={'text'} name={'street_address_2'}/>

            <label htmlFor={'city'}>City</label>
            <input type={'text'} name={'city'}/>

            <label htmlFor={'state'}>State</label>
            <input type={'text'} name={'state'}/>

            <label htmlFor={'zipcode'}>Zip Code</label>
            <input type={'text'} name={'zipcode'}/>

            <label htmlFor={'phoneNumber'}>Phone Number</label>
            <input type={'text'} name={'phoneNumber'}/>


            <label htmlFor={'userType'}>Account Type</label>
            <select name={'userType'} title={'Account type'}>
                <option value={'customer'}>Customer</option>
                <option value={'owner'}>Owner</option>
            </select>

            <div className={'submit-button'}>
                <button onClick={onRegisterClick}>Submit</button>
            </div>

        </div>
    </form>);
}