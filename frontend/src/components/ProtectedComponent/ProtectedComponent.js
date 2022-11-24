import { useSelector } from "react-redux";
import NotFound from "../NotFound/NotFound";

const ProtectedComponent = ({ component, requiredRole }) => {
  const userRole = useSelector((state) => state.user.role);

  if (requiredRole === userRole) {
    return component;
  }

  return <NotFound />;
}

export default ProtectedComponent;
