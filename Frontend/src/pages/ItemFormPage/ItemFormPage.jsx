import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import { CreateUpdateItemForm } from "../../components/CreateUpdateItemForm/CreateUpdateItemForm.jsx";
import { getRole } from "../../services/apiService";
import styles from "../ItemFormPage/ItemFormPage.module.css";

export function ItemFormPage() {
    const { id } = useParams();
    const location = useLocation();
    const [isAdmin, setIsAdmin] = useState(false);

    useEffect(() => {
        const role = getRole();
        setIsAdmin(typeof role === "string" ? role.toLowerCase() === "admin" : Boolean(role));
    }, [location]);

    const effectiveIsAdmin = Boolean(location.state?.fromAdmin || isAdmin);

    return (
        <main className={styles.page}>
            <CreateUpdateItemForm isAdmin={effectiveIsAdmin} ItemId={id} />
        </main>
    );
}
