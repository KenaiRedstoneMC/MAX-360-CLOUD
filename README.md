# MAX 360 CLOUD

Proyecto Android para transferir medios de una GoPro MAX (2019) a Google Drive.

## Estado real
- Android app base y workflow APK: incluidos.
- Exploración de archivos locales: incluida.
- Primera prueba de conexión HTTP con la GoPro: incluida en código. La cámara debe estar encendida y el móvil conectado manualmente a su Wi-Fi; se consulta `10.5.5.9:8080/gp/gpMediaList`.
- Descarga de medios y subida a Drive: todavía pendientes de implementar y probar.
- OAuth de Drive: requiere configurar un cliente OAuth propio para el identificador de esta app y firmar/registrar su SHA-1. No se deben subir contraseñas ni tokens al repositorio.

## Probar la GoPro
1. Enciende la MAX y activa su conexión inalámbrica / modo de conexión con la aplicación.
2. En Android, abre Ajustes > Wi-Fi y conéctate a la red de la GoPro.
3. Abre MAX 360 CLOUD y pulsa «Comprobar GoPro».
4. Si Android muestra que la red no tiene Internet, elige permanecer conectado a esa red.
5. La prueba mostrará si la API de medios responde.

La API gpControl/media de la MAX original está documentada por la comunidad; es no oficial y puede variar según firmware. La API oficial Open GoPro actual no incluye la MAX original (2019) en su tabla de compatibilidad.

## Compilar
GitHub Actions compila un APK de depuración y lo publica como artefacto.

## Privacidad
La aplicación no pide contraseñas de GoPro ni de Google en el chat. El acceso a Drive se añadirá con OAuth de Google, autorizado por el usuario en el móvil.
