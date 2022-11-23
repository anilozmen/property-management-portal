import Header from "../../components/Header/Header";
import React from 'react';
import PageRoutes from "./PageRoutes";
import Footer from "../../components/Footer/Footer";
import Layout from "../../components/Layout/Layout";

export default function Main() {
    return (
        <React.Fragment>
            <Header />
            <PageRoutes />
            <Layout />
            <Footer />
        </React.Fragment>
    )
};