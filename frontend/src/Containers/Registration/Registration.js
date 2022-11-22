import './Registration.css';
import {useEffect, useRef} from "react";
import axios from "axios";

export default function Registration() {
    const formRef = useRef();

    useEffect(() => {
        document.title = "Registration Page";
    }, []);
    
    function submitRegistrationData(data) {
        axios.post('/register', data).then(response => {
            
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
            'first_name': form.fname.value,
            'last_name': form.lname.value,
            'address': {
                'street_address_1': form.street_address_1.value,
                'street_address_2': form.street_address_2.value,
                'city': form.city.value,
                'state': form.state.value,
                'zipcode': form.zipcode.value
            }
        }
        submitRegistrationData(data);
    }

    return (<form ref={formRef} className='form-registration'>
        <div className={'registration-screen'}>
            <label htmlFor={'email'}>Email</label>
            <input type={'email'} name={'email'}/>

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


            <div>
                <button onClick={onRegisterClick}>Submit</button>
            </div>

        </div>
    </form>);
}