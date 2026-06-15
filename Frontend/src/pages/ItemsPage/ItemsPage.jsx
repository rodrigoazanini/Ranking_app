import { itemService } from "../../services/itemService";
import { CreateUpdateItemForm } from "../../components/CreateUpdateItemForm/CreateUpdateItemForm.jsx";
import styles from "./ItemsPage.module.css";

export function ItemsPage() {
    async function createItem(item) {
        await itemService.createItem(item);
    }

    return (
        <main className={styles.page}>
            <CreateUpdateItemForm onSubmit={createItem} />
        </main>
    );
}
