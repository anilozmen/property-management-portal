import { useEffect, useRef } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import axios from "axios";

const ChangePassword = () => {

    const [searchParams] = useSearchParams();
    const formRef = useRef(null);
    const navigate = useNavigate();

    useEffect(() => {
        document.title = "Change Password";
    }, []);


    const handleSubmit = (event) => {
        event.preventDefault();

        const form = formRef.current;

        if (!formRef.current)
            return;

        const formData = {
            token: searchParams.get('changeToken'),
            oldPassword: form['old_password'].value,
            newPassword: form['password'].value,
        };

        axios.post('authenticate/change-password', formData).then(response => {
            navigate("/login")
        }).catch(error => {
            console.log(error.response.data);
            const errorData = error.response.data.error;
            const validationErrors = errorData.validationErrors;
            console.log(validationErrors);
            //todo: handle error properly .. show snackbar with proper error message, we might get from server.
            if (validationErrors) {
                alert(validationErrors);
            }
        })

    }

    return (
        <div>
            <h3>Change Password</h3>
            <form onSubmit={handleSubmit} ref={formRef}>
                <div>
                    <label htmlFor={'old_password'}>Old Password</label>
                    <input type={'password'} name={'old_password'} required />
                </div>
                <div>
                    <label htmlFor={'password'}>New Password</label>
                    <input type={'password'} name={'password'} required />
                </div>
                <div>
                    <button type="submit">Update Password</button>
                </div>
            </form>
        </div>
    );
}


export default ChangePassword;