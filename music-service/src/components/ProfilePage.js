import React, {useContext} from 'react'
import AuthContext from "./Auth/context/AuthProvider";
import {useNavigate} from "react-router-dom";
import Users from "./Users";

const ProfilePage = () => {
    const { setAuth } = useContext(AuthContext);
    const navigate = useNavigate();
    const logout = async () => {
        // if used in more components, this should be in context
        // axios to /logout endpoint
        setAuth({});
        navigate('/');
    }

        return(
            <div>
                <h1>
                    Profile
                </h1>
                <Users />
                <button onClick={logout}>Sign Out</button>


            </div>
        )

}

export default ProfilePage;