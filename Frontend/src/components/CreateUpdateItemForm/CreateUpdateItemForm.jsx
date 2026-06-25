import { useEffect, useState } from "react";
import styles from "./CreateUpdateItemForm.module.css";
import { itemService } from "../../services/itemService";
import { categoryService } from "../../services/categoryService";
import { uploadImageService } from "../../services/uploadImageService";

export function CreateUpdateItemForm({ editItem, isAdmin }) {
    const [name, setName] = useState(editItem?.name || "");
    const [description, setDescription] = useState(editItem?.description || "");
    const [weight, setWeight] = useState(editItem?.weight || 0);
    const [brand, setBrand] = useState(editItem?.brand || "");
    const [categoryId, setCategoryId] = useState(
        editItem?.categoryResponse?.id
            ? String(editItem.categoryResponse.id)
            : editItem?.categoryId
                ? String(editItem.categoryId)
                : ""
    );
    const [image, setImage] = useState("");
    const [enabled, setEnabled] = useState(editItem?.enabled ?? true);


    const [categories, setCategories] = useState([]);
    useEffect(() => {
        async function loadCategories() {
            try {
                const allCategories = await categoryService.getAllCategories();
                setCategories(allCategories);
            } catch (error) {
                console.error("Failed to load categories", error);
                setCategories([]);
            }
        }

        loadCategories();
    }, []);

    useEffect(() => {
        const currentCategoryId = editItem?.categoryResponse?.id ?? editItem?.categoryId ?? "";
        setCategoryId(currentCategoryId ? String(currentCategoryId) : "");
    }, [editItem]);

    async function onSubmit(event) {
        event.preventDefault();

        // TODO: Validar ID de categoria
        if (!name || !description || !weight || !brand || !categoryId || (!image && !editItem?.imageUrl)) return;

        let imageUrl = editItem?.imageUrl || "";

        if (image) {
            const formData = new FormData();
            formData.append("image", image);
            const uploadImage = await uploadImageService.uploadImage(formData);
            imageUrl = uploadImage?.imageUrl || uploadImage;
        }

        if (!imageUrl) return;

        const result = editItem
            ? await itemService.updateItem(editItem.id, {
                name,
                description,
                weight,
                brand,
                categoryId: Number(categoryId),
                enabled,
                imageUrl
            })
            : await itemService.createItem({
                name,
                description,
                weight,
                brand,
                categoryId: Number(categoryId),
                enabled,
                imageUrl,
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
                    // Category select
                }
                <div>
                    <label>Categoria</label>
                    <select
                        className={styles.input}
                        value={categoryId}
                        onChange={(e) => setCategoryId(e.target.value)}
                    >
                        <option value="" disabled>
                            Selecciona una categoria
                        </option>
                        {categories.map((category) => (
                            <option key={category.id} value={String(category.id)}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            <div className={styles["right-section"]}>
                <div className={styles["image-preview"]}>
                    <picture>
                        <source srcSet="/images/stock/item_default.avif" type="image/avif" />
                        <img id="preview" src="/images/stock/item_default.png" alt="Preview" />
                    </picture>
                </div>

                <label>Imagen</label>

                <label className={styles["upload-box"]}>
                    <input type="file" onChange={(e) => { setImage(e.target.files[0]) }} accept="image/*" id="imageInput" />
                    <span className={styles["upload-icon"]}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-upload" viewBox="0 0 16 16">
                            <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5" />
                            <path d="M7.646 1.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 2.707V11.5a.5.5 0 0 1-1 0V2.707L5.354 4.854a.5.5 0 1 1-.708-.708z" />
                        </svg>
                    </span>
                </label>

                {isAdmin ? (
                    <div className={styles.toggleContainer}>
                        <label className={styles.label}>
                            {enabled ? "ACTIVO" : "INACTIVO"}
                        </label>
                        <label className={styles.switch}>
                            <input
                                id={"status"}
                                type="checkbox"
                                checked={enabled}
                                onChange={(e) => setEnabled(e.target.checked)}
                            />
                            <span className={styles.track} aria-hidden="true">
                                <span className={styles.thumb}></span>
                            </span>
                        </label>
                    </div>
                ) : null}

                <button type="submit" className={styles["save-btn"]}>
                    {editItem ? "ACTUALIZAR" : "CREAR"}
                </button>
            </div>
        </form>
    );
}
