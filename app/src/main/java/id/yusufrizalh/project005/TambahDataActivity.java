package id.yusufrizalh.project005;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.HashMap;

public class TambahDataActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_tambah_nama, edit_tambah_jabatan, edit_tambah_gaji;
    private Button btn_tambah_pegawai, btn_lihat_pegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        edit_tambah_nama = findViewById(R.id.edit_tambah_nama);
        edit_tambah_jabatan = findViewById(R.id.edit_tambah_jabatan);
        edit_tambah_gaji = findViewById(R.id.edit_tambah_gaji);
        btn_lihat_pegawai = findViewById(R.id.btn_lihat_pegawai);
        btn_tambah_pegawai = findViewById(R.id.btn_tambah_pegawai);

        btn_lihat_pegawai.setOnClickListener(this);
        btn_tambah_pegawai.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_lihat_pegawai) {
            startActivity(new Intent(TambahDataActivity.this, LihatDataActivity.class));
        } else if (v == btn_tambah_pegawai) {
            simpanDataPegawai();
        }
    }

    private void simpanDataPegawai() {
        // fields apa saja yang akan disimpan
        final String nama = edit_tambah_nama.getText().toString().trim();
        final String jabatan = edit_tambah_jabatan.getText().toString().trim();
        final String gaji = edit_tambah_gaji.getText().toString().trim();

        class SimpanDataPegawai extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahDataActivity.this,
                        "Menyimpan Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_PGW_NAMA, nama);
                params.put(Konfigurasi.KEY_PGW_JABATAN, jabatan);
                params.put(Konfigurasi.KEY_PGW_GAJI, gaji);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_ADD, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahDataActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                clearText();
            }
        }
        SimpanDataPegawai simpanDataPegawai = new SimpanDataPegawai();
        simpanDataPegawai.execute();
    }

    private void clearText() {
        edit_tambah_nama.setText("");
        edit_tambah_jabatan.setText("");
        edit_tambah_gaji.setText("");
        edit_tambah_nama.requestFocus();
    }
}