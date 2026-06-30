import styles from "./Footer.module.css";

export default function Footer() {
  return (
    <footer className={styles.footer}>

      {/* Sección newsletter */}
      <div className={styles.newsletter}>
        <p className={styles.newsletterTitle}>Suscribite a nuestro newsletter</p>
        <p className={styles.newsletterSubtitle}>Recibí novedades y los productos mejor rankeados.</p>
        <div className={styles.newsletterForm}>
          <input
            type="email"
            placeholder="tu@email.com"
            className={styles.newsletterInput}
          />
          <button className={styles.newsletterBtn}>Suscribirse</button>
        </div>
      </div>

      <div className={styles.divider} />

      <div className={styles.bottom}>
        <p className={styles.copyright}>© {new Date().getFullYear()} RankingApp. Todos los derechos reservados.</p>

        <div className={styles.socials}>
          <a
            href="https://instagram.com/rankingapp"
            target="_blank"
            rel="noopener noreferrer"
            className={styles.socialLink}
            aria-label="Instagram"
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
              <rect x="2" y="2" width="20" height="20" rx="5" ry="5"/>
              <circle cx="12" cy="12" r="4"/>
              <circle cx="17.5" cy="6.5" r="1" fill="currentColor" stroke="none"/>
            </svg>
          </a>

          <a
            href="https://x.com/rankingapp"
            target="_blank"
            rel="noopener noreferrer"
            className={styles.socialLink}
            aria-label="X"
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
              <path d="M18.244 2.25h3.308l-7.227 8.26 8.502 11.24H16.17l-4.714-6.231-5.401 6.231H2.747l7.73-8.835L1.254 2.25H8.08l4.253 5.622zm-1.161 17.52h1.833L7.084 4.126H5.117z"/>
            </svg>
          </a>

          <a
            href="https://facebook.com/rankingapp"
            target="_blank"
            rel="noopener noreferrer"
            className={styles.socialLink}
            aria-label="Facebook"
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
              <path d="M24 12.073C24 5.405 18.627 0 12 0S0 5.405 0 12.073C0 18.1 4.388 23.094 10.125 24v-8.437H7.078v-3.49h3.047V9.41c0-3.025 1.791-4.697 4.533-4.697 1.312 0 2.686.235 2.686.235v2.97h-1.513c-1.491 0-1.956.93-1.956 1.886v2.269h3.328l-.532 3.49h-2.796V24C19.612 23.094 24 18.1 24 12.073z"/>
            </svg>
          </a>

          <a
            href="mailto:contacto@rankingapp.com"
            className={styles.socialLink}
            aria-label="Email"
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
              <rect x="2" y="4" width="20" height="16" rx="2"/>
              <polyline points="2,4 12,13 22,4"/>
            </svg>
          </a>
        </div>
      </div>

    </footer>
  );
}
