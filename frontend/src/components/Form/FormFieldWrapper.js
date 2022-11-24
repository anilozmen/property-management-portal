const FormFieldWrapper = (props) => {

  return (
    <div className="col-md-12 mb-3">
      <div className="form-group">
        {props.children}
      </div>
    </div>
  );
}

export default FormFieldWrapper;
