import { useEffect, useState } from "react";
import styles from "./AdminPage.module.css";
import Btn from "../../components/Btn/Btn";
import { mock_items } from "../../data/mockData";
import { useNavigate } from 'react-router-dom';
import { itemService } from "../../services/itemService";

void mock_items;

//agregar useeffect para pedir el token o buscarlo en el local storage y hacer un redireccionamiento al /home
export function AdminPage() {
	const navigate = useNavigate();
	const [items, setItems] = useState([]);
	const [page, setPageNumber] = useState(0);
	const [totalPages, setTotalPages] = useState(1);
	const [loading, setLoading] = useState(true);
	const pageSize = 10;

	useEffect(() => {
		let isMounted = true;

		async function loadItems() {
			try {
				setLoading(true);
				const response = await itemService.getItems(page, pageSize);
				if (!isMounted) return;

				const content = Array.isArray(response?.content) ? response.content : [];
				setItems(content);
				setTotalPages(response?.totalPages ?? 1);
			} catch (error) {
				console.error("No se pudo cargar los items", error);
				setItems([]);
				setTotalPages(1);
			} finally {
				if (isMounted) {
					setLoading(false);
				}
			}
		}

		loadItems();
		return () => {
			isMounted = false;
		};
	}, [page]);



	const handleNew = () => { navigate("/items/create", { state: { fromAdmin: true } }); };  ///navigate

	const handleEdit = (item) => { navigate("/items/edit/" + item.id, { state: { fromAdmin: true } }); };

	const handleApprove = (id) => {
		setItems((prev) => prev.map((item) => (item.id === id ? { ...item, enabled: true } : item)));
	};

	const handleDelete = (id) => {
		setItems((prev) => prev.filter((item) => item.id !== id));
	};

	return (
		<div className={styles.page}>
			<h1 className={styles.pageTitle}>Panel de administración</h1>

			<div className={styles.actions}>
				<Btn variant="coral" onClick={handleNew}> + Nuevo producto</Btn>
			</div>

			<div className={styles.tableWrapper}>
				<p className={styles.tableTitle}>Todos los productos</p>

				<div className={styles.paginationRow}>
					<button
						className={styles.paginationBtn}
						disabled={page === 0}
						onClick={() => setPageNumber((prev) => Math.max(prev - 1, 0))}
					>
						Anterior
					</button>
					<span className={styles.paginationInfo}>Página {page + 1} de {totalPages}</span>
					<button
						className={styles.paginationBtn}
						disabled={page + 1 >= totalPages}
						onClick={() => setPageNumber((prev) => prev + 1)}
					>
						Siguiente
					</button>
				</div>

				{loading ? (
					<p className={styles.empty}>Cargando productos...</p>
				) : items.length === 0 ? (
					<p className={styles.empty}>No hay productos cargados.</p>
				) : (
					<table>
						<thead>
							<tr>
								<th>Nombre</th>
								<th>Categoría</th>
								<th>Activo</th>
								<th>Sugeridos</th>
								<th>Acciones</th>
							</tr>
						</thead>
						<tbody>
							{items.map((item) => (
								<tr key={item.id}>
									<td>
										<p className={styles.itemName}>{item.name}</p>
										<p className={styles.itemBrand}>{item.brand}</p>
									</td>
									<td>{item.categoryResponse?.name || item.category || "Sin categoría"}</td>
									<td>
										<span className={`${styles.badge} ${item.enabled ? styles.badgeEnabled : styles.badgeDisabled}`}>
											{item.enabled ? "Activo" : "Inactivo"}
										</span>
									</td>
									<td>
										<span className={`${styles.badge} ${item.suggested ? styles.badgeSuggested : styles.badgeOwn}`}>
											{item.suggested ? "Sugerido" : "Propio"}
										</span>
									</td>
									<td>
										<div className={styles.rowActions}>
											<button className={styles.editBtn} onClick={() => handleEdit(item)}>✏️ Editar</button>
											{item.suggested && !item.enabled && (
												<button className={styles.approveBtn} onClick={() => handleApprove(item.id)}>Aprobar</button>
											)}
											<button className={styles.deleteBtn} onClick={() => handleDelete(item.id)}>Eliminar</button>
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				)}
			</div>
		</div>
	);
}
