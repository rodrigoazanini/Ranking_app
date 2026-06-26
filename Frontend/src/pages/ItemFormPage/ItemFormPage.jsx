import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { CreateUpdateItemForm } from "../../components/CreateUpdateItemForm/CreateUpdateItemForm.jsx";
import styles from "../ItemFormPage/ItemFormPage.module.css";

export function ItemFormPage() {
    const { id } = useParams();
    const location = useLocation();

    useEffect(() => {
        const token = localStorage.getItem("token");
        const decodedJwt = token ? jwtDecode(token) : null;
    }, [location]);

    return (
        <main className={styles.page}>
            <CreateUpdateItemForm isAdmin={decodedJwt.Admin} ItemId={id} />
        </main>
    );
}
