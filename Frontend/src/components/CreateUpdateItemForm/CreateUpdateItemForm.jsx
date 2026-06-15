import { useState } from "react";
import itemDefault from "../../images/item_default.png";
import styles from "./CreateUpdateItemForm.module.css";

export function CreateUpdateItemForm({ onSubmit }) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [weight, setWeight] = useState(0);
    const [brand, setBrand] = useState("");
    const [category, setCategory] = useState("");

    async function createItem(event) {
        event.preventDefault();
        if (!name || !description || !weight || !brand || !category) return;
        onSubmit({ name, description, weight, brand, category });
        setName("");
        setDescription("");
        setWeight(0);
        setBrand("");
        setCategory("");
    }

    return (
        <form className={styles.form} onSubmit={createItem}>
            <div className={styles["left-section"]}>
                <div>
                    <label>Nombre</label>
                    <input
                        type="text"
                        className={styles.input}
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>

                <div>
                    <label>Descripcion</label>
                    <input type="text" className={styles.input}
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                </div>

                <div>
                    <label>Peso</label>
                    <input type="number" className={styles.input}
                        value={weight}
                        onChange={(e) => setWeight(parseFloat(e.target.value))} />
                </div>

                <div>
                    <label>Marca</label>
                    <input type="text" className={styles.input}
                        value={brand}
                        onChange={(e) => setBrand(e.target.value)}
                    />
                </div>

                <div>
                    <label>Categoria</label>
                    <input type="text" className={styles.input}
                        value={category}
                        onChange={(e) => setCategory(e.target.value)}
                    />
                </div>
            </div>

            <div className={styles["right-section"]}>
                <div className={styles["image-preview"]}>
                    <img id="preview" src={itemDefault} alt="Preview" />
                </div>

                <div className={styles["image-label"]}>Imagen</div>

                <label className={styles["upload-box"]}>
                    <input type="file" accept="image/*" id="imageInput" />
                    <span className={styles["upload-icon"]}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-upload" viewBox="0 0 16 16">
                            <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5" />
                            <path d="M7.646 1.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 2.707V11.5a.5.5 0 0 1-1 0V2.707L5.354 4.854a.5.5 0 1 1-.708-.708z" />
                        </svg>
                    </span>
                </label>

                <div className={styles["toggle-container"]}>
                    <label className={styles["switch"]}>
                        <input type="checkbox" id="status" />
                        <span className={styles["slider"]}></span>
                        <span className={styles["slider-text"]} id="statusText">
                            ACTIVO
                        </span>
                    </label>
                </div>

                <button type="submit" className={styles["save-btn"]}>
                    CREAR
                </button>
            </div>
        </form>
    );
}
