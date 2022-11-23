import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import Layout from '../Layout/Layout';
import axios from "axios";

const PropertyDetail = () => {

    const params = useParams();
    const navigate = useNavigate();
    const [propertyDetail, setPropertyDetail] = useState({});

    useEffect(
        () => {
            console.log(params.id)
            if (params.id) {
                // axios.get('http://localhost:8080/api/v1/products/' + params.id + '/reviews')
                //     .then(response => {
                //         setProductDetail(response.data)
                //     })
                //     .catch(err => console.log(err.message))
            }
        }, [params.id])


    let propertyDetailsDisplay = null;

    if (params.id) {

        propertyDetailsDisplay = (
            <Layout>
                <h1>asdasdas</h1>
            </Layout>
        );


    }

    return propertyDetailsDisplay;
};


export default PropertyDetail;