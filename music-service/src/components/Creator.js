import React , { useState, useEffect } from 'react'
import Users from "./Users";


class Creator extends React.Component{
    render() {
        return(
            <div>

                <h1> Creator page </h1>
                <Users />

            </div>
        )
    }
}

export default Creator;