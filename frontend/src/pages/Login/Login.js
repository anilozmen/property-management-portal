import { useEffect, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";

import { loginAsyncAction } from "../../reducers/login";

function Login() {
  const formRef = useRef(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { isLoggingIn, error: loginError } = useSelector(state => state.login);

  useEffect(() => {
    document.title = "Login Page";
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
    <div>
      <h3>Login</h3>
      <form onSubmit={handleSubmit} ref={formRef}>
        <div>
          <label htmlFor="email">Email</label>
          <input id="email" name="email" type="email" minLength={5} required />
        </div>
        <div>
          <label htmlFor="password">Password</label>
          <input id="password" name="password" type="password" minLength={3} required />
        </div>
        {loginError ?
          <div>User email and password do not match!</div>
          : ''}
        <div>
          <button type="submit" disabled={isLoggingIn}>Login</button>
        </div>
        <Link to="reset-password">Forgot Password?</Link>
      </form>
    </div>
  );
}

export default Login;
