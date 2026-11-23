# MAX 360 CLOUD

Aplicación Android para investigar y construir una sincronización de medios de GoPro MAX (2019) con Google Drive.

## Estado de esta versión

Esta es la primera base compilable de Android. Permite seleccionar una carpeta local (por ejemplo, la carpeta de medios de GoPro Quik) y examinar fotos y vídeos compatibles. **Aún no descarga directamente desde la GoPro MAX ni sube archivos a Google Drive.** No se debe considerar terminada la sincronización automática.

## Requisitos
- Android 8.0 (API 26) o superior.
- Android Studio / JDK 17.

## Compilar
Cada push a `main` ejecuta GitHub Actions y publica el APK de depuración como artefacto en la ejecución del workflow.

## Hitos técnicos
1. Investigar y validar el protocolo Wi-Fi de la GoPro MAX original (2019); no confundirlo con MAX2/Open GoPro.
2. Implementar descarga inalámbrica y reanudación segura.
3. Integrar Google Drive mediante OAuth oficial y consentimiento del usuario.
4. Añadir sincronización en segundo plano, control de duplicados y reintentos.
5. Probar en dispositivos Android reales y documentar límites de batería, Wi-Fi y datos móviles.

## Privacidad
No se solicitan contraseñas. No hay claves privadas ni tokens incluidos en el repositorio. La futura integración de Drive debe usar OAuth y credenciales configuradas por el desarrollador en Google Cloud.
