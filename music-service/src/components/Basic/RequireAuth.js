import { useLocation, Navigate, Outlet } from "react-router-dom";
import useAuth from "../Auth/hooks/useAuth";

const RequireAuth = ({ allowedRoles }) => {
    const { auth } = useAuth();
    const location = useLocation();
    const isAuthorized = auth?.roles?.some(role => allowedRoles?.includes(role));

    return (
        <>
            {isAuthorized ? (
                <Outlet />
            ) : auth?.user ? (
                <Navigate to="/unauthorized" state={{ from: location }} replace />
            ) : (
                <Navigate to="/login" state={{ from: location }} replace />
            )}
        </>
    );
}

export default RequireAuth;