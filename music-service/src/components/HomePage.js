import React , { useState, useEffect } from 'react'
import useAuth from "./Auth/hooks/useAuth";

const HomePage = () => {
        const { auth } = useAuth();
        console.log(auth);

        return(
            <div>

                <h1> HOME PAGE </h1>

            </div>
        )

}

export default HomePage;