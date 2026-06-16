import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { PublicRoute,/* PrivateRoute */} from "./routes";

import { LoginPage } from "./pages/LoginPage/LoginPage.jsx";
import { RegisterPage } from "./pages/RegisterPage/RegisterPage.jsx";



export function App () {
    return (
        <>
 <BrowserRouter>
                <Routes>

{/*publicas*/}
                    <Route element={<PublicRoute />}>
                        <Route path="/auth/login" element={<LoginPage />} />
                        <Route path="/auth/register" element={<RegisterPage />} />
                    </Route>
{/*privadas*/}




 <Route path="*" element={<Navigate to="/auth/login" />} />

 

                            </Routes>
                                </BrowserRouter>
</>
    );
}

