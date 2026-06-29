import { useEffect, useState } from "react";
import styles from "./CreateUpdateItemForm.module.css";
import { itemService } from "../../services/itemService";
import { categoryService } from "../../services/categoryService";
import { uploadImageService } from "../../services/uploadImageService";

function getFileExtension(filename) {
    // Returns everything after the last dot, or an empty string if no dot exists
    return filename.slice(((filename.lastIndexOf(".") - 1) >>> 0) + 2);
}

export function CreateUpdateItemForm({ ItemId, isAdmin }) {
    const [editItem, setEditItem] = useState(null);
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [weight, setWeight] = useState(0);
    const [brand, setBrand] = useState("");
    const [previewUrl, setPreviewUrl] = useState("");
    const [previewType, setPreviewType] = useState("");
    const [categoryId, setCategoryId] = useState("");
    const [imageUrl, setImageUrl] = useState("");
    const [imageFile, setImageFile] = useState("");
    const [enabled, setEnabled] = useState(true);

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
        if (!ItemId) {
            setEditItem(null);
            setName("");
            setDescription("");
            setWeight(0);
            setBrand("");
            setCategoryId("");
            setImageUrl("");
            setImageFile("");
            setEnabled(true);
            setPreviewUrl("");
            setPreviewType("");
            return;
        }

        let isMounted = true;

        async function loadItem() {
            try {
                const itemSearch = await itemService.getItem(ItemId);
                if (!isMounted) return;

                setEditItem(itemSearch);
                setName(itemSearch?.name ?? "");
                setDescription(itemSearch?.description ?? "");
                setWeight(itemSearch?.weight ?? 0);
                setBrand(itemSearch?.brand ?? "");
                setEnabled(itemSearch?.enabled ?? true);
                setCategoryId(
                    itemSearch?.categoryResponse?.id
                        ? String(itemSearch.categoryResponse.id)
                        : itemSearch?.categoryId
                            ? String(itemSearch.categoryId)
                            : ""
                );
                setImageUrl(itemSearch?.imageUrl ?? "");
            } catch (error) {
                console.error("Failed to load item", error);
            }
        }

        loadItem();

        return () => {
            isMounted = false;
        };
    }, [ItemId]);

    useEffect(() => {
        if (!imageUrl) {
            setPreviewUrl("");
            setPreviewType("");
            return;
        }

        setPreviewUrl(imageUrl);
        setPreviewType(`image/${getFileExtension(imageUrl)}`);
    }, [imageUrl]);

    function handleImageSelection(event) {
        const file = event.target.files?.[0];
        if (!file) {
            setImageFile("");
            setPreviewUrl(imageUrl);
            setPreviewType(imageUrl ? `image/${getFileExtension(imageUrl)}` : "");
            return;
        }

        setImageFile(file);
        setPreviewUrl(URL.createObjectURL(file));
        setPreviewType(file.type || "image/*");
    }

    async function onSubmit(event) {
        event.preventDefault();

        // TODO: Validar ID de categoria
        if (!name || !description || !weight || !brand || !categoryId) return;

        let finalImageUrl = imageUrl;

        if (imageFile) {
            const formData = new FormData();
            formData.append("image", imageFile);
            const uploadImage = await uploadImageService.uploadImage(formData);

            if (!uploadImage?.imageUrl) {
                alert("algo salio mal");
                return;
            }

            finalImageUrl = uploadImage.imageUrl;
            setImageUrl(finalImageUrl);
        }

        if (!finalImageUrl) return;

        const result = editItem
            ? await itemService.updateItem(editItem.id, {
                name,
                description,
                weight,
                brand,
                categoryId: Number(categoryId),
                enabled,
                imageUrl: finalImageUrl
            })
            : await itemService.createItem({
                name,
                description,
                weight,
                brand,
                categoryId: Number(categoryId),
                enabled,
                imageUrl: finalImageUrl,
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
                        {previewType ? <source srcSet={previewUrl} type={previewType} /> : null}
                        <source srcSet="/images/stock/item_default.avif" type="image/avif" />
                        <img id="preview" src={previewUrl || "/images/stock/item_default.png"} alt="Preview" />
                    </picture>
                </div>

                <label>Imagen</label>

                <label className={styles["upload-box"]}>
                    <input
                        type="file"
                        onChange={handleImageSelection}
                        accept=".png,.jpg,.jpeg,.webp,.avif,image/png,image/jpeg,image/webp,image/avif"
                        id="imageInput"
                    />
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