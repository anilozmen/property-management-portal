import ContentContainer from "../Layout/Layout";
import axios from 'axios';
import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router';
import {Link} from "react-router-dom";

const UserList = ({users = [], title='Users'}) => {

    const navigate = useNavigate();

    const [refreshFlagState, setRefreshFlagState] = useState(false);
    const [usersState, setUsersState] = useState(users);

    useEffect(() => {
        if (users.length !== 0) {
            return;
        }

        axios.get('/admin/users').then(response => {
            setUsersState(response.data);
        }).catch(error => {
            console.log(error.message);
            // alert('Something went wrong fetching admin information.');
        })
    }, [refreshFlagState]);

    const userEditHandler = (id, currentStatus) => {

        axios.put(`/admin/users/${id}`, currentStatus).then(response => {
            setRefreshFlagState(!refreshFlagState);

        }).catch(error => {
            console.log(error.message);
        })
    }

    return (
        <ContentContainer>
            <section className="intro-single p-2">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12 col-lg-8">
                            <div className="title-single-box">
                                <h1 className="title-single">{title}</h1>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <table className="table table-hover">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Phone</th>
                    <th scope="col">User Type</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                {usersState.map((item, index) => {
                    return <tr key={index}>
                        <th scope="row">{item.id}</th>
                        <td>{item.firstName}</td>
                        <td>{item.lastName}</td>
                        <td>{item.email}</td>
                        <td>{item.phoneNumber}</td>
                        <td>{item.userType}</td>
                        <td>

                            {item.userType === 'OWNER' && <Link
                                className="btn btn-sm btn-outline-info mr-2"
                                to={`/admin/users/${item.id}/properties`}
                                key={item.id}
                                id={item.id}>
                                <i className="fa fa-eye"></i> Properties
                            </Link>
                            }
                            {item.userType === 'OWNER' && !item.activated &&
                                <button className="btn btn-sm btn-outline-warning mr-2" onClick={() => {
                                    userEditHandler(item.id, {activated: !item.activated})
                                }}>
                                    <i className={item.activated ? 'fa fa-check' : 'fa fa-close'}></i> {item.activated ? 'Deactivate' : 'Activate'}
                                </button>
                            }
                            <button className="btn btn-sm btn-outline-danger" onClick={() => {
                                userEditHandler(item.id, {deleted: !item.deleted})
                            }}>
                                <i className={item.deleted ? 'fa fa-check' : 'fa fa-close'}></i> {item.deleted ? 'Rollback' : 'Remove'}
                            </button>
                        </td>
                    </tr>
                })}

                </tbody>
            </table>
        </ContentContainer>
    );
};

export default UserList;