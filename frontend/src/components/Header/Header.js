import { Link, NavLink } from 'react-router-dom';
import { hasSessionData, removeTokens } from '../../services/token';
import { useNavigate } from 'react-router';
import { useDispatch, useSelector } from 'react-redux';
import { setRole } from '../../reducers/user';
import { CUSTOMER, OWNER, ADMIN } from '../../constants/roles';

const Header = () => {
    const userRole = useSelector(state => state.user.role);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const logoutHandler = (e) => {
        e.preventDefault();
        removeTokens();
        dispatch(setRole());
        navigate('/login');
    }

    return (
        <nav className="navbar navbar-default navbar-trans navbar-expand-lg fixed-top">
            <div className="container">
                <button className="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarDefault"
                    aria-controls="navbarDefault" aria-expanded="false" aria-label="Toggle navigation">
                    <span></span>
                    <span></span>
                    <span></span>
                </button>

                <Link className="navbar-brand text-brand" to="/">Property<span className="color-b">Management</span></Link>
                <button type="button" className="btn btn-link nav-search navbar-toggle-box-collapse d-md-none" data-toggle="collapse"
                    data-target="#navbarTogglerDemo01" aria-expanded="false">
                    <span className="fa fa-search" aria-hidden="true"></span>
                </button>
                <div className="navbar-collapse collapse justify-content-center" id="navbarDefault">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <NavLink end className='nav-link' to="/properties">Properties</NavLink>
                        </li>

                        {userRole === ADMIN && (
                            <li className="nav-item">
                                <NavLink end className='nav-link' to="/admin">Dashboard</NavLink>
                            </li>
                        )}

                        {userRole === OWNER && (
                            <li className="nav-item">
                                <NavLink end className='nav-link' to="/properties/new">Add Property</NavLink>
                            </li>
                        )}
                        {userRole === CUSTOMER && (
                            <li className="nav-item">
                                <NavLink end className='nav-link' to="/properties/saved">Saved Properties</NavLink>
                            </li>
                        )}
                        {userRole === CUSTOMER && (
                            <li className="nav-item">
                                <NavLink end className='nav-link' to="/offers">My Offers</NavLink>
                            </li>
                        )}

                        {userRole === ADMIN && (
                            <li className="nav-item">
                                <NavLink end className='nav-link' to="/admin/users">Users</NavLink>
                            </li>
                        )}

                        {!hasSessionData() && <li className="nav-item">
                            <Link className='nav-link' to="/login">
                                <i className="fa fa-sign-in" aria-hidden="true"></i> Login
                            </Link>
                        </li>}
                        {!hasSessionData() && <li className="nav-item">
                            <Link className='nav-link' to="/register">
                                <i className="fa fa-user-plus" aria-hidden="true"></i> Register
                            </Link>
                        </li>}
                        {hasSessionData() &&
                            <li className="nav-item">
                                <a className="nav-link" onClick={logoutHandler} href="">
                                    <i className="fa fa-sign-out" aria-hidden="true"></i>
                                    Logout
                                </a>
                            </li>
                        }
                    </ul>
                </div>
            </div>
        </nav>
    )

}


export default Header;