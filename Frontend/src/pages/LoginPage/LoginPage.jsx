import { useState } from "react";
import styles from "./LoginPage.module.css";
import Btn from "../../components/Btn/Btn";
import Input from "../../components/Input/Input";

export default function LoginPage({ setPage }) {
  const [mode, setMode]   = useState("login");
  const [form, setForm]   = useState({ username:"", email:"", password:"", confirm:"" });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = () => {
    setError("");
    if (mode === "register") {
      if (!form.username || !form.email || !form.password) { setError("Completá todos los campos."); return; }
      if (form.password !== form.confirm) { setError("Las contraseñas no coinciden."); return; }
    } else {
      if (!form.email || !form.password) { setError("Completá todos los campos."); return; }
    }
    setLoading(true);
    
  };

  const toggleMode = () => {
    setMode(mode === "login" ? "register" : "login");
    setError("");
    setForm({ username:"", email:"", password:"", confirm:"" });
  };

  return (
    <div className={styles.page}>
      <div className={styles.card}>
        <div className={styles.avatarWrapper}>
          <div className={styles.avatar}>👤</div>
          <h2 className={styles.title}>{mode === "login" ? "Iniciar sesión" : "Registro"}</h2>
        </div>

        {mode === "register" && <Input label="Nombre de usuario" value={form.name} onChange={(e) => setForm({...form, name:e.target.value})} placeholder="Tu nombre" />}
        <Input label="E-mail" type="email" value={form.email} onChange={(e) => setForm({...form, email:e.target.value})} placeholder="correo@ejemplo.com" />
        <Input label="Contraseña" type="password" value={form.password} onChange={(e) => setForm({...form, password:e.target.value})} placeholder="••••••••" />
        {mode === "register" && <Input label="Repetir contraseña" type="password" value={form.confirm} onChange={(e) => setForm({...form, confirm:e.target.value})} placeholder="••••••••" />}

        {error && <p className={styles.error}>{error}</p>}

        <Btn variant="coral" onClick={handleSubmit} disabled={loading} className={styles.submitBtn}>
          {loading ? "Cargando..." : mode === "login" ? "ENTRAR" : "REGISTRARSE"}
        </Btn>

        <p className={styles.switchText}>
          {mode === "login" ? "¿No tenés cuenta? " : "¿Ya tenés cuenta? "}
          <span className={styles.switchLink} onClick={toggleMode}>
            {mode === "login" ? "REGISTRATE" : "INGRESAR"}
          </span>
        </p>
      </div>
    </div>
  );
}
