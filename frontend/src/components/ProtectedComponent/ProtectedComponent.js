import { useSelector } from "react-redux";
import NotFound from "../NotFound/NotFound";

const ProtectedComponent = ({
  component,
  requiredRole = null,
  requiredRoles = [],
  isPage = true,
}) => {
  const userRole = useSelector((state) => state.user.role);

  if (requiredRole) requiredRoles.push(requiredRole);

  if (requiredRoles.includes(userRole)) {
    return component;
  }

  return isPage ? <NotFound /> : null;
};

export default ProtectedComponent;
