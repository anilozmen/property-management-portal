import {getAccessToken, getUserType} from '../../services/token';
import {useEffect, useMemo} from "react";
import CustomerDashBoard from "../../pages/CustomerDashBoard/CustomerDashBoard";
import OwnerDashBoard from "../../pages/OwnerDashBoard/OwnerDashBoard";

const Homepage = () => {

    const accessToken = useMemo(() => {
        return getAccessToken();
    }, [getAccessToken()]);


    const currentUserType = useMemo(() => () => {
        return getUserType();
    }, [getUserType()]);


    useEffect(() => {

    }, []);


    return (
        <section className="section-property section-t8">
            <div className="container">
                <div className="row">
                    <div className="col-md-12">
                        <div className="title-wrap d-flex justify-content-between">
                            <div className="title-box">
                                <h2 className="title-a">Homepage</h2>
                            </div>

                            {currentUserType() === "CUSTOMER" && <CustomerDashBoard/>}
                            {currentUserType() === "OWNER" && <OwnerDashBoard/>}
                            {currentUserType() == null && accessToken == null && <CustomerDashBoard/>}
                            
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}

export default Homepage;