import Header from "../../components/Header/Header";
import React from 'react';
import PageRoutes from "./PageRoutes";
import Footer from "../../components/Footer/Footer";

export default function Main() {
    return (
        <React.Fragment>
            <Header />
            <PageRoutes />
            <Footer />
        </React.Fragment>
    )
};