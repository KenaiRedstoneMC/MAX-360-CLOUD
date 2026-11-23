# MAX 360 CLOUD

Aplicación Android para investigar y construir una sincronización de medios de GoPro MAX (2019) con Google Drive.

## Estado actual

Esta primera base crea una app Android y permite seleccionar una carpeta local de medios mediante el selector seguro de Android. **Todavía no descarga archivos directamente desde la GoPro ni los sube a Google Drive.** Esas dos integraciones requieren desarrollo y pruebas reales; no se presentan como funciones terminadas.

## Requisitos

- Android 8.0 (API 26) o superior.
- Android Studio / JDK 17 para compilar localmente.

## Compilación

GitHub Actions compila automáticamente el APK de depuración al hacer push. El archivo se publica como artefacto de la ejecución del workflow.

## Próximos hitos

1. Validar la conexión Wi-Fi y el protocolo de transferencia de la GoPro MAX 2019.
2. Implementar transferencia local robusta, reintentos y detección de duplicados.
3. Integrar Google Drive mediante OAuth oficial, sin pedir ni almacenar contraseñas.
4. Probar sincronización en segundo plano y documentar las limitaciones de Android.

## Privacidad

La app no contiene credenciales de Google ni claves privadas. La futura conexión a Drive debe realizarse mediante el inicio de sesión OAuth del propio usuario.
