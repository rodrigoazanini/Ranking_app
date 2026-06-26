import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { CreateUpdateItemForm } from "../../components/CreateUpdateItemForm/CreateUpdateItemForm.jsx";
import styles from "../ItemFormPage/ItemFormPage.module.css";

export function ItemFormPage() {
    const { id } = useParams();
    const location = useLocation();
    const [isAdmin, setIsAdmin] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem("token");
        const decodedJwt = token ? jwtDecode(token) : null;
        const role = decodedJwt?.role || decodedJwt?.roles || decodedJwt?.isAdmin;

        if (typeof role === "string") {
            setIsAdmin(role.toLowerCase() === "admin");
            return;
        }

        setIsAdmin(Boolean(role));
    }, [location]);

    const effectiveIsAdmin = Boolean(location.state?.fromAdmin || isAdmin);

    return (
        <main className={styles.page}>
            <CreateUpdateItemForm isAdmin={effectiveIsAdmin} ItemId={id} />
        </main>
    );
}
