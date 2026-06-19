import { CreateUpdateItemForm } from "../../components/CreateUpdateItemForm/CreateUpdateItemForm.jsx";
import styles from "./ItemsPage.module.css";

export function ItemsPage() {
    return (
        <main className={styles.page}>
            <CreateUpdateItemForm isAdmin={true} />
        </main>
    );
}
