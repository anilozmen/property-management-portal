import { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const ResetPassword = () => {

    const formRef = useRef(null);
    const navigate = useNavigate();

    useEffect(() => {
        document.title = "Reset Password";
      }, []);


  const handleSubmit = (event) => {
    event.preventDefault();

    const form = formRef.current;

    if (!formRef.current)
      return;

    const formData = {
      email: form['email'].value,
    };


    axios.post('authenticate/reset-password', formData).then(response => {
        navigate(`/change-password?changeToken=${response.data.token}`);
    }).catch(error => {
        console.log(error.response.data);
    })

  }

    return (
        <div>
          <h3>Reset Password</h3>
          <form onSubmit={handleSubmit} ref={formRef}>
            <div>
              <label htmlFor="email">Email</label>
              <input id="email" name="email" type="email" minLength={5} required />
            </div>

            <div>
              <button type="submit">Update Password</button>
            </div>
          </form>
        </div>
      );
}


export default ResetPassword;