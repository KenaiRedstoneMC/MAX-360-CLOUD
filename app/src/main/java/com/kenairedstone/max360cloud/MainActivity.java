package com.kenairedstone.max360cloud;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends Activity {
    private static final int REQUEST_FOLDER = 4102;
    private LinearLayout results;
    private TextView status;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(22), dp(28), dp(22), dp(20));
        root.setBackgroundColor(0xFF101820);

        TextView title = new TextView(this);
        title.setText("MAX 360 CLOUD"); title.setTextColor(0xFFFFFFFF);
        title.setTextSize(27); title.setGravity(Gravity.CENTER); title.setTypeface(null, 1);
        root.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("GoPro MAX (2019) → Google Drive");
        subtitle.setTextColor(0xFFB8C7D9); subtitle.setTextSize(14);
        subtitle.setGravity(Gravity.CENTER); subtitle.setPadding(0, dp(8), 0, dp(22));
        root.addView(subtitle);

        status = new TextView(this);
        status.setText("Estado: configuración inicial. Aún no hay conexión directa con la cámara ni con Drive.");
        status.setTextColor(0xFFFFD166); status.setTextSize(14);
        status.setPadding(dp(14), dp(12), dp(14), dp(12));
        status.setBackgroundColor(0xFF263442); root.addView(status);

        Button choose = new Button(this);
        choose.setText("Seleccionar carpeta de medios");
        choose.setOnClickListener(v -> chooseFolder()); root.addView(choose);

        TextView hint = new TextView(this);
        hint.setText("Selecciona una carpeta local, por ejemplo DCIM. Android pedirá permiso para acceder a ella.");
        hint.setTextColor(0xFFB8C7D9); hint.setTextSize(13);
        hint.setPadding(0, dp(8), 0, dp(12)); root.addView(hint);

        ScrollView scroll = new ScrollView(this);
        results = new LinearLayout(this); results.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(results); root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        TextView footer = new TextView(this);
        footer.setText("Versión 0.1.0 · Explorador local · Sin subida automática todavía");
        footer.setTextColor(0xFF8FA3B8); footer.setTextSize(11);
        footer.setGravity(Gravity.CENTER); footer.setPadding(0, dp(12), 0, 0);
        root.addView(footer); setContentView(root);
    }

    private void chooseFolder() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_FOLDER);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_FOLDER || resultCode != RESULT_OK || data == null || data.getData() == null) return;
        Uri tree = data.getData();
        int flags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        try { getContentResolver().takePersistableUriPermission(tree, flags); }
        catch (SecurityException ignored) { }
        scanFolder(tree);
    }

    private void scanFolder(Uri tree) {
        results.removeAllViews();
        Uri children = DocumentsContract.buildChildDocumentsUriUsingTree(tree, DocumentsContract.getTreeDocumentId(tree));
        String[] projection = { DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                DocumentsContract.Document.COLUMN_DISPLAY_NAME, DocumentsContract.Document.COLUMN_MIME_TYPE,
                DocumentsContract.Document.COLUMN_SIZE };
        int count = 0;
        try (Cursor cursor = getContentResolver().query(children, projection, null, null,
                DocumentsContract.Document.COLUMN_DISPLAY_NAME + " ASC")) {
            if (cursor != null) while (cursor.moveToNext()) {
                String name = cursor.getString(1), mime = cursor.getString(2);
                long size = cursor.isNull(3) ? -1 : cursor.getLong(3);
                if (mime == null || !(mime.startsWith("image/") || mime.startsWith("video/"))) continue;
                TextView item = new TextView(this);
                item.setText((mime.startsWith("video/") ? "VIDEO  " : "FOTO  ") + name
                        + (size >= 0 ? "  ·  " + formatSize(size) : ""));
                item.setTextColor(0xFFFFFFFF); item.setTextSize(14);
                item.setPadding(dp(10), dp(12), dp(10), dp(12)); results.addView(item);
                View line = new View(this); line.setBackgroundColor(0xFF334455);
                results.addView(line, new LinearLayout.LayoutParams(-1, dp(1))); count++;
            }
            status.setText(String.format(Locale.getDefault(),
                    "Carpeta examinada: %d foto(s) o vídeo(s). Esta versión solo los muestra; todavía no los transfiere.", count));
            status.setTextColor(0xFF9FE3B0);
        } catch (Exception e) {
            status.setText("No se pudo leer la carpeta. Vuelve a seleccionarla y concede permiso.");
            status.setTextColor(0xFFFF8A80);
        }
        if (count == 0) {
            TextView empty = new TextView(this);
            empty.setText("No se encontraron fotos ni vídeos en el primer nivel de esta carpeta.");
            empty.setTextColor(0xFFB8C7D9); empty.setPadding(dp(8), dp(18), dp(8), dp(18));
            results.addView(empty);
        }
    }

    private String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format(Locale.getDefault(), "%.1f KB", bytes / 1024.0);
        return String.format(Locale.getDefault(), "%.1f MB", bytes / (1024.0 * 1024.0));
    }
    private int dp(int value) { return (int) (value * getResources().getDisplayMetrics().density + 0.5f); }
}
