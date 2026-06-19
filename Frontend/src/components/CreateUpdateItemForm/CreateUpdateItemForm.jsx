import { useEffect, useState } from "react";
import itemDefault from "../../images/item_default.png";
import styles from "./CreateUpdateItemForm.module.css";
import { itemService } from "../../services/itemService";

export function CreateUpdateItemForm({ editItem, isAdmin }) {
    const [name, setName] = useState(editItem?.name || "");
    const [description, setDescription] = useState(editItem?.description || "");
    const [weight, setWeight] = useState(editItem?.weight || 0);
    const [brand, setBrand] = useState(editItem?.brand || "");
    const [category, setCategory] = useState(editItem?.category || "");


    const [categories, setCategories] = useState([]);
    useEffect(() => {
        // TODO: Hacer fetch de categorias para el dropdown
        // y actualizar el estado con setCategories
    }, []);

    async function onSubmit(event) {
        event.preventDefault();

        // TODO: Validar ID de categoria
        if (!name || !description || !weight || !brand || !category) return;

        const result = editItem 
            ? await itemService.updateItem(editItem.id, { name, description, weight, brand, category })
            : await itemService.createItem({
                name,
                description,
                weight,
                brand,
                categoryId: 1, // TODO: Hacer dropdown de categorias
                userId: 1, // TODO: Eliminarlo y hacer que el backend lo asigne segun el token
                enabled: true, // TODO: Asociar el switch al estado
                suggested: !isAdmin
            });

        // TODO: Mostrar error en la interfaz
        if (!result) return;

        // TODO: Redirigir a la pagina del item creado/actualizado
        //if(isAdmin) navigate("/admin/items");
        //else navigate("/items");
    }

    return (
        <form className={styles.form} onSubmit={onSubmit}>
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

                {
                // TODO: Hacer dropdown de categorias en vez de input de texto usando el state categories
                }
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
                
                <label>Imagen</label>

                <label className={styles["upload-box"]}>
                    <input type="file" accept="image/*" id="imageInput" />
                    <span className={styles["upload-icon"]}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-upload" viewBox="0 0 16 16">
                            <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5" />
                            <path d="M7.646 1.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 2.707V11.5a.5.5 0 0 1-1 0V2.707L5.354 4.854a.5.5 0 1 1-.708-.708z" />
                        </svg>
                    </span>
                </label>

                {
                    isAdmin ? (
                        <div className={styles["toggle-container"]}>
                            <label className={styles["switch"]}>
                                <input type="checkbox" id="status" />
                                <span className={styles["slider"]}></span>
                                <span className={styles["slider-text"]} id="statusText">
                                    ACTIVO
                                </span>
                            </label>
                        </div>
                    ) : null
                }

                <button type="submit" className={styles["save-btn"]}>
                    {editItem ? "ACTUALIZAR" : "CREAR"}
                </button>
            </div>
        </form>
    );
}
