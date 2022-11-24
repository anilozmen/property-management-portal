import React from "react";

const Form = React.forwardRef((props, ref) => {
  const { children, ...otherProps } = props;

  return (
    <form ref={ref} {...otherProps}>
      <div className="row">
        {children}
      </div>
    </form>
  );
});

export default Form;
