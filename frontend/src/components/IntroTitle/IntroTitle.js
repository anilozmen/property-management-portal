const IntroTitle = props => {

  return (
    <section className="intro-single">
      <div className="container">
        <div className="row">
          <div className="col-md-12 col-lg-8">
            <div className="title-single-box">
              <h1 className="title-single">{props.children}</h1>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default IntroTitle;
