import styles from "./TaskList.module.css";

export function TaskList({ tasks, onUpdate, onDelete }) {
	if(tasks.length === 0){
		return (
			<p>No se encontró ninguna tarea</p>
		)
	}
	
	return (
		<ul className={styles.taskList}>
			{tasks.map((task) => (
				<li key={`task-item-${task.id}`} className={styles.taskItem}>
					<p
						className={task.completed ? styles.completedTask : null}
						onClick={() => onUpdate({ ...task, completed: !task.completed })}
					>
						{task.title}
					</p>

					<button
						className={styles.deleteButton}
						onClick={() => onDelete(task)}
					>
						❌
					</button>
				</li>
			))}
		</ul>
	);
}
