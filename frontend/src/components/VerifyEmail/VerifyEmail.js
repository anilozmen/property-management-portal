import { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import Layout from '../Layout/Layout';
import axios from "axios";

const VerifyEmail = () => {

    const formRef = useRef(null);
    const navigate = useNavigate();


    useEffect(() => {
        // document.title = "Verify Email";
    }, []);

    const handleSubmit = (event) => {
        event.preventDefault();

        const form = formRef.current;

        if (!formRef.current)
            return;

        const formData = {
            email: form['email'].value,
            token: form['token'].value,
        };

        axios.post('authenticate/verify-email', formData).then(response => {
            navigate("/login");
        }).catch(error => {
            console.log(error.response.data.error.message);
        })
    }

    return (
        <Layout>
            <h1 className="mb-4">Verify Email</h1>
            <form className="form-a contactForm" onSubmit={handleSubmit} ref={formRef}>
                <div className="row">
                    <div className="col-md-12 mb-3">
                        <div className="form-group">
                            <label htmlFor={'email'} className="form-label">Email Address: </label>
                            <input id={'email'} name="email" type="email" className="form-control form-control-lg form-control-a" minLength={5} required />
                        </div>
                        <div className="form-group">
                            <label htmlFor={'token'} className="form-label">Token: </label>
                            <input id={'token'} name="token" type="text" className="form-control form-control-lg form-control-a" minLength={5} required />
                        </div>
                    </div>
                    <div className="col-md-12 text-right">
                        <button type="submit" className="btn btn-a">Update Password</button>
                    </div>
                </div>
            </form>
        </Layout>
    );
}
export default VerifyEmail;