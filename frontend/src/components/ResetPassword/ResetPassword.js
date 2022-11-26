import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import Layout from '../Layout/Layout';
import axios from "axios";

const ResetPassword = () => {

  const formRef = useRef(null);
  const navigate = useNavigate();
  const [errorMessageState, setErrorMessageState] = useState(null);
  const [successMessageState, setSuccessMessageState] = useState(null);
  const [errorMessageVisibilityState, setErrorMessageVisibilityState] = useState(false);
  const [successMessageVisibilityState, setSuccessMessageVisibilityState] = useState(false);

  useEffect(() => {
    // document.title = "Reset Password";
  }, [errorMessageState, successMessageState]);


  const handleSubmit = (event) => {
    event.preventDefault();

    const form = formRef.current;

    if (!formRef.current)
      return;

    const formData = {
      email: form['email'].value,
    };


    axios.post('authenticate/reset-password', formData).then(response => {
      setSuccessMessageState("Please check your email for the link to reset your password");
      setSuccessMessageVisibilityState(true);
    }).catch(error => {
      setErrorMessageState(error.response.data.error.message);
      setErrorMessageVisibilityState(true);
    })

  }

  return (
    <Layout>
      <h1 className="mb-4">Reset Password</h1>
      <form className="form-a contactForm" onSubmit={handleSubmit} ref={formRef}>
        <div className="row">
          <div className="col-md-12 mb-3">
            <div className="form-group">
              <label htmlFor={'email'} className="form-label">Email Address: </label>
              <input id={'email'} name="email" type="email" className="form-control form-control-lg form-control-a" minLength={5} required />
            </div>
            <div id="sendmessage" style={{ display: successMessageVisibilityState ? 'block' : 'none' }}>{successMessageState}</div>
            <div id="errormessage" style={{ display: errorMessageVisibilityState ? 'block' : 'none' }}>{errorMessageState}</div>
          </div>
          <div className="col-md-12 text-right">
            <button type="submit" className="btn btn-a">Update Password</button>
          </div>
        </div>
      </form>
    </Layout>

  );
}


export default ResetPassword;