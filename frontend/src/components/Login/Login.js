import { useEffect, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { hasSessionData } from '../../services/token';

import { loginAsyncAction } from "../../reducers/login";

function Login() {
  const formRef = useRef(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { isLoggingIn, error: loginError } = useSelector(state => state.login);

  useEffect(() => {
    document.title = "Login";


    if (hasSessionData()) {
      navigate("/");
    }

  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();

    const form = formRef.current;

    if (!formRef.current)
      return;

    const loginData = {
      email: form['email'].value,
      password: form['password'].value
    };

    dispatch(loginAsyncAction(loginData, () => {
      navigate("/");
    }));
  }


  return (
    <section className="contact mt-5 mb-4">
      <div className="container">
        <div className="row">
          <div className="col-sm-12 section-t8">
            <div className="row">
              <div className="col-md-12">
                <h1 className="mb-4">Login</h1>
                <form className="form-a contactForm" onSubmit={handleSubmit} ref={formRef}>
                  <div className="row">
                    <div className="col-md-12 mb-3">
                      <div className="form-group">
                        <label htmlFor={'email'} className="form-label">Email Address: </label>
                        <input id={'email'} name="email" type="email" className="form-control form-control-lg form-control-a" minLength={5} required />
                      </div>
                    </div>
                    <div className="col-md-12 mb-3">
                      <div className="form-group">
                        <label htmlFor={'password'} className="form-label">Password: </label>
                        <input type="password" id={'password'} name="password" className="form-control form-control-lg form-control-a" minLength={3} required />
                      </div>
                    </div>

                    {loginError ?
                      <div>User email and password do not match!</div>
                      : ''}

                    <div className="col-md-12 text-right">
                      <button type="submit" className="btn btn-a" disabled={isLoggingIn}>Login</button>
                    </div>
                  </div>
                </form>
              </div>

            </div>
          </div>
        </div>
      </div>
    </section>

  );
}

export default Login;
