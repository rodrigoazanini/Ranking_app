import styles from "./TasksPage.module.css";
import { useEffect, useState } from "react";
import { TaskList } from "../../components/TaskList/TaskList";
import { taskService } from "../../services/taskService";
import { CreateTaskForm } from "../../components/CreateTaskForm/CreateTaskForm";

export function TasksPage() {
	const [tasks, setTasks] = useState([]);

	async function getTasks() {
		const data = await taskService.getTasks();
		if (data.ok) setTasks(data.tasks);
	}

	async function createTask(task) {
		const data = await taskService.createTask(task);
		if (data.ok) getTasks();
	}

	async function deleteTask(task) {
		const data = await taskService.deleteTask(task.id);
		if (data.ok) getTasks();
	}

	async function updateTask(task) {
		const data = await taskService.updateTask(task.id, task);
		if (data.ok) getTasks();
	}

	useEffect(() => {
		getTasks();
	}, []);

	const [search, setSearch] = useState("");
	const [filter, setFilter] = useState("all");

	const filteredTasks = tasks.filter((task) => {
		return (
			task.title.toLowerCase().includes(search.toLowerCase())
			&& (
				filter === "all" 
				|| (filter === "completed" && task.completed)
				|| (filter === "pending" && !task.completed)
			)
		);
	});

	return (
		<main>
			<header>
				<h2>Lista de tareas</h2>
				<p>
					{`Tenes ${tasks.filter((p) => !p.completed).length} tareas pendientes`}
				</p>
			</header>

			<div className={styles.inputsWrapper}>
				<CreateTaskForm onCreate={createTask} />

				<input
					type="text"
					placeholder="Buscar por titulo..."
					value={search}
					onChange={(e) => setSearch(e.target.value)}
				/>

				<select value={filter} onChange={(e) => setFilter(e.target.value)}>
					<option value="all">Todas</option>
					<option value="pending">Pendientes</option>
					<option value="completed">Completadas</option>
				</select>
			</div>

			<TaskList
				tasks={filteredTasks}
				onUpdate={updateTask}
				onDelete={deleteTask}
			/>
		</main>
	);
}
