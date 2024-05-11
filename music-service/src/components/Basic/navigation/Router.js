import {createBrowserRouter, createRoutesFromElements, Route} from "react-router-dom";
import NotFoundPage from "../NotFoundPage";
import HomePage from "../../HomePage";
import Register from "../../Auth/Register";
import Login from "../../Auth/Login";
import Unauthorized from "../Unauthorized";
import RequireAuth from "../RequireAuth";
import Creator from "../../Creator";
import ProfilePage from "../../ProfilePage";
import React from "react";
import Layout from "../Layout";
import HeroPage from "../../heropage/HeroPage";
import Shop from "../../shop/Shop";
import ProductPage from "../../shop/ProductPage";


const router = createBrowserRouter(
    createRoutesFromElements(
        <Route element={<Layout />} errorElement={<NotFoundPage/>}>

            <Route path="/" element={<HeroPage />} />
            <Route path="/register" element={<Register />} />
            <Route path="/login" element={<Login />} />
            <Route path="/shop" element={<Shop />} />
            <Route path="/shop/product/:id" element={<ProductPage />} />
            <Route path="/unauthorized" element={<Unauthorized />} />

            <Route element={<RequireAuth allowedRoles={[1]} />}>
                {/*    Protected   */}
                <Route path={"/creator"} element={<Creator />}> </Route>
            </Route>
            <Route element={<RequireAuth allowedRoles={[2]} />}>

                {/*    Protected   */}
                <Route path={"/profile"} element={<ProfilePage />}> </Route>
            </Route>
        </Route>
    )
);
export default router;