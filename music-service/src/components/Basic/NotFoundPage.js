import React from 'react'
import { Link } from 'react-router-dom'
class NotFoundPage extends React.Component{
    render() {
        return(
            <div className={"flex flex-column gap-2"}>
                <h1>
                    <p>404 NOT FOUND</p>
                    <Link to={"/"}>Home</Link>
                </h1>
            </div>
        )
    }
}
export default NotFoundPage;