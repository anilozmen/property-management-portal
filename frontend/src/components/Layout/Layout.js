
const ContentContainer = ({children}) => {

    return (
        <section className="contact mt-5">
            <div className="container">
                <div className="row">
                    <div className="col-sm-12 section-t8">
                        <div className="row">
                            <div className="col-md-12">
                                {children}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );

}


export default ContentContainer;