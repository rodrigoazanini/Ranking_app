import { useState } from "react";
import styles from "./AdminPage.module.css";
import Btn from "../../components/Btn/Btn";
import { mock_items } from "../../data/mockData";
import { useNavigate } from 'react-router-dom';

//agregar useeffect para pedir el token o buscarlo en el local storage y hacer un redireccionamiento al /home
//CAMBIAR A NAVIGATE
export function AdminPage({ setPage}) {
  // TODO: reemplazar por → useEffect(() => api.get('/items/all').then(...), [])
  const [items, setItems] = useState(mock_items);

  const  SelectedItem =  setSelectedItem  

  const handleNew = () => { setSelectedItem(null); setPage("new"); };  ///navigate

  const handleEdit = (item) => { setSelectedItem(item); setPage("edit"); };

  const handleApprove = (id) => {
	// TODO: api.patch(`/items/${id}/enabled`, { enabled: true })
	setItems((prev) => prev.map((item) => item.id === id ? { ...item, enabled: true } : item));
  };

  const handleDelete = (id) => {
	// TODO: api.delete(`/items/${id}`)
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

		{items.length === 0 ? (
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
				  <td>{item.category}</td>
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
